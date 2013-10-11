package com.buildlight.driver.trafficlight.commandline.parser;

import com.buildlight.driver.trafficlight.api.TrafficLight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author zutherb
 */
@Component
public final class HelpArgumentParser extends AbstractArgumentParser {

    private static final ClassPathResource HELP_FILE = new ClassPathResource("/com/buildlight/driver/trafficlight/commandline/parser/help.txt");
    private static final Logger LOGGER = LoggerFactory.getLogger(HelpArgumentParser.class);
    private static final String help = "--help";
    private static final String MESSAGE = "TrafficLight Java Driver, Maintained by B. Zuther and ggallego.\n" +
    		"Compatible with Cleware and DreamCheeky devices  \n" +
            "Send bug reports using https://github.com/zutherb/build-light/issues or \n" +
            "                       https://github.com/ggallego/build-light/issues\n" +
            "\n" +
            "Usage: trafficlight [--gui] [--knightrider] [--test] [--help] [color on|off] [wait time]\n" +
            "\n" +
            "  --gui          opens JavaFX GUI\n" +
            "  --help         prints out this message\n" +
            "  --test         runs traffic light test sequence\n" +
            "  --knightrider  runs Knight Rider sequence on the traffic light\n" +
            "\n" +
            "  color on|off   switches Led on or off\n" +
            "  wait time      waits defined time in ms before the next action is executed\n" +
            "\n" +
            "Examples:\n" +
            "  trafficlight --gui                     => opens JavaFX GUI\n" +
            "  trafficlight --test                    => runs Knight Rider sequence\n" +
            "  trafficlight --knightrider             => runs Knight Rider sequence\n" +
            "  trafficlight red on wait 500 red off   => switches on red led, wait 500 ms, switches off red led\n" +
            "  trafficlight red on yellow on green on => switches on all leds\n";

    @Autowired
    public HelpArgumentParser(TrafficLight light) {
        super(light);
    }

    @Override
    public boolean isResponsible(ArgumentBuffer buffer) {
        return help.equalsIgnoreCase(buffer.peek());
    }

    @Override
    public void execute(ArgumentBuffer buffer) {
        try {
            LOGGER.info(MESSAGE);
        } catch (Exception e) {
            throw new ParserException(e);
        }
    }
}
