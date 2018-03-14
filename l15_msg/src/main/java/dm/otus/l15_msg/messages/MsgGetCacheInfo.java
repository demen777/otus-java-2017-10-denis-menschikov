package dm.otus.l15_msg.messages;

import dm.otus.l15_msg.cache.CacheInfo;
import dm.otus.l15_msg.message_system.Message;
import dm.otus.l15_msg.message_system.ServiceType;

public class MsgGetCacheInfo extends Message{

    private final ServiceType serviceType = new ServiceType(CacheInfo.class);

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    @Override
    public void exec(Object receiver) {
        CacheInfo cacheInfo = (CacheInfo) receiver;
        MsgGetCacheInfoAnswer msg = new MsgGetCacheInfoAnswer(cacheInfo.getHitCount(), cacheInfo.getMissCount());
        getMessageSystem().sendMessageTo(receiver, msg, getTo());
    }
}
