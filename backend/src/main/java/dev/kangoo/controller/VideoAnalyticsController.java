package dev.kangoo.controller;

import dev.kangoo.domain.requests.VideoConversionRequest;
import dev.kangoo.domain.responses.RandomVideoResponse;
import dev.kangoo.domain.responses.VideoLengthResponse;
import dev.kangoo.service.VideoService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/v1/api/videos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VideoAnalyticsController {

    private final VideoService videoService;

    public VideoAnalyticsController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GET
    @Path("/pirulla")
    public Map<String, Object> getPirulla(){
        return this.videoService.getPirulla();
    }

    @GET
    @Path("/random")
    public RandomVideoResponse getRandomVideo() {
        return this.videoService.getRandomVideo();
    }

    @POST
    @Path("/length")
    public VideoLengthResponse getVideoLength(VideoConversionRequest request) {
        return this.videoService.getConvertedVideoLength(request);
    }

}
