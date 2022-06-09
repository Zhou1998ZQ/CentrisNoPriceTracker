package com.zzq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmqpConfig {

    public static final String QUEUE_CENTRIS = "queue.centris";
    public static final String EXCHANGE_CENTRIS = "exchange.centris";
    public final static String ROUTING_KEY_CENTRIS  = "centris";

    @Bean
    MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue queueCentris() {
        return QueueBuilder
                .durable(QUEUE_CENTRIS)
                .build();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_CENTRIS).build();
    }

    @Bean
    public Binding bindingWithInfo() {
        return BindingBuilder.bind(queueCentris()).to(exchange()).with(ROUTING_KEY_CENTRIS).noargs();
    }
}
