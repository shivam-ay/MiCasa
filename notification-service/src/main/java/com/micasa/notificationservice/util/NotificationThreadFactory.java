package com.micasa.notificationservice.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread factory for notification service.
 * Structure:
 *  1.newThread(Runnable) : Get a new thread.
 *  2.createThread(Runnable) : Creates a new thread.
 *  3.getThreadName() : Create a new name for thread.
 */
public class NotificationThreadFactory implements ThreadFactory
{
    //Thread name.
    private final String threadName;
    //Thread count.
    private final AtomicInteger threadCount = new AtomicInteger(0);
    //Is daemon thread.
    private final boolean isDaemon;

    public NotificationThreadFactory(Class<?> className)
    {
        this(className,false);
    }

    public NotificationThreadFactory(Class<?> className, boolean isDaemon)
    {
        this.threadName = className.getSimpleName();
        this.isDaemon = isDaemon;
    }

    /**
     * Gets a new thread.
     * Structure:
     *  1. Call createThread(Runnable) to get new thread.
     *  2. Return new thread.
     * @param runnable : A runnable to be executed by new thread instance
     * @return New thread.
     */
    @Override
    public Thread newThread(Runnable runnable)
    {
        return createThread(runnable);
    }

    /**
     * Creates a new thread.
     * Structure:
     *  1. Create new thread.
     *  2. Return new thread.
     * @param runnable : A runnable to be executed by new thread instance
     * @return New thread.
     */
    private Thread createThread(Runnable runnable)
    {
        Thread thread = new Thread(runnable);
        thread.setPriority(5);
        thread.setName(this.getThreadName());
        thread.setDaemon(this.isDaemon);
        return thread;
    }

    /**
     * Creates a new name for thread.
     * Structure:
     *  1. Use threadName and threadCount to create a new thread.
     * @return New thread name.
     */
    private String getThreadName()
    {
        return this.threadName +"-"+this.threadCount.incrementAndGet();
    }
}
