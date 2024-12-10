package com.nqbao.project.configuration.redis;
import com.nqbao.project.service.GenericService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Collections;

@Component
public class DynamicCacheResolver extends SimpleCacheResolver {
    public DynamicCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        if (context.getTarget() instanceof GenericService<?,?, ?> service) {
            String cacheName = service.getCacheName();
            Cache cache = super.getCacheManager().getCache(cacheName);
            if (cache != null) {
                return Collections.singleton(cache);
            } else {
                throw new IllegalArgumentException("Cache with name '" + cacheName + "' not found.");
            }
        }
        return Collections.emptyList();
    }
}