package dev.lampirg.confcurring.inner;

import org.springframework.stereotype.Service;

@Service
public class OperationSystemInformationSystem {

    public int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

}
