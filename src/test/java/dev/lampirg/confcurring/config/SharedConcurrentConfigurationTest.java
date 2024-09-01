package dev.lampirg.confcurring.config;

import dev.lampirg.confcurring.factory.YamlPropertySourceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(value = "/config/concurrent-shared.yaml", factory = YamlPropertySourceFactory.class)
class SharedConcurrentConfigurationTest {

    @Autowired
    @Qualifier("exportExecutor")
    private TaskExecutor exportExecutor;

    @Autowired
    @Qualifier("importExecutor")
    private TaskExecutor importExecutor;
    @Autowired
    @Qualifier("sharedExecutor")
    private TaskExecutor sharedExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void isThreadPooled() {
        assertEquals(ThreadPoolTaskExecutor.class, exportExecutor.getClass());
        assertEquals(ThreadPoolTaskExecutor.class, importExecutor.getClass());
        assertEquals(ThreadPoolTaskExecutor.class, sharedExecutor.getClass());
    }

    @Test
    void isSame() {
        assertSame(exportExecutor, importExecutor);
        assertSame(sharedExecutor, importExecutor);
    }

    @Test
    void noDefault() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> applicationContext.getBean(TaskExecutor.class));
    }

    @Test
    void sizeIsTen() {
        assertEquals(10, ((ThreadPoolTaskExecutor) exportExecutor).getMaxPoolSize());
    }
}