package dev.kangoo.configuration;

import io.smallrye.config.ConfigMapping;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigMapping(prefix = "github.api")
public interface GitHubApiConfig {

    String username();
    String repository();
    String path();
}
