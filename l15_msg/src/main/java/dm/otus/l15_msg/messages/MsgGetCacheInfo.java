package dm.otus.l15_msg.messages;

import dm.otus.l15_msg.db.CacheInfo;
import dm.otus.l15_msg.message_system.AsyncMessage;
import dm.otus.l15_msg.message_system.ServiceType;

public class MsgGetCacheInfo extends AsyncMessage {
    private final ServiceType serviceType = new ServiceType(CacheInfo.class);

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    @Override
    public void exec(Object receiver) {
        CacheInfo cacheInfo = (CacheInfo) receiver;
        MsgGetCacheInfoAnswer msg = new MsgGetCacheInfoAnswer(getId(),
                cacheInfo.getHitCount(), cacheInfo.getMissCount());
        sendReply(receiver, msg);
    }
}
