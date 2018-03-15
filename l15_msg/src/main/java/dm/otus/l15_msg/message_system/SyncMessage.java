package dm.otus.l15_msg.message_system;

public abstract class SyncMessage extends Message {
    public abstract Object call(Object receiver);
}
