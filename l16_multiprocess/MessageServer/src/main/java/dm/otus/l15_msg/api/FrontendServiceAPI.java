package dm.otus.l15_msg.api;

public interface FrontendServiceAPI {
    void sendCacheInfo(long requestId, long cacheHits, long cacheMiss);
}
