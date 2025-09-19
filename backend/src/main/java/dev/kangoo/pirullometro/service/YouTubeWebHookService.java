package dev.kangoo.pirullometro.service;

import dev.kangoo.pirullometro.entity.VideoEntity;
import dev.kangoo.pirullometro.repository.VideoMetadataRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YouTubeWebHookService {

    private static final Pattern VIDEO_ID_PATTERN = Pattern.compile("<yt:videoId>(.*?)</yt:videoId>");
    private static final Logger log = LoggerFactory.getLogger(YouTubeWebHookService.class);

    private final VideoMetadataRepository videoMetadataRepository;
    private final VideoDataService videoDataService;

    public YouTubeWebHookService(VideoMetadataRepository videoMetadataRepository, VideoDataService videoDataService) {
        this.videoMetadataRepository = videoMetadataRepository;
        this.videoDataService = videoDataService;
    }

    @Transactional
    public void processNotification(String requestBody) {
        Matcher matcher = VIDEO_ID_PATTERN.matcher(requestBody);

        List<String> videoIds = new ArrayList<>();
        while (matcher.find()) {
            String videoId = matcher.group(1);
            videoIds.add(videoId);
        }

        List<String> newVideos = this.videoMetadataRepository.getMissingVideos(videoIds);

       if (newVideos.isEmpty()) {
           log.info("No new videos found");
           return;
       }

        List<VideoEntity> videoDetailsList = newVideos.stream()
                .map(this.videoDataService::getVideoInformation).toList();

        this.videoMetadataRepository.saveAll(videoDetailsList);

        // TODO: Persist on SQL File on src/resources
    }
}
