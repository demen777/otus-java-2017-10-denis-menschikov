package dm.otus.l15_msg.message_system;

@SuppressWarnings("WeakerAccess")
public interface MessageDelivery {
    void send(Message message);
    Message take() throws InterruptedException;
    void close();
}
