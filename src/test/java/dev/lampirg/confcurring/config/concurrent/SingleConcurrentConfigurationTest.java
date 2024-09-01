package dev.lampirg.confcurring.config.concurrent;

import dev.lampirg.confcurring.confprop.ConcurrentProperties;
import dev.lampirg.confcurring.factory.YamlPropertySourceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(value = "/config/concurrent-single.yaml", factory = YamlPropertySourceFactory.class)
class SingleConcurrentConfigurationTest {

    @Autowired
    @Qualifier("exportExecutor")
    private TaskExecutor exportExecutor;

    @Autowired
    @Qualifier("importExecutor")
    private TaskExecutor importExecutor;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ConcurrentProperties concurrentProperties;

    @Test
    void correctType() {
        assertEquals(ThreadPoolTaskExecutor.class, exportExecutor.getClass());
        assertEquals(ThreadPoolTaskExecutor.class, exportExecutor.getClass());
    }

    @Test
    void correctSize() {
        assertEquals(3, ((ThreadPoolTaskExecutor) exportExecutor).getMaxPoolSize());
        assertEquals(7, ((ThreadPoolTaskExecutor) importExecutor).getMaxPoolSize());
    }

    @Test
    void notSame() {
        assertNotSame(exportExecutor, importExecutor);
    }

    @Test
    void noDefault() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> applicationContext.getBean(TaskExecutor.class));
    }

    @Test
    void noShared() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean("sharedExecutor"));
    }
}
