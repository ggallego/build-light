package com.buildlight.driver.trafficlight.commandline.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buildlight.driver.trafficlight.api.TrafficLight;

/**
 * @author zutherb
 */
@Component
public final class HelpArgumentParser extends AbstractArgumentParser {

    private static final String help = "--help";

    private static final String MESSAGE = 
    		"\tTrafficLight Java Driver, Maintained by B. Zuther \n"+
            "\t                          Dream Cheeky port by Glauber Gallego.\n" +
    		"\tCompatible with Cleware and DreamCheeky devices  \n" +
            "\tSend bug reports using https://github.com/zutherb/build-light/issues or \n" +
            "\t                       https://github.com/ggallego/build-light/issues\n" +
            "\n" +
            "\tUsage: trafficlight [--gui] [--knightrider] [--test] [--help] [color on|off] [wait time]\n" +
            "\t  --gui          opens JavaFX GUI\n" +
            "\t  --help         prints out this message\n" +
            "\t  --test         runs traffic light test sequence\n" +
            "\t  --knightrider  runs Knight Rider sequence on the traffic light\n" +
            "\t  color on|off   switches Led on or off\n" +
            "\t  wait time      waits defined time in ms before the next action is executed\n" +
            "\tExamples:\n" +
            "\t  trafficlight --gui                     => opens JavaFX GUI\n" +
            "\t  trafficlight --test                    => runs Test sequence\n" +
            "\t  trafficlight --knightrider             => runs Knight Rider sequence\n" +
            "\t  trafficlight red on wait 500 red off   => switches on red led, wait 500 ms, switches off red led\n" +
            "\t  trafficlight red on yellow on green on => switches on all leds\n";

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
            System.out.println(MESSAGE);
        } catch (Exception e) {
            throw new ParserException(e);
        }
    }
}
