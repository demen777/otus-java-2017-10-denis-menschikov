package dm.otus.l15_msg.messages;

import dm.otus.l15_msg.api.FrontendServiceAPI;
import dm.otus.l15_msg.message_system.AsyncMessage;
import dm.otus.l15_msg.message_system.ServiceType;

public class MsgGetCacheInfoAnswer extends AsyncMessage {
    private final long hitCount;
    private final long missCount;
    private final long requestMessageId;

    public MsgGetCacheInfoAnswer(long requestMessageId, long hitCount, long missCount) {
        super(MsgGetCacheInfoAnswer.class);
        this.requestMessageId = requestMessageId;
        this.hitCount = hitCount;
        this.missCount = missCount;
    }

    @Override
    public ServiceType getServiceType() {
        return null;
    }

    @Override
    public void exec(Object receiver) {
        FrontendServiceAPI frontendService = (FrontendServiceAPI)receiver;
        frontendService.sendCacheInfo(requestMessageId, hitCount, missCount);
    }
}
