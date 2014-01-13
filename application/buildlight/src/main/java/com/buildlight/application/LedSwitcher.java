package com.buildlight.application;

import com.buildlight.application.interrogator.BambooBuildInterrogator;
import com.buildlight.application.interrogator.BuildInterrogator;
import com.buildlight.application.interrogator.BuildState;
import com.buildlight.driver.trafficlight.api.TrafficLight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zutherb
 */
@Component
public class LedSwitcher {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LedSwitcher.class);

	private TrafficLight light;
	private BuildState lastChangedBuildState;
	private List<BuildInterrogator> buildInterrogators;

	@Autowired
	public LedSwitcher(TrafficLight light,
			List<BuildInterrogator> buildInterrogators) {
		this.light = light;
		this.buildInterrogators = buildInterrogators;
	}

	@Scheduled(fixedDelay = 1000)
	public void changeTrafficLightLed() {
		for (BuildInterrogator buildInterrogator : buildInterrogators) {
			if (buildInterrogator.isResponsible()) {
				BuildState currentBuildState = buildInterrogator
						.getCurrentBuildState();
				changeLedIfNessary(currentBuildState);
			}
		}
	}

	private void changeLedIfNessary(BuildState currentBuildState) {
    		try {
	        if (!currentBuildState.equals(lastChangedBuildState) || BuildState.Building.equals(currentBuildState)) {
	            lastChangedBuildState = currentBuildState;
	            light.switchOffAllLeds();
	            LOGGER.info("state: "+lastChangedBuildState+", Color Device: "+currentBuildState.getLed().toString());
	            
	            if(lastChangedBuildState.equals(BuildState.Building)){
		         
		            	light.switchOffAllLeds();
						Thread.sleep(500);
						light.switchOn(currentBuildState.getLed());
						Thread.sleep(500);
					
	            }else if(lastChangedBuildState.equals(BuildState.Successful)){
	            	light.switchOn(currentBuildState.getLed());
	            	Thread.sleep(5000);				
	            	light.switchOffAllLeds();
	            }else if(lastChangedBuildState.equals(BuildState.Failed)){
	            	 light.switchOn(currentBuildState.getLed());
	            }	            
	        }
        } catch (InterruptedException e) {			
			e.printStackTrace();
		}
        
       
    }

}
