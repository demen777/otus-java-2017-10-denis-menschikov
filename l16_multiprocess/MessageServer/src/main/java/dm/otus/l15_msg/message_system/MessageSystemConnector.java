package dm.otus.l15_msg.message_system;

public interface MessageSystemConnector {
    void setServiceObject(Object service);
    void registerService(ServiceType serviceType);
    void sendMessage(Message message);
    void sendMessageTo(Message message, Address to);
    SyncMessageAnswer sendMessageSync(SyncMessage message);
}
