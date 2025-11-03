package dev.kangoo.pirullometro.service;

import dev.kangoo.pirullometro.entity.VideoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebHookService {
    
    private static final Logger log = LoggerFactory.getLogger(WebHookService.class);

    private final VideoService videoService;

    public WebHookService(VideoService videoService) {
        this.videoService = videoService;
    }

    public void processNotification(String requestBody) {

        List<VideoEntity> newVideos = this.videoService.getLastVideos().stream()
                .map(VideoEntity::getVideoId)
                .filter(videoId -> !this.videoService.existsByVideoId(videoId))
                .map(this.videoService::getVideoInformation)
                .toList();

        if (newVideos.isEmpty()) {
            log.info("No new videos found.");
            return;
        }

        log.info("{} new videos found.", newVideos.size());
        this.videoService.saveAll(newVideos);
    }
}
