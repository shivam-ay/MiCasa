package com.micasa.notificationservice.service;

import com.micasa.notificationservice.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class EmailConsumer
{

    @KafkaListener(topics = {"email-topic"})
    public void consumerEmail(ConsumerRecord<String,EmailDto> record)
    {
        String topic = record.topic();
        log.info("Msg read from topic: {}",topic);
        EmailDto emailDto = record.value();
        log.info("Email Body: {}", emailDto.getBody());
    }
}
