package dm.otus.l15_msg.message_system;

public abstract class AsyncMessage extends Message {
    protected AsyncMessage(Class className) {
        super(className);
    }
}
