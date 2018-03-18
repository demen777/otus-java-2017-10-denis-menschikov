package dm.otus.l15_msg.message_system;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketMessageSystemConnector implements MessageSystemConnector {
    private final Socket socket;
    private final SocketMessageDelivery messageDelivery;
    private final ExecutorService executor;
    private Object service;
    private final BlockingQueue<SyncMessageAnswer> syncMessageAnswerQueue;

    public SocketMessageSystemConnector(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        messageDelivery = new SocketMessageDelivery(this.socket);
        syncMessageAnswerQueue = new ArrayBlockingQueue<>(1);
        executor = Executors.newFixedThreadPool(1);
        executor.execute(this::receiveMessageHandler);
    }

    @Override
    public void setServiceObject(Object service) {
        this.service = service;
    }

    @Override
    public void registerService(ServiceType serviceType) {
        MsgRegisterService msg = new MsgRegisterService(serviceType);
        sendMessageSync(msg);
    }

    @Override
    public void sendMessage(Message message) {
        messageDelivery.send(message);
    }

    @Override
    public void sendMessageTo(Message message, Address to) {
        message.setTo(to);
        sendMessage(message);
    }

    @Override
    public SyncMessageAnswer sendMessageSync(SyncMessage message) {
        sendMessage(message);
        try {
            return syncMessageAnswerQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void receiveMessageHandler() {
        while(!executor.isShutdown()) {
            try {
                Message message = messageDelivery.take();
                message.setMessageSystemConnector(this);
                if (message instanceof SyncMessageAnswer) {
                    syncMessageAnswerQueue.put((SyncMessageAnswer) message);
                    continue;
                }
                message.exec(service);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void close() {
        this.messageDelivery.close();
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
