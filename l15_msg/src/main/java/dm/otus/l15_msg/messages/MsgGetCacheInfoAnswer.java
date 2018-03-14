package dm.otus.l15_msg.messages;

import dm.otus.l15_msg.message_system.Message;
import dm.otus.l15_msg.message_system.ServiceType;

public class MsgGetCacheInfoAnswer extends Message {
    private final long hitCount;
    private final long missCount;

    public MsgGetCacheInfoAnswer(long hitCount, long missCount) {
        this.hitCount = hitCount;
        this.missCount = missCount;
    }

    @Override
    public ServiceType getServiceType() {
        return null;
    }

    @Override
    public void exec(Object receiver) {

    }
}
