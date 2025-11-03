package dev.kangoo.pirullometro.controller;

import dev.kangoo.pirullometro.domain.response.ChannelAnalyticsResponse;
import dev.kangoo.pirullometro.domain.response.RandomVideoResponse;
import dev.kangoo.pirullometro.domain.response.VideoLengthResponse;
import dev.kangoo.pirullometro.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/analytics")
    public ChannelAnalyticsResponse getChannelAnalytics(){
        return this.videoService.getChannelAnalytics();
    }

    @GetMapping("/random")
    public RandomVideoResponse getRandomVideo(){
        return this.videoService.getRandomVideo();
    }

    @GetMapping("/convert/{url}")
    public VideoLengthResponse getVideoLengthConverted(@PathVariable String url){
        return this.videoService.convertVideoLength(url);
    }

}
