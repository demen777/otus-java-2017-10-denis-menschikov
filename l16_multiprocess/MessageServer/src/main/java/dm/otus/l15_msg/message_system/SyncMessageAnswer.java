package dm.otus.l15_msg.message_system;

public abstract class SyncMessageAnswer extends Message {
    protected SyncMessageAnswer(Class className) {
        super(className);
    }

    @Override
    public ServiceType getServiceType() { return null; }

    @Override
    public void exec(Object receiver) { }
}
