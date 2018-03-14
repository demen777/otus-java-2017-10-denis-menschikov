package dm.otus.l15_msg.frontend;

import dm.otus.l15_msg.cache.CacheInfo;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.HashMap;

@WebSocket
public class CacheStateWebSocket {
    private final CacheInfo cacheInfo;
    private static final String TEMPLATE_FILENAME = "cache_state.json";
    private final DBWorkEmulator dbWorkEmulator;

    public CacheStateWebSocket(CacheInfo cacheInfo, DBWorkEmulator dbWorkEmulator) {
        this.dbWorkEmulator = dbWorkEmulator;
        this.cacheInfo = cacheInfo;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String data) {
        String info = null;
        try {
            info = generatePage(cacheInfo.getHitCount(), cacheInfo.getMissCount());
            session.getRemote().sendString(info);
            System.out.println("Sending message: " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generatePage(long hitCount, long missCount) throws IOException {
        dbWorkEmulator.doSomeWork();
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("hits", hitCount);
        variables.put("misses", missCount);
        return TemplateHelper.generatePage(TEMPLATE_FILENAME, variables);
    }
}
