package dev.kangoo.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;

@RegisterRestClient(configKey = "github-api")
public interface GitHubRestClient {

    @GET
    @Path("/repos/{owner}/{repo}/contents/{path: .+}")
    @Produces("application/vnd.github+json")
    Map<String, Object> getContentData(
        @PathParam("owner") String owner,
        @PathParam("repo") String repo,
        @PathParam("path") String path
    );

    @PUT
    @Path("/repos/{owner}/{repo}/contents/{path: .+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/vnd.github+json")
    Map<String, Object> updateFile(
        @PathParam("owner") String owner,
        @PathParam("repo") String repo,
        @PathParam("path") String path,
        Map<String, Object> body
    );
}
