package com.buildlight.driver.trafficlight.commandline.parser;

import com.buildlight.driver.trafficlight.api.TrafficLight;

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
