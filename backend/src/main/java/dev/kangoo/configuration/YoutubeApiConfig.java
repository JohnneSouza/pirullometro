package dev.kangoo.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class YoutubeApiConfig {

    @ConfigProperty(name = "youtube.api.key")
    String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
