package dev.lampirg.confcurring.config;

import dev.lampirg.confcurring.factory.YamlPropertySourceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(value = "/config/single-thread.yaml", factory = YamlPropertySourceFactory.class)
class SingleThreadConfigurationTest {

    @Autowired
    @Qualifier("exportExecutor")
    private TaskExecutor exportExecutor;

    @Autowired
    @Qualifier("importExecutor")
    private TaskExecutor importExecutor;

    @Autowired
    private TaskExecutor qualifierlessTaskExecutor;

    @Test
    void isSync() {
        assertEquals(SyncTaskExecutor.class, exportExecutor.getClass());
        assertEquals(SyncTaskExecutor.class, importExecutor.getClass());
        assertEquals(SyncTaskExecutor.class, qualifierlessTaskExecutor.getClass());
    }

    @Test
    void isSame() {
        assertSame(exportExecutor, importExecutor);
        assertSame(qualifierlessTaskExecutor, exportExecutor);
    }
}