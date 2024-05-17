package com.micasa.notificationservice.util;

import org.slf4j.MDC;

import java.util.Map;

/**
 * Interface to propagate context to child threads.
 * Structure:
 *  1. run() : Sets parent thread context to new thread.
 *  2. execute() : needs to be implemented.
 */
public interface ContextAwareTask extends Runnable
{
    /*
     * Map to hold parent context.
     */
    Map<String,String> contextMap = MDC.getCopyOfContextMap();

    /**
     * Method to propagate context.
     * Structure:
     *  1. Set context to current thread.
     *  2. Call execute method.
     */
    @Override
    default void run()
    {
        MDC.setContextMap(contextMap);
        this.execute();
    }

    /**
     * Needs to be implemented.
     */
    void execute();
}
