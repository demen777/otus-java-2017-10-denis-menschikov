package dm.otus.l15_msg.message_system;

public abstract class AsyncMessage extends Message {
    private Address from;

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    @SuppressWarnings("UnusedReturnValue")
    public long sendReply(Object sender, AsyncMessage replyMessage) {
        return getMessageSystem().sendMessageTo(sender, replyMessage, getFrom());
    }

    public abstract void exec(Object receiver);
}
