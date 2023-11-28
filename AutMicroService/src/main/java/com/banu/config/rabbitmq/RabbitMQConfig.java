package com.banu.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /*
     * hangi excahange türünde gidecek
     * hangi kuyduğa
     * hangi bindin key ile gidecek
     * 1 -> gerekli tanımlamalar
     * 2 -> Exchange Türü Seçimi
     * 3 -> Kıyruk Olusturma
     * 4 -> Kuyruk ile Exchange'i birbirine bağlama
     */

    private final String EXCHANGE_AUTH = "exchange-auth";
    private final String BINDING_KEY = "key-auth";
    private final String AUTH_QUEUE = "queue-auth-create-user";

    @Bean //bizim icin nesne yaratıyorlar ve spring contexin icine yerlestirir
    DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_AUTH);
    }

    @Bean
    Queue queueAuthCreateUser(){
        return new Queue(AUTH_QUEUE);
    }

    @Bean
    public Binding bindingCreateUser(final DirectExchange directExchange, final Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with(BINDING_KEY);
    }
}
