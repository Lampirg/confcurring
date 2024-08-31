package dev.lampirg.confcurring.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "concurrent.single-thread=false")
class ConcurrentConfigurationTest {

    @Autowired
    @Qualifier("exportExecutor")
    private TaskExecutor exportExecutor;

    @Autowired
    @Qualifier("importExecutor")
    private TaskExecutor importExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void isThreadPooled() {
        assertEquals(exportExecutor.getClass(), ThreadPoolTaskExecutor.class);
        assertEquals(importExecutor.getClass(), ThreadPoolTaskExecutor.class);
    }

    @Test
    void isNotSame() {
        assertNotSame(exportExecutor, importExecutor);
    }

    @Test
    void noDefault() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> applicationContext.getBean(TaskExecutor.class));
    }
}