package com.buildlight.driver.trafficlight.commandline.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buildlight.driver.trafficlight.api.Led;
import com.buildlight.driver.trafficlight.api.TrafficLight;

/**
 * @author zutherb
 */
@Component
public class LedArgumentParser extends AbstractArgumentParser {

    @Autowired
    public LedArgumentParser(TrafficLight trafficLight) {
        super(trafficLight);
    }

    @Override
    public boolean isResponsible(ArgumentBuffer buffer) {
        return Led.valueOfIgnoreCaseOrNull(buffer.peek()) != null;
    }

    @Override
    public void execute(ArgumentBuffer buffer) {
        Led led = Led.valueOfIgnoreCaseOrNull(buffer.peek());
        String name = buffer.next();
        Mode mode = Mode.valueOfIgnoreCaseOrError(name);
        switch (mode) {
            case ON:
                trafficLight().switchOn(led);
                break;
            case OFF:
                trafficLight().switchOff(led);
                break;
            default:
                String msg = String.format("Postion %d: Led %s must be following <on | off>", buffer.position(), led.name());
                throw new ParserException(msg);
        }
    }

    private static enum Mode {
        ON,
        OFF,
        ERROR;

        public static Mode valueOfIgnoreCaseOrError(String name) {
            for (Mode mode : Mode.values()) {
                if (mode.name().equalsIgnoreCase(name)) {
                    return mode;
                }
            }
            return ERROR;
        }
    }
}
