package dm.otus.l15_msg.message_system;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("WeakerAccess")
public class MessageSystem {

    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());
    private static final long DEFAULT_STEP_TIME = 10L;
    private static final int PORT = 8888;

    private final Map<ServiceType, List<Address>> serviceTypeToAddress;
    private final Map<Address, MessageDelivery> addressToDeliver;
    private final Map<MessageDelivery, Address> deliverToAddress;
    private final List<MessageDelivery> deliveries;
    private final List<Socket> sockets;

    private final AtomicBoolean terminateFlag = new AtomicBoolean();
    @SuppressWarnings("FieldCanBeLocal")
    private final AtomicLong messageIdCounter = new AtomicLong();
    private final AtomicLong workerIdCounter = new AtomicLong();

    @SuppressWarnings("WeakerAccess")
    public MessageSystem() {
        serviceTypeToAddress = new HashMap<>();
        addressToDeliver = new HashMap<>();
        deliverToAddress = new HashMap<>();
        terminateFlag.set(false);
        messageIdCounter.set(0);
        workerIdCounter.set(0);
        deliveries = new ArrayList<>();
        sockets = new ArrayList<>();
    }

    private synchronized void registerService(ServiceType serviceType, MessageDelivery messageDelivery) {
        Address address = generateAddress(serviceType);
        List<Address> addresses = serviceTypeToAddress.computeIfAbsent(serviceType, k -> new ArrayList<>());
        addresses.add(address);
        logger.info(String.format("registerService=%s with address=%s", serviceType.getName(), address.getId()));
        addressToDeliver.put(address, messageDelivery);
        deliverToAddress.put(messageDelivery, address);
    }

    private Address generateAddress(ServiceType serviceType) {
        int counter=1;
        while(true) {
            Address curAddress = new Address(String.format("%s-%d", serviceType.getName(), counter++));
            if (!addressToDeliver.containsKey(curAddress)) {
                return curAddress;
            }
        }
    }

    private Address chooseAddress(ServiceType serviceType) {
        List<Address> addresses = serviceTypeToAddress.get(serviceType);
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

    public void runServerSocket() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!terminateFlag.get()) {
                Socket socket = serverSocket.accept();
                MessageDelivery delivery = new SocketMessageDelivery(socket);
                deliveries.add(delivery);
                startThreadForDelivery(delivery);
            }
        }
    }

    private void startThreadForDelivery(MessageDelivery messageDelivery) {
        String name = "MessageSystem-worker-" + workerIdCounter.incrementAndGet();
        Thread thread = new Thread(() -> {
            while (!terminateFlag.get()) {
                long startTime = System.currentTimeMillis();
                try {
                    Message message = messageDelivery.take();
                    if (message instanceof MsgRegisterService) {
                        registerService(message.getServiceType(), messageDelivery);
                        messageDelivery.send(new MsgRegisterServiceAnswer(true));
                        continue;
                    }
                    Address from = deliverToAddress.get(messageDelivery);
                    logger.info(String.format("From = %s", from.getId()));
                    message.setFrom(from);
                    Address to = message.getTo();
                    if (to == null) {
                        to = chooseAddress(message.getServiceType());
                    }
                    message.setId(messageIdCounter.incrementAndGet());
                    if (to != null) {
                        message.setTo(to);
                        MessageDelivery destinationDelivery = addressToDeliver.get(to);
                        if (destinationDelivery != null) {
                            destinationDelivery.send(message);
                        }
                    }
                    long diffTime = MessageSystem.DEFAULT_STEP_TIME-(System.currentTimeMillis()-startTime);
                    if (diffTime > 0) {
                        Thread.sleep(diffTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            logger.log(Level.INFO, "Worker terminate " + Thread.currentThread().getName());
        });
        thread.setName(name);
        thread.start();
    }

    public void dispose() {
        terminateFlag.set(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(MessageDelivery messageDelivery:deliveries) {
            messageDelivery.close();
        }
        for(Socket socket:sockets) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
