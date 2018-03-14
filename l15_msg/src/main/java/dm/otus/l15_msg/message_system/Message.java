package dm.otus.l15_msg.message_system;

public abstract class Message {
    private Address from;
    private Address to;
    private MessageSystem messageSystem;

    public abstract ServiceType getServiceType();

    public abstract void exec(Object receiver);

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public void setMessageSystem(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }
}
