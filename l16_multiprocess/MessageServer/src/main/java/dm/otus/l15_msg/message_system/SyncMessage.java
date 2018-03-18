package dm.otus.l15_msg.message_system;

public abstract class SyncMessage extends Message {
    protected SyncMessage(Class className) {
        super(className);
    }
}
