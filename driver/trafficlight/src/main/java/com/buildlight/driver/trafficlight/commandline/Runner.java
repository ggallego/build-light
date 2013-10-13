package com.buildlight.driver.trafficlight.commandline;

import com.buildlight.driver.trafficlight.commandline.parser.ArgumentBuffer;
import com.buildlight.driver.trafficlight.commandline.parser.CommandLineParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author zutherb
 */
public final class Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    private Runner() { /*NOOP*/ }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/com/buildlight/driver/trafficlight/spring-context.xml");
        try {
            ArgumentBuffer buffer = new ArgumentBuffer(args);
            applicationContext.getBean(CommandLineParser.class).execute(buffer);
        } catch (Exception e) {
            LOGGER.error("ERROR", e);
            applicationContext.close();
            System.exit(-1);
        } finally {
            if (applicationContext.isRunning()) {
                applicationContext.close();
            }
            System.exit(0);
        }
    }
}
