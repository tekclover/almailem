package com.almailem.ams.api.connector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {
    @Bean(name = "asyncTaskExecutor")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(12);
        taskExecutor.setQueueCapacity(150);
        taskExecutor.setMaxPoolSize(12);
        taskExecutor.setThreadNamePrefix("AsyncTaskThread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}