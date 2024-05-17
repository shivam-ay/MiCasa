package com.micasa.notificationservice.service;

import com.micasa.notificationservice.constants.NotificationConstants;
import com.micasa.notificationservice.exception.InternalServerException;
import com.micasa.notificationservice.util.ContextAwareTask;
import com.micasa.notificationservice.util.NotificationThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service class to start and stop consumer.
 * Structure:
 *  1. stopConsumer(String, Long) : Stops a consumer for a certain time then start again.
 *  2. stopConsumer(String) : Stops a consumer.
 *  3. startConsumer(String) : Starts a consumer.
 */
@Slf4j
@Service
public class ManageConsumerService
{
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final ExecutorService executorService;

    @Autowired
    public ManageConsumerService(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry)
    {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.executorService = Executors.newSingleThreadExecutor(new NotificationThreadFactory(ManageConsumerService.class));
    }

    /**
     * This method stops consumer with passed consumerId for a certain time.
     * Structure:
     *  1. Stop consumer.
     *  2. Submit task to start consumer.
     *  3. Return.
     * @param consumerId : ConsumerId against which consumer will be stopped.
     * @param time : time for which consumer will be stopped.
     */
    public void stopConsumer(String consumerId,long time)
    {
        log.info("Stopping consumer with id {} for {} seconds", consumerId, time);
        this.stopConsumer(consumerId);
        ContextAwareTask contextAwareTask = () -> {
            try
            {
                Thread.sleep(time * 1000);
                this.startConsumer(consumerId);
            }
            catch (InterruptedException exception)
            {
                if (Thread.currentThread().isInterrupted())
                {
                    log.error("Something went wrong while starting consumer", exception);
                }
            }
        };
        this.executorService.submit(contextAwareTask);
    }

    /**
     * This method stops consumer with passed consumerId.
     * Structure:
     *  1. Stop consumer.
     *  2. Return.
     * @param consumerId : ConsumerId against which consumer will be stopped.
     */
    private void stopConsumer(String consumerId)
    {
        Optional<MessageListenerContainer> listenerContainerOptional = Optional.ofNullable(
                this.kafkaListenerEndpointRegistry.getListenerContainer(consumerId));
        listenerContainerOptional.ifPresentOrElse(listenerContainer -> {
            listenerContainer.stop();
            log.info("Stopped consumer with id {}", consumerId);
        }, () -> {
            throw InternalServerException.builder()
                    .timestamp(System.currentTimeMillis())
                    .fieldName("consumerId")
                    .fieldValue(consumerId)
                    .moreInfo(NotificationConstants.CONSUMER_NOT_FOUND+consumerId)
                    .message(NotificationConstants.UNABLE_TO_STOP+consumerId)
                    .build();
        });
    }

    /**
     * This method starts consumer with passed consumerId.
     * Structure:
     *  1. Start consumer.
     *  2. Return.
     * @param consumerId : ConsumerId against which consumer will be started.
     */
    public void startConsumer(String consumerId)
    {
        Optional<MessageListenerContainer> listenerContainerOptional = Optional.ofNullable(
                this.kafkaListenerEndpointRegistry.getListenerContainer(consumerId));
        listenerContainerOptional.ifPresentOrElse(listenerContainer -> {
            listenerContainer.start();
            log.info("Started consumer with id {}", consumerId);
        }, () -> {
            throw InternalServerException.builder()
                    .timestamp(System.currentTimeMillis())
                    .fieldName("consumerId")
                    .fieldValue(consumerId)
                    .moreInfo(NotificationConstants.CONSUMER_NOT_FOUND+consumerId)
                    .message(NotificationConstants.UNABLE_TO_STOP+consumerId)
                    .build();
        });
    }

}
