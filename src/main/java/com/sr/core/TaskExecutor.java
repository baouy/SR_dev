package com.sr.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by mmp on 17/10/16.
 */
public class TaskExecutor extends ThreadPoolTaskExecutor {

    public TaskExecutor() {
        this.setThreadNamePrefix("my-mvc-task-executor-");
        this.setCorePoolSize(5);
        this.setMaxPoolSize(50);
        this.setQueueCapacity(1000);
        this.setKeepAliveSeconds(60);
    }
}
