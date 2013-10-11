package com.buildlight.application.interrogator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buildlight.respository.bamboo.api.BambooRepository;
import com.buildlight.respository.bamboo.model.BambooPlanResponse;
import com.buildlight.respository.bamboo.model.BambooResultResponse;
import com.buildlight.respository.bamboo.model.Result;

/**
 * @author zutherb
 */
@Component
public class BambooBuildInterrogator implements BuildInterrogator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BambooBuildInterrogator.class);
	
    @Autowired(required = false)
    private BambooRepository bambooRepository;

    @Override
    public boolean isResponsible() {
        return bambooRepository != null;
    }

    @Override
    public BuildState getCurrentBuildState() {
        BambooPlanResponse planResponse = bambooRepository.getPlanResponse();
        if (planResponse.isBuilding()) {
        	LOGGER.debug("Plan is building");
            return BuildState.Building;
        }
        BambooResultResponse bambooResultResponse = bambooRepository.getResultResponse();
        List<Result> results = bambooResultResponse.getResults().getResults();
        Result result = results.get(0);
        LOGGER.debug("Plan result is " + result);
        switch (result.getState()) {
            case Successful:
                return BuildState.Successful;
            default:
                return BuildState.Failed;
        }
    }
}
