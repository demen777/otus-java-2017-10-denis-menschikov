package dm.otus.l15_msg.frontend;

public interface FrontendService {
    void requestCacheInfo(CacheStateWebSocket cacheStateWebSocket);
    void sendCacheInfo(long requestId, long cacheHits, long cacheMiss);
}
