package dev.lampirg.confcurring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@ConditionalOnProperty(value = "concurrent.single-thread")
public class SingleThreadConfiguration {

    @Bean
    public TaskExecutor syncTaskExecutor() {
        return new SyncTaskExecutor();
    }

}
