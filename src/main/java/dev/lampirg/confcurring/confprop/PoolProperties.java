package dev.lampirg.confcurring.confprop;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoolProperties {

    private PoolMode poolMode;
    private UnitMode unitMode;
    private int numberOfThreads;

}
