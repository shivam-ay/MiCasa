package com.micasa.notificationservice.constants;

/**
 * Class to hold constants that will be used with in notification service.
 */
public class NotificationConstants
{
    public static final String NOTIFICATION_ENDPOINT = "/notification";
    public static final String STOP_CONSUMER_ENDPOINT ="/consumer:stop/{consumerId}/{time}";
    public static final String START_CONSUMER_ENDPOINT = "/consumer:start/{consumerId}";
    public static final String UNABLE_TO_STOP = "Unable to stop consumer with consumerId: ";
    public static final String CONSUMER_NOT_FOUND = "Consumer not found with consumerId: ";
    public static final String CORRELATION_ID_HEADER = "correlationId";

    private NotificationConstants() {}
}
