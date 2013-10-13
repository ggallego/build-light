package com.buildlight.application.commandline;

import static com.buildlight.application.ConfigurationFile.BAMBOO_DEFAULT_CONFIGURATION;
import static com.buildlight.application.ConfigurationFile.CONFIGURATION_FILE;
import static com.buildlight.application.ConfigurationFile.JENKINS_DEFAULT_CONFIGURATION;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author zutherb
 */
public final class Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    private Runner() {/* NOOP */}

    public static void main(String[] args) throws IOException {
        System.out.println("\tBuild Light - Build Watch Application, Maintained by B. Zuther.");
        System.out.println("\tSend bug reports using https://github.com/zutherb/build-light/issues");
        if (CONFIGURATION_FILE.exists()) {
            Properties properties = new Properties();
            properties.load(FileUtils.openInputStream(CONFIGURATION_FILE));

            String builderServer = properties.getProperty("build.server");
            System.setProperty("spring.profiles.active", builderServer.toLowerCase());
            System.setProperty("buildlight.property.file", CONFIGURATION_FILE.getAbsolutePath());

            LOGGER.info(String.format("Buildlight started in %s Mode", builderServer));

            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/com/buildlight/application/spring-context.xml");

            Scanner scanner = new Scanner(System.in);
            String next = null;
            while (!"exit".equalsIgnoreCase(next)) {
                LOGGER.info("Type 'exit' and press enter to exit the application:");
                next = scanner.next();
            }
            context.close();
            scanner.close();
            System.exit(0);
        } else {
        	System.out.println("\tPlease create {"+CONFIGURATION_FILE.getAbsoluteFile()+"}");
        	System.out.println("\tJenkins example:");
        	System.out.println(JENKINS_DEFAULT_CONFIGURATION);
        	System.out.println("\tBamboo example:");
        	System.out.println(BAMBOO_DEFAULT_CONFIGURATION);
        }
    }
}
