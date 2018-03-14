package dm.otus.l15_msg.message_system;


public interface MessageSystem {
    void addService(ServiceType serviceType, Object receiver);
    void sendMessage(Object sender, Message message);
    void sendMessageTo(Object sender, Message message, Address to);
    void start();
    void dispose();
}
