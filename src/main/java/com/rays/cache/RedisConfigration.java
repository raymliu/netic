package com.rays.cache;

import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;



/**
 * Created by hand on 2017/4/18.
 */
@EnableCaching
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfigration {

    private final RedisProperties redisProperties ;
    //private final RedisSentinelConfiguration sentinelConfiguration; //主从部署，暂时不用


    public RedisConfigration(RedisProperties redisProperties
           // ,ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider
             ) {
        this.redisProperties = redisProperties;
        //this.sentinelConfiguration = (RedisSentinelConfiguration) sentinelConfigurationProvider.getIfAvailable();

    }

    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        RedisProperties.Pool props = redisProperties.getPool();
        jedisPoolConfig.setMaxTotal(props.getMaxActive());
        jedisPoolConfig.setMaxIdle(props.getMinIdle());
        jedisPoolConfig.setMinIdle(props.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis((long)props.getMaxWait());
        return jedisPoolConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        //JedisConnectionFactory jedisConnectionFactory = this.getSentinelConfig() != null
         //       ? new JedisConnectionFactory(this.getSentinelConfig(), poolConfig)
         //       : new JedisConnectionFactory(poolConfig);
        JedisPoolConfig poolConfig = this.redisProperties.getPool() != null ? this.jedisPoolConfig() : new JedisPoolConfig();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(redisProperties.getHost());
        jedisConnectionFactory.setPort(redisProperties.getPort());
        if (this.redisProperties.getPassword() != null) {
            jedisConnectionFactory.setPassword(this.redisProperties.getPassword());
        }
        jedisConnectionFactory.setDatabase(this.redisProperties.getDatabase());
        if (this.redisProperties.getTimeout() > 0) {
            jedisConnectionFactory.setTimeout(this.redisProperties.getTimeout());
        }
        return  jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(this.jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return  redisTemplate ;
    }

    @Bean
    public CacheManager cacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        cacheManager.setDefaultExpiration(3000);
        return cacheManager;

    }


    /*protected final RedisSentinelConfiguration getSentinelConfig() {
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
    }*/

}
