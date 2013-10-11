package com.buildlight.application.interrogator;

import com.buildlight.respository.jenkins.api.JenkinsRepository;
import com.buildlight.respository.jenkins.model.JenkinsBuildResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zutherb
 */
@Component
public class JenkinsBuildInterrogator implements BuildInterrogator {

	private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsBuildInterrogator.class);

	@Autowired(required = false)
    private JenkinsRepository jenkinsRepository;

    @Override
    public boolean isResponsible() {
        return jenkinsRepository != null;
    }

    @Override
    public BuildState getCurrentBuildState() {
        JenkinsBuildResponse buildResponse = jenkinsRepository.getBuildResponse();
        BuildState state = getCurrentBuildState(buildResponse);
        LOGGER.debug("Plan result is " + state);
        return state;
    }

    private BuildState getCurrentBuildState(JenkinsBuildResponse buildResponse) {
        switch (buildResponse.getColor()) {
            case red:
            case yellow:
            case aborted:
                return BuildState.Failed;
            case blue:
                return BuildState.Successful;
            default:
                return BuildState.Building;
        }
    }
}
