package dm.otus.l15_msg.messages;

import dm.otus.l15_msg.api.CacheInfoAPI;
import dm.otus.l15_msg.message_system.AsyncMessage;
import dm.otus.l15_msg.message_system.ServiceType;

@SuppressWarnings("WeakerAccess")
public class MsgGetCacheInfo extends AsyncMessage {
    private final ServiceType serviceType = new ServiceType(CacheInfoAPI.class);

    public MsgGetCacheInfo() {
        super(MsgGetCacheInfo.class);
    }

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    @Override
    public void exec(Object receiver) {
        CacheInfoAPI cacheInfo = (CacheInfoAPI) receiver;
        MsgGetCacheInfoAnswer msg = new MsgGetCacheInfoAnswer(getId(),
                cacheInfo.getHitCount(), cacheInfo.getMissCount());
        sendReply(msg);
    }
}
