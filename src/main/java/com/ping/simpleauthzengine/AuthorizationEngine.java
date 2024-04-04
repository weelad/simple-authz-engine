package com.ping.simpleauthzengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface AuthorizationEngine {
    Decision evaluate(Map<String, Object> context);
}

class SimpleAuthorizationEngine implements AuthorizationEngine {

    SimpleAuthorizationEngine() {
    }

    private ConfigurationFile loadConfiguration() {
        try {
            System.out.println("Loading configuration file.");
            Resource resource = new ClassPathResource("/configuration.json");
            InputStream input = resource.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            ConfigurationFile loaded = objectMapper.readValue(input, ConfigurationFile.class);
            System.out.println("Successfully loaded configuration file " + loaded);
            return loaded;
        } catch (IOException ignored) {
            return null;
        }
    }

    @Override
    public Decision evaluate(Map<String, Object> context) {
        System.out.println("Beginning request context evaluation");

        ConfigurationFile configurationFile = loadConfiguration();

        List<Decision> decisions = configurationFile.rules.stream().map(r -> r.evaluate(context)).collect(Collectors.toList());
        System.out.println("Evaluated the rules " + decisions);

        Decision decision = decisions.stream().noneMatch(Decision.DENY::equals) ? Decision.PERMIT : Decision.DENY;
        System.out.println("Reduced the decision " + decision);

        System.out.println("Ending request context evaluation");
        return decision;
    }
}

class ConfigurationFile {
    public List<Rule> rules;
}
