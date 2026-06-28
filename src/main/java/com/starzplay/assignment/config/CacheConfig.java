package com.starzplay.assignment.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * In-memory caching (Caffeine) for the read-heavy GET endpoints. The caches are
 * evicted whenever payment methods are created or updated, so reads stay fast
 * without serving stale data.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String PAYMENT_METHODS = "paymentMethods";
    public static final String PAYMENT_PLANS_BY_DURATION = "paymentPlansByDuration";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                PAYMENT_METHODS, PAYMENT_PLANS_BY_DURATION);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(10))
                .maximumSize(1_000));
        return cacheManager;
    }
}
