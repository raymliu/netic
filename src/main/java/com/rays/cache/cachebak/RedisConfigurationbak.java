/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:artemis
 * Package Name:com.handchina.yunmart.artemis.config Date:2017/4/1 0001 Create
 * By:zongyun.zhou@hand-china.com
 *//*

package com.handchina.yunmart.artemis.config;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration extends CachingConfigurerSupport {

    private final RedisProperties properties;
    private final RedisSentinelConfiguration sentinelConfiguration;
    private final RedisClusterConfiguration clusterConfiguration;

    public RedisConfiguration(RedisProperties properties,
                              ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider,
                              ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider) {

        this.properties = properties;
        this.sentinelConfiguration =
                (RedisSentinelConfiguration) sentinelConfigurationProvider.getIfAvailable();
        this.clusterConfiguration =
                (RedisClusterConfiguration) clusterConfigurationProvider.getIfAvailable();
    }

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }

    public JedisConnectionFactory redisConnectionFactory() throws UnknownHostException {
        return this.applyProperties(this.createJedisConnectionFactory());
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        RedisProperties.Pool props = this.properties.getPool();
        config.setMaxTotal(props.getMaxActive());
        config.setMaxIdle(props.getMaxIdle());
        config.setMinIdle(props.getMinIdle());
        config.setMaxWaitMillis((long) props.getMaxWait());
        return config;
    }

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        } else {
            RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
            if (sentinelProperties != null) {
                RedisSentinelConfiguration config = new RedisSentinelConfiguration();
                config.master(sentinelProperties.getMaster());
                config.setSentinels(this.createSentinels(sentinelProperties));
                return config;
            } else {
                return null;
            }
        }
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        ArrayList nodes = new ArrayList();
        String[] var3 = StringUtils.commaDelimitedListToStringArray(sentinel.getNodes());
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String node = var3[var5];

            try {
                String[] ex = StringUtils.split(node, ":");
                Assert.state(ex.length == 2, "Must be defined as \'host:port\'");
                nodes.add(new RedisNode(ex[0], Integer.valueOf(ex[1]).intValue()));
            } catch (RuntimeException var8) {
                throw new IllegalStateException("Invalid redis sentinel property \'" + node + "\'", var8);
            }
        }

        return nodes;
    }

    private JedisConnectionFactory createJedisConnectionFactory() {
        JedisPoolConfig poolConfig =
                this.properties.getPool() != null ? this.jedisPoolConfig() : new JedisPoolConfig();
        return this.getSentinelConfig() != null
                ? new JedisConnectionFactory(this.getSentinelConfig(), poolConfig)
                : (this.getClusterConfiguration() != null
                ? new JedisConnectionFactory(this.getClusterConfiguration(), poolConfig)
                : new JedisConnectionFactory(poolConfig));
    }

    protected final RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        } else if (this.properties.getCluster() == null) {
            return null;
        } else {
            RedisProperties.Cluster clusterProperties = this.properties.getCluster();
            RedisClusterConfiguration config =
                    new RedisClusterConfiguration(clusterProperties.getNodes());
            if (clusterProperties.getMaxRedirects() != null) {
                config.setMaxRedirects(clusterProperties.getMaxRedirects().intValue());
            }

            return config;
        }
    }

    protected final JedisConnectionFactory applyProperties(JedisConnectionFactory factory) {
        factory.setHostName(this.properties.getHost());
        factory.setPort(this.properties.getPort());
        if (this.properties.getPassword() != null) {
            factory.setPassword(this.properties.getPassword());
        }

        factory.setDatabase(this.properties.getDatabase());
        if (this.properties.getTimeout() > 0) {
            factory.setTimeout(this.properties.getTimeout());
        }

        return factory;
    }

    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
        return cacheManager;
    }

    @Bean(name = "redisTemplate")
    public StringRedisTemplate stringRedisTemplate() throws UnknownHostException {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(this.redisConnectionFactory());
        return redisTemplate;
    }
}
*/
