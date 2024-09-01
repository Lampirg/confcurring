package dev.lampirg.confcurring.confprop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("concurrent")
@Getter
@Setter
public class ConcurrentProperties {

    private boolean isSingleThread;
    private PoolProperties importPool;
    private PoolProperties exportPool;
    private PoolProperties sharedPool;

}
