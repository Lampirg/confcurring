package dev.lampirg.confcurring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class ConcurrencyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final TaskExecutor exportExecutor;
    private final TaskExecutor importExecutor;


    public ConcurrencyService(@Qualifier("exportExecutor") TaskExecutor exportExecutor,
                              @Qualifier("importExecutor") TaskExecutor importExecutor) {
        this.exportExecutor = exportExecutor;
        this.importExecutor = importExecutor;
    }

    public void doExport() {
        exportExecutor.execute(() ->
                logger.info("Doing export. Thread name: {}", Thread.currentThread().getName())
        );
    }

    public void doImport() {
        importExecutor.execute(() ->
                logger.info("Doing import. Thread name: {}", Thread.currentThread().getName())
        );
    }
}
