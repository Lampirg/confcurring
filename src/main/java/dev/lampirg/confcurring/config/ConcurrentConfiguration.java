package dev.lampirg.confcurring.config;

import dev.lampirg.confcurring.confprop.ConcurrentProperties;
import dev.lampirg.confcurring.confprop.PoolMode;
import dev.lampirg.confcurring.confprop.PoolProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Optional;

@Configuration
@ConditionalOnProperty(value = "concurrent.single-thread", havingValue = "false")
@RequiredArgsConstructor
public class ConcurrentConfiguration {


    private final ConcurrentProperties concurrentProperties;


    @Bean
    @ConditionalOnExpression("${concurrent.shared-pool.number-of-threads} > 0")
    public TaskExecutor sharedExecutor() {
        PoolProperties sharedPool = concurrentProperties.getSharedPool();
        return createTaskExecutor(sharedPool.getNumberOfThreads());
    }

    @Bean
    public TaskExecutor exportExecutor(@Qualifier("sharedExecutor") Optional<TaskExecutor> sharedExecutor) {
        PoolProperties exportPool = concurrentProperties.getExportPool();
        if (exportPool == null) {
            return sharedExecutor.orElseThrow(() -> new NoSuchBeanDefinitionException(TaskExecutor.class));
        }
        return sharedExecutor
                .filter(te -> exportPool.getPoolMode() == PoolMode.SHARED)
                .orElseGet(() -> createTaskExecutor(exportPool.getNumberOfThreads()));
    }

    @Bean
    public TaskExecutor importExecutor(@Qualifier("sharedExecutor") Optional<TaskExecutor> sharedExecutor) {
        PoolProperties importPool = concurrentProperties.getImportPool();
        if (importPool == null) {
            return sharedExecutor.orElseThrow(() -> new NoSuchBeanDefinitionException(TaskExecutor.class));
        }
        return sharedExecutor
                .filter(te -> importPool.getPoolMode() == PoolMode.SHARED)
                .orElseGet(() -> createTaskExecutor(importPool.getNumberOfThreads()));
    }

    private TaskExecutor createTaskExecutor(int numberOfThreads) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(numberOfThreads);
        threadPoolTaskExecutor.setQueueCapacity(0);
        return threadPoolTaskExecutor;
    }

}
