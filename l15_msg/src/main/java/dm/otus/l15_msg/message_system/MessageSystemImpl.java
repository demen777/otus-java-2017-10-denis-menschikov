package dm.otus.l15_msg.message_system;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSystemImpl implements MessageSystem {

    private final static Logger logger = Logger.getLogger(MessageSystemImpl.class.getName());
    private static final long DEFAULT_STEP_TIME = 10L;

    private final Map<ServiceType, ConcurrentLinkedQueue<Message>> messagesMap;
    private final Map<ServiceType, List<Address>> serviceTypeToAddresses;
    private final Map<Address, Object> addressToReceiver;
    private final Map<Object, Address> receiverToAddress;
    private final int workerQuantity;
    private AtomicBoolean terminateFlag;

    public MessageSystemImpl(Map<Object, Address> receiverToAddress, int workerQuantity) {
        this.receiverToAddress = receiverToAddress;
        this.workerQuantity = workerQuantity;
        messagesMap = new HashMap<>();
        serviceTypeToAddresses = new HashMap<>();
        addressToReceiver = new HashMap<>();
        terminateFlag = new AtomicBoolean();
        terminateFlag.set(false);
    }

    @Override
    public synchronized void addService(ServiceType serviceType, Object receiver) {
        Address address = generateAddress(serviceType);
        List<Address> addresses = serviceTypeToAddresses.computeIfAbsent(serviceType, k -> new ArrayList<>());
        addresses.add(address);
        addressToReceiver.put(address, receiver);
        receiverToAddress.put(receiver, address);
        messagesMap.computeIfAbsent(serviceType, k -> new ConcurrentLinkedQueue<>());
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
    public void sendMessage(Object sender, Message message) {
        message.setMessageSystem(this);
        Address address = receiverToAddress.get(sender);
        if (address != null) {
            message.setFrom(address);
        }
        messagesMap.get(message.getServiceType()).add(message);
    }

    @Override
    public void sendMessageTo(Object sender, Message message, Address to) {
        message.setTo(to);
        sendMessage(sender, message);
    }

    @Override
    public void start() {
        for(int i=0; i<workerQuantity; i++) {
            String name = "MessageSystem-worker-" + i;
            Thread thread = new Thread(this::runWorker);
            thread.setName(name);
            thread.start();
        }
    }

    private void runWorker() {
        while (!terminateFlag.get()) {
            long startTime = System.currentTimeMillis();
            for(ConcurrentLinkedQueue<Message> queue: messagesMap.values()) {
                if (!queue.isEmpty()) {
                    Message message = queue.poll();
                    if (message != null) {
                        Address to = message.getTo();
                        if (to == null) {
                            to = chooseAddress(message.getServiceType());
                        }
                        if (to != null) {
                            Object receiver = addressToReceiver.get(to);
                            message.exec(receiver);
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
    public void dispose() {
        terminateFlag.set(true);
    }
}
