package dm.otus.l15_msg.frontend;

import dm.otus.l15_msg.api.FrontendServiceAPI;

interface FrontendService extends FrontendServiceAPI {
    void requestCacheInfo(CacheStateWebSocket cacheStateWebSocket);
    void sendCacheInfo(long requestId, long cacheHits, long cacheMiss);
}
