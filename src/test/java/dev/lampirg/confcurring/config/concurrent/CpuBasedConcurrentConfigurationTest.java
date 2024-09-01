package dev.lampirg.confcurring.config.concurrent;


import dev.lampirg.confcurring.factory.YamlPropertySourceFactory;
import dev.lampirg.confcurring.inner.OperationSystemInformationSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(value = "/config/concurrent-single-cpu.yaml", factory = YamlPropertySourceFactory.class)
public class CpuBasedConcurrentConfigurationTest {
    @Nested
    class With100Cores {

        @Autowired
        @Qualifier("exportExecutor")
        private TaskExecutor exportExecutor;

        @Autowired
        @Qualifier("importExecutor")
        private TaskExecutor importExecutor;
        @TestConfiguration
        static class OperationSystemMock {
            @Bean
            public OperationSystemInformationSystem operationSystemInformationSystem() {
                OperationSystemInformationSystem mock = Mockito.mock(OperationSystemInformationSystem.class);
                Mockito.lenient().when(mock.availableProcessors()).thenReturn(100);
                return mock;
            }
        }

        @Test
        void correctSize() {
            Assertions.assertEquals(30, ((ThreadPoolTaskExecutor) exportExecutor).getMaxPoolSize());
            Assertions.assertEquals(70, ((ThreadPoolTaskExecutor) importExecutor).getMaxPoolSize());
        }
    }


}
