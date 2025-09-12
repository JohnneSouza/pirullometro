package dev.kangoo.service;

import java.util.Map;

public interface GitHubOperations {

    Map<String, Object> getContentData(String owner, String repo, String path);

}
