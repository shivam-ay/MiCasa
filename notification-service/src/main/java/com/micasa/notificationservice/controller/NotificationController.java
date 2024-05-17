package com.micasa.notificationservice.controller;

import com.micasa.notificationservice.constants.NotificationConstants;
import com.micasa.notificationservice.service.ManageConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for notification service.
 * Structure:
 *  1. stopNotificationConsumer(@PathVariable String, @PathVariable long) : Put method to stop a consumer for a certain time.
 *  2. startNotificationConsumer(@PathVariable String) : Put method to start a consumer.
 */
@Slf4j
@RestController
@RequestMapping(NotificationConstants.NOTIFICATION_ENDPOINT)
public class NotificationController
{
    //ManageConsumerService instance
    private final ManageConsumerService manageConsumerService;

    @Autowired
    public NotificationController(ManageConsumerService manageConsumerService)
    {
        this.manageConsumerService = manageConsumerService;
    }

    /**
     * Method to stop a consumer for a certain time.
     * Structure:
     *  1. Use managerConsumerService to stop consumer.
     *  2. Return response.
     *
     * @param consumerId : ConsumerId that will be stopped.
     * @param time : Time period for which consumer will be stopped.
     * @return Response with status 200.
     */
    @PutMapping(NotificationConstants.STOP_CONSUMER_ENDPOINT)
    public ResponseEntity<Void> stopNotificationConsumer(@PathVariable String consumerId, @PathVariable long time)
    {
        log.debug("Received request to stop consumer with consumerId: {} and time: {}", consumerId, time);
        this.manageConsumerService.stopConsumer(consumerId,time);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Method to start a consumer.
     * Structure:
     *  1. Use managerConsumerService to start consumer.
     *  2. Return response.
     *
     * @param consumerId : ConsumerId that will be started.
     * @return Response with status 200.
     */
    @PutMapping(NotificationConstants.START_CONSUMER_ENDPOINT)
    public ResponseEntity<Void> startNotificationConsumer(@PathVariable String consumerId)
    {
        log.debug("Received request to start consumer with consumerId: {}", consumerId);
        this.manageConsumerService.startConsumer(consumerId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
