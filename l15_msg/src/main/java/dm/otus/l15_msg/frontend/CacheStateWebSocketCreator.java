package dm.otus.l15_msg.frontend;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class CacheStateWebSocketCreator implements WebSocketCreator {
    private final FrontendService frontendService;

    public CacheStateWebSocketCreator(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        CacheStateWebSocket socket = new CacheStateWebSocket(frontendService);
        System.out.println("Socket created");
        return socket;
    }
}
