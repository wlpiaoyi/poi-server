package com.icss.poie.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/17 19:37
 * {@code @version:}:       1.0
 */
@Configuration
public class CacheConfig {

    @Bean("cache_long")
    public Cache<String, Object> caffeineCacheLong() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(7, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(1000 * 100)
                // 缓存的最大条数
                .maximumSize(1000 * 1000)
                .build();
    }

    @Bean("cache_short")
    public Cache<String, Object> caffeineCacheShort() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(4, TimeUnit.HOURS)
                // 初始的缓存空间大小
                .initialCapacity(100 * 100)
                // 缓存的最大条数
                .maximumSize(100 * 1000)
                .build();
    }

    @Bean("cache_temp")
    public Cache<String, Object> caffeineCacheTemp() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(10, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(10 * 100)
                // 缓存的最大条数
                .maximumSize(10 * 1000)
                .build();
    }

}
