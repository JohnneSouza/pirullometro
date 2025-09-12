package dev.kangoo.service;

import dev.kangoo.rest.GitHubRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Map;

@ApplicationScoped
public class GitHubService implements GitHubOperations {

    private final GitHubRestClient gitHubRestClient;

    public GitHubService(@RestClient GitHubRestClient gitHubRestClient) {
        this.gitHubRestClient = gitHubRestClient;
    }

    @Override
    public Map<String, Object> getContentData(String owner, String repo, String path) {
        return this.gitHubRestClient.getContentData(owner, repo, path);
    }
}
