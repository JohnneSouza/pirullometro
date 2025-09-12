# GitHub API: Create a Pull Request

Yes — GitHub provides a REST API to open a pull request.

Endpoint:
- Method: POST
- URL: https://api.github.com/repos/{owner}/{repo}/pulls

Required headers:
- Authorization: Bearer YOUR_GITHUB_TOKEN
  - Token scope: repo (for private repos) or public_repo (for public repos)
- Accept: application/vnd.github+json
- X-GitHub-Api-Version: 2022-11-28 (recommended)

Request body (JSON):
- title (string): Title of the pull request
- head (string): Branch name (or "owner:branch") you are proposing to merge from
- base (string): The branch you want to merge into (e.g., "main")
- body (string, optional): Description
- draft (boolean, optional): Create as draft PR

Example curl:

```
curl -X POST \
  -H "Authorization: Bearer $GITHUB_TOKEN" \
  -H "Accept: application/vnd.github+json" \
  -H "X-GitHub-Api-Version: 2022-11-28" \
  https://api.github.com/repos/OWNER/REPO/pulls \
  -d '{
    "title": "My feature PR",
    "head": "feature/my-branch",
    "base": "main",
    "body": "This PR adds awesome stuff",
    "draft": false
  }'
```

Notes:
- head must exist on the repository (or be referenced as "forkOwner:branch" for forks).
- The API also supports reviewers, assignees, labels via subsequent endpoints (Issues/PRs API).
- The equivalent GraphQL mutation is `createPullRequest` if you prefer GraphQL.

Configuration example (Quarkus MicroProfile REST Client):

In application.yaml:

```
quarkus:
  rest-client:
    github-api:
      url: https://api.github.com
```

Client interface (example):

```java
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/repos/{owner}/{repo}/pulls")
@RegisterRestClient(configKey = "github-api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface GitHubRestClient {
  @POST
  PullRequestResponse createPullRequest(
      @HeaderParam("Authorization") String authorization,
      @HeaderParam("Accept") String accept,
      @HeaderParam("X-GitHub-Api-Version") String apiVersion,
      @PathParam("owner") String owner,
      @PathParam("repo") String repo,
      CreatePullRequestRequest request);
}
```

Request/Response DTOs outline:

```java
public class CreatePullRequestRequest {
  public String title;
  public String head;
  public String base;
  public String body;
  public Boolean draft;
}

public class PullRequestResponse {
  public int number;      // PR number
  public String html_url; // Web URL
  public String state;    // open/closed
}
```

This repository does not require GitHub integration to run, but the above shows how you can call the official API to open a PR.
