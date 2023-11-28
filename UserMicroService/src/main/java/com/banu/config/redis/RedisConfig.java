package com.banu.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories // redisi database oolarak kullanacağımızı belirtiyoruz
@EnableCaching // Cach mekanizmasını aktif ediyoruz
public class RedisConfig {
    @Value("${usermicroservice.redis.host}")
    private String host;

    @Value("${usermicroservice.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
    }
}
