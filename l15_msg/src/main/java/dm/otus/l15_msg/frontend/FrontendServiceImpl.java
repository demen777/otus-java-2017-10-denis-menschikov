package dm.otus.l15_msg.frontend;

import dm.otus.l15_msg.message_system.MessageSystem;
import dm.otus.l15_msg.message_system.ServiceType;
import dm.otus.l15_msg.messages.MsgGetCacheInfo;

import java.util.HashMap;
import java.util.Map;

public class FrontendServiceImpl implements FrontendService {
    private final DBWorkEmulator dbWorkEmulator;
    private final MessageSystem messageSystem;
    private final Map<Long, CacheStateWebSocket> idToCacheStateWebSocket;

    public FrontendServiceImpl(DBWorkEmulator dbWorkEmulator, MessageSystem messageSystem) {
        this.dbWorkEmulator = dbWorkEmulator;
        this.messageSystem = messageSystem;
        idToCacheStateWebSocket = new HashMap<>();
        messageSystem.addReceiver(new ServiceType(FrontendService.class), this);
    }

    @Override
    public synchronized void requestCacheInfo(CacheStateWebSocket cacheStateWebSocket) {
        dbWorkEmulator.doSomeWork();
        MsgGetCacheInfo msg = new MsgGetCacheInfo();
        long messageId = messageSystem.sendMessage(this, msg);
        idToCacheStateWebSocket.put(messageId, cacheStateWebSocket);
    }

    @Override
    public void sendCacheInfo(long requestId, long cacheHits, long cacheMiss) {
        CacheStateWebSocket cacheStateWebSocket = idToCacheStateWebSocket.get(requestId);
        cacheStateWebSocket.sendInfo(cacheHits, cacheMiss);
        idToCacheStateWebSocket.remove(requestId);
    }

}
