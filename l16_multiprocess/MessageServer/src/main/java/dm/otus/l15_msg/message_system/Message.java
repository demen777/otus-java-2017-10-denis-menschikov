package dm.otus.l15_msg.message_system;

import java.util.logging.Logger;

@SuppressWarnings("WeakerAccess")
public abstract class Message {
    private final static Logger logger = Logger.getLogger(Message.class.getName());

    private Address from;
    private Address to;
    private MessageSystemConnector messageSystemConnector;
    private long id;
    public static final String CLASS_NAME_VARIABLE = "className";
    @SuppressWarnings("FieldCanBeLocal")
    private final String className;

    protected Message(Class klass) {
        this.className = klass.getName();
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public abstract ServiceType getServiceType();

    public MessageSystemConnector getMessageSystemConnector() {
        return messageSystemConnector;
    }

    public void setMessageSystemConnector(MessageSystemConnector messageSystemConnector) {
        this.messageSystemConnector = messageSystemConnector;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public void sendReply(Message replyMessage) {
        logger.info(String.format("messageSystemConnector=%s old_from=%s", getMessageSystemConnector(),
                getFrom().getId()));
        getMessageSystemConnector().sendMessageTo(replyMessage, getFrom());
    }

    public abstract void exec(Object receiver);
}
