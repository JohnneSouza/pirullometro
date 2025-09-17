package dev.kangoo.rest;

import dev.kangoo.domain.responses.youtube.YoutubeVideoResponse;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/youtube/v3/videos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "youtube-api")
@ClientQueryParam(name = "key", value = "${youtube.api.key}")
public interface YouTubeRestClient {

    @GET
    YoutubeVideoResponse getVideoDetails(
            @QueryParam("part") String part,
            @QueryParam("id") String videoId
    );

}
