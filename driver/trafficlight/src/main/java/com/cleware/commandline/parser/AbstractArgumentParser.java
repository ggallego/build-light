package com.cleware.commandline.parser;

import com.cleware.driver.TrafficLight;

/**
 * @author zutherb
 */
public abstract class AbstractArgumentParser implements ArgumentParser {

    private TrafficLight trafficLight;

    public AbstractArgumentParser(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public TrafficLight trafficLight() {
        return trafficLight;
    }
}
