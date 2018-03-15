package dm.otus.l15_msg.message_system;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSystemImpl implements MessageSystem {

    private final static Logger logger = Logger.getLogger(MessageSystemImpl.class.getName());
    private static final long DEFAULT_STEP_TIME = 10L;

    private final Map<Address, ConcurrentLinkedQueue<AsyncMessage>> addressToQueues;
    private final Map<ServiceType, List<Address>> serviceTypeToAddresses;
    private final Map<Address, Object> addressToReceiver;
    private final Map<Address, ReentrantLock> addressToLock;
    private final Map<Object, Address> receiverToAddress;
    private final AtomicBoolean terminateFlag;
    private final AtomicLong messageIdCounter;

    public MessageSystemImpl() {
        receiverToAddress = new HashMap<>();
        addressToQueues = new HashMap<>();
        serviceTypeToAddresses = new HashMap<>();
        addressToReceiver = new HashMap<>();
        addressToLock = new HashMap<>();
        terminateFlag = new AtomicBoolean();
        terminateFlag.set(false);
        messageIdCounter = new AtomicLong();
        messageIdCounter.set(0);
    }

    @Override
    public synchronized void addReceiver(ServiceType serviceType, Object receiver) {
        Address address = generateAddress(serviceType);
        List<Address> addresses = serviceTypeToAddresses.computeIfAbsent(serviceType, k -> new ArrayList<>());
        addresses.add(address);
        addressToReceiver.put(address, receiver);
        receiverToAddress.put(receiver, address);
        addressToLock.put(address, new ReentrantLock());
        addressToQueues.put(address, new ConcurrentLinkedQueue<>());
        startThreadForAddress(address);
    }

    private Address generateAddress(ServiceType serviceType) {
        int counter=1;
        while(true) {
            Address curAddress = new Address(String.format("%s-%d", serviceType, counter++));
            if (!addressToReceiver.containsKey(curAddress)) {
                return curAddress;
            }
        }
    }

    @Override
    public long sendMessage(Object sender, AsyncMessage message) {
        long messageId = messageIdCounter.incrementAndGet();
        message.setMessageSystem(this);
        message.setId(messageId);
        Address addressFrom = receiverToAddress.get(sender);
        if (addressFrom != null) {
            message.setFrom(addressFrom);
        }
        if (message.getTo() == null) {
            Address addressTo = chooseAddress(message.getServiceType());
            message.setTo(addressTo);
        }
        addressToQueues.get(message.getTo()).add(message);
        return messageId;
    }

    private Address chooseAddress(ServiceType serviceType) {
        List<Address> addresses = serviceTypeToAddresses.get(serviceType);
        if (addresses == null) {
            logger.log(Level.SEVERE, "No one address for serviceType=" + serviceType);
            return null;
        }
        if (addresses.size() == 1) {
            return addresses.get(0);
        }
        Random random = new Random();
        return addresses.get(random.nextInt(addresses.size()));
    }

    @Override
    public long sendMessageTo(Object sender, AsyncMessage message, Address to) {
        message.setTo(to);
        return sendMessage(sender, message);
    }

    @Override
    public Object sendMessageSync(Object sender, SyncMessage message) {
        message.setMessageSystem(this);
        Address addressTo = chooseAddress(message.getServiceType());
        Object receiver = addressToReceiver.get(addressTo);
        ReentrantLock lock = addressToLock.get(addressTo);
        Object result;
        lock.lock();
        try {
            result = message.call(receiver);
        }
        finally {
            lock.unlock();
        }
        return result;
    }

    private void startThreadForAddress(Address address) {
        String name = "MessageSystem-worker-" + address.getId();
        Thread thread = new Thread(() -> {
            while (!terminateFlag.get()) {
                long startTime = System.currentTimeMillis();
                ConcurrentLinkedQueue<AsyncMessage> queue = addressToQueues.get(address);
                while (!queue.isEmpty()) {
                    AsyncMessage message = queue.poll();
                    if (message != null) {
                        Address to = message.getTo();
                        if (to != null) {
                            Object receiver = addressToReceiver.get(to);
                            ReentrantLock lock = addressToLock.get(to);
                            lock.lock();
                            try {
                                message.exec(receiver);
                            }
                            finally {
                                lock.unlock();
                            }
                        }
                    }
                }
                long diffTime = MessageSystemImpl.DEFAULT_STEP_TIME-(System.currentTimeMillis()-startTime);
                if (diffTime > 0) {
                    try {
                        Thread.sleep(diffTime);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
            logger.log(Level.INFO, "Worker terminate " + Thread.currentThread().getName());
        });
        thread.setName(name);
        thread.start();
    }

    @Override
    public void dispose() {
        terminateFlag.set(true);
    }
}
