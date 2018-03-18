package dm.otus.l15_msg.frontend;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.HashMap;

@WebSocket
public class CacheStateWebSocket {
    private static final String TEMPLATE_FILENAME = "cache_state.json";
    private final FrontendService frontendService;
    private Session session;

    public CacheStateWebSocket(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String data) {
        frontendService.requestCacheInfo(this);
    }

    public void sendInfo(long hitCount, long missCount) {
        String info = null;
        try {
            info = generatePage(hitCount, missCount);
            session.getRemote().sendString(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sending message: " + info);
    }

    private String generatePage(long hitCount, long missCount) throws IOException {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("hits", hitCount);
        variables.put("misses", missCount);
        return TemplateHelper.generatePage(TEMPLATE_FILENAME, variables);
    }
}
