package dm.otus.l15_msg.message_system;


public interface MessageSystem {
    void addReceiver(ServiceType serviceType, Object receiver);
    long sendMessage(Object sender, AsyncMessage message);
    long sendMessageTo(Object sender, AsyncMessage message, Address to);
    Object sendMessageSync(Object sender, SyncMessage message);
    void dispose();
}
