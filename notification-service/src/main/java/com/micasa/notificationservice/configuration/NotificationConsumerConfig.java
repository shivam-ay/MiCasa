package com.micasa.notificationservice.configuration;

import com.micasa.notificationservice.dto.EmailDto;
import com.micasa.notificationservice.service.NotificationConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for notification consumers.
 */
@Slf4j
@EnableKafka
@Configuration
public class NotificationConsumerConfig
{
    @Bean
    protected Map<String,Object> consumerConfigs()
    {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, EmailDto> consumerFactory(Map <String,Object> props)
    {
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(EmailDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,EmailDto> notificationListenerConsumerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, EmailDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> props = this.consumerConfigs();
        factory.setConsumerFactory(consumerFactory(props));
        return factory;
    }

    @Bean(name = "email-listener")
    public NotificationConsumer emailConsumer()
    {
        return new NotificationConsumer();
    }
}
