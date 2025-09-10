package dev.kangoo.configuration;

import dev.kangoo.domain.responses.youtube.YoutubeVideoResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/youtube/v3/videos")
@RegisterRestClient(configKey = "youtube-api")
public interface YouTubeRestClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    YoutubeVideoResponse getVideoDetails(
            @QueryParam("part") String part,
            @QueryParam("id") String videoId,
            @QueryParam("key") String apiKey
    );

}
