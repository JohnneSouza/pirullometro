package dev.kangoo.rest;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
@RegisterProvider(GitHubRestClient.class)
public class GitHubAuthHeaderFilter implements ClientRequestFilter {

    @Inject
    @ConfigProperty(name = "github.api.key")
    String githubApiKey;

    @Override
    public void filter(ClientRequestContext requestContext) {
        requestContext.getHeaders().add("Authorization", "Bearer " + githubApiKey);
    }
}
