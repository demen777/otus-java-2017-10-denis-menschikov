package dm.otus.l15_msg.frontend;

import dm.otus.l15_msg.message_system.MessageSystemConnector;
import dm.otus.l15_msg.message_system.ServiceType;
import dm.otus.l15_msg.messages.MsgGetCacheInfo;

import java.util.ArrayList;
import java.util.List;

public class FrontendServiceImpl implements FrontendService {
    private final MessageSystemConnector messageSystemConnector;
    private final List<CacheStateWebSocket> cacheStateWebSockets;

    public FrontendServiceImpl(MessageSystemConnector messageSystemConnector) {
        this.messageSystemConnector = messageSystemConnector;
        this.messageSystemConnector.setServiceObject(this);
        cacheStateWebSockets = new ArrayList<>();
        messageSystemConnector.registerService(new ServiceType(FrontendService.class));
    }

    @Override
    public synchronized void requestCacheInfo(CacheStateWebSocket cacheStateWebSocket) {
        MsgGetCacheInfo msg = new MsgGetCacheInfo();
        messageSystemConnector.sendMessage(msg);
        cacheStateWebSockets.add(cacheStateWebSocket);
    }

    @Override
    public synchronized void sendCacheInfo(long requestId, long cacheHits, long cacheMiss) {
        for(CacheStateWebSocket cacheStateWebSocket: cacheStateWebSockets) {
            cacheStateWebSocket.sendInfo(cacheHits, cacheMiss);
        }
        cacheStateWebSockets.clear();
    }

}
