package dev.kangoo.pirullometro.controller;

import dev.kangoo.pirullometro.domain.response.ChannelAnalyticsResponse;
import dev.kangoo.pirullometro.domain.response.RandomVideoResponse;
import dev.kangoo.pirullometro.domain.response.VideoLengthResponse;
import dev.kangoo.pirullometro.service.VideoDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/videos")
public class VideoDataController {

    private final VideoDataService videoDataService;

    public VideoDataController(VideoDataService videoDataService) {
        this.videoDataService = videoDataService;
    }

    @GetMapping("/analytics")
    public ChannelAnalyticsResponse getChannelAnalytics(){
        return this.videoDataService.getChannelAnalytics();
    }

    @GetMapping("/random")
    public RandomVideoResponse getRandomVideo(){
        return this.videoDataService.getRandomVideo();
    }

    @GetMapping("/convert/{url}")
    public VideoLengthResponse getVideoLengthConverted(@PathVariable String url){
        return this.videoDataService.convertVideoLength(url);
    }

}
