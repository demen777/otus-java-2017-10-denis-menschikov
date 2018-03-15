package dm.otus.l15_msg.message_system;

public abstract class Message {
    private Address to;
    private MessageSystem messageSystem;
    private long id;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public abstract ServiceType getServiceType();

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public void setMessageSystem(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }
}
