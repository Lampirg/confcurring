package dev.lampirg.confcurring.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "concurrent.single-thread=true")
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
        assertEquals(exportExecutor.getClass(), SyncTaskExecutor.class);
        assertEquals(importExecutor.getClass(), SyncTaskExecutor.class);
        assertEquals(qualifierlessTaskExecutor.getClass(), SyncTaskExecutor.class);
    }

    @Test
    void isSame() {
        assertSame(exportExecutor, importExecutor);
        assertSame(qualifierlessTaskExecutor, exportExecutor);
    }
}