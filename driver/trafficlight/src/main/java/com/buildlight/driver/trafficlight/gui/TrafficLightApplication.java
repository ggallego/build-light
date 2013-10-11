package com.buildlight.driver.trafficlight.gui;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.springframework.util.StopWatch;

import com.buildlight.driver.trafficlight.api.Led;
import com.buildlight.driver.trafficlight.api.TrafficLight;
import com.buildlight.driver.trafficlight.api.TrafficLightFactory;
import com.buildlight.driver.trafficlight.commandline.parser.ParserException;

/**
 * @author zutherb
 * @author ggallego
 */
public class TrafficLightApplication extends Application {

    private static final TrafficLight TRAFFIC_LIGHT = TrafficLightFactory.getInstance();

    private static Map<Led, Boolean> LED_STATES;

    static {
        LED_STATES = new HashMap<Led, Boolean>();
        for (Led led : Led.values()) {
            LED_STATES.put(led, Boolean.FALSE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TRAFFIC_LIGHT.switchOffAllLeds();

        VBox vbox = vBox();
        vbox.setStyle("-fx-background-color: " + "gray");

        int hsize = 0;
        switch (TRAFFIC_LIGHT.getDevice()) {
		case CLEWARE:
	        vbox.getChildren().addAll(
        		buttonForLed(Led.RED), 
        		buttonForLed(Led.YELLOW), 
        		buttonForLed(Led.GREEN));
	        hsize = 300;
			break;
        default:
        	vbox.getChildren().addAll(
        			buttonForLed(Led.BLUE),
            		buttonForLed(Led.RED), 
            		buttonForLed(Led.GREEN),
        			buttonForLed(Led.AQUA),
        			buttonForLed(Led.PURPLE),
        			buttonForLed(Led.YELLOW), 
        			buttonForLed(Led.WHITE));
            hsize = 550;
			break;
    	}
        vbox.getChildren().add(buttonKnightRider());

        primaryStage.setTitle("Traffic Light");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(vbox, 200, hsize));
        primaryStage.show();
    }

    private VBox vBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        return vbox;
    }

    private Button buttonForLed(final Led led) {
        Button redButton = new Button();
        redButton.setStyle("-fx-background-color: " + led.name().toLowerCase());
        redButton.setPrefSize(270, 70);
        redButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	switch (TRAFFIC_LIGHT.getDevice()) {
				case CLEWARE:
					if (!LED_STATES.get(led)) {
						TRAFFIC_LIGHT.switchOn(led);
						LED_STATES.put(led, Boolean.TRUE);
					} else {
						TRAFFIC_LIGHT.switchOff(led);
						LED_STATES.put(led, Boolean.FALSE);
					}
					break;
				default:
					TRAFFIC_LIGHT.switchOn(led);
					break;
            	}
            }
        });
        return redButton;
    }
    private Button buttonKnightRider() {
		Button redButton = new Button();
		redButton.setStyle("-fx-background-color: " + "pink");
		redButton.setText("KnightRider");
		redButton.setPrefSize(270, 70);
		redButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					long executionTime = 0;
					long sleepTime = 1000;
					Led[] leds = Led.values();
					int moveCounter = 0;
					boolean moveForward = true;
					StopWatch stopWatch = new StopWatch();
					while (executionTime == 0
							|| stopWatch.getTotalTimeMillis() < executionTime) {
						stopWatch.start();
						// TRAFFIC_LIGHT.switchOffAllLeds();
						if (moveForward) {
							TRAFFIC_LIGHT.switchOn(leds[moveCounter++]);
						} else {
							TRAFFIC_LIGHT.switchOn(leds[--moveCounter]);
						}
						if (moveCounter == 0) {
							moveCounter++;
							moveForward = true;
						}
						if (moveCounter == leds.length) {
							moveForward = false;
							--moveCounter;
						}
						Thread.sleep(sleepTime);
						stopWatch.stop();
					}
				} catch (Exception e) {
					throw new ParserException(e);
				}
			}
		});
		return redButton;
	}

    @Override
    public void stop() throws Exception {
    	TRAFFIC_LIGHT.switchOffAllLeds();
        TRAFFIC_LIGHT.close();
        System.exit(0);
    }
}
