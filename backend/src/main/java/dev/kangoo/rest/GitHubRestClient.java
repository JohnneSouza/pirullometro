package dev.kangoo.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;

@Produces("application/vnd.github+json")
@RegisterRestClient(configKey = "github-api")
@ClientHeaderParam(name = "Authorization", value = "${github.api.key}")
@Path("/repos/{owner}/{repo}/contents/{path: .+}")
public interface GitHubRestClient {

    @GET
    Map<String, Object> getContentData(
        @PathParam("owner") String owner,
        @PathParam("repo") String repo,
        @PathParam("path") String path
    );

    @PUT
    Map<String, Object> updateFile(
        @PathParam("owner") String owner,
        @PathParam("repo") String repo,
        @PathParam("path") String path,
        Map<String, Object> body
    );
}
