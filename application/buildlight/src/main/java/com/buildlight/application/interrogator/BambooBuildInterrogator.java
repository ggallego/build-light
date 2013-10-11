package com.buildlight.application.interrogator;

import com.buildlight.respository.bamboo.api.BambooRepository;
import com.buildlight.respository.bamboo.model.BambooPlanResponse;
import com.buildlight.respository.bamboo.model.BambooResultResponse;
import com.buildlight.respository.bamboo.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zutherb
 */
@Component
public class BambooBuildInterrogator implements BuildInterrogator {

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
            return BuildState.Building;
        }
        BambooResultResponse bambooResultResponse = bambooRepository.getResultResponse();
        List<Result> results = bambooResultResponse.getResults().getResults();
        Result result = results.get(0);
        switch (result.getState()) {
            case Successful:
                return BuildState.Successful;
            default:
                return BuildState.Failed;
        }
    }
}
