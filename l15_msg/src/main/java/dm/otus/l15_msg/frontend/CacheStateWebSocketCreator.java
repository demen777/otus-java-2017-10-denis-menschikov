package dm.otus.l15_msg.frontend;

import dm.otus.l15_msg.cache.CacheInfo;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class CacheStateWebSocketCreator implements WebSocketCreator {
    private final CacheInfo cacheInfo;
    private final DBWorkEmulator dbWorkEmulator;

    public CacheStateWebSocketCreator(CacheInfo cacheInfo, DBWorkEmulator dbWorkEmulator) {
        this.cacheInfo = cacheInfo;
        this.dbWorkEmulator = dbWorkEmulator;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        CacheStateWebSocket socket = new CacheStateWebSocket(cacheInfo, dbWorkEmulator);
        System.out.println("Socket created");
        return socket;
    }
}
