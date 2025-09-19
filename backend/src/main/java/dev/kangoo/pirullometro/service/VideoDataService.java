package dev.kangoo.pirullometro.service;

import dev.kangoo.pirullometro.domain.response.ChannelAnalyticsResponse;
import dev.kangoo.pirullometro.domain.response.RandomVideoResponse;
import dev.kangoo.pirullometro.domain.response.VideoLengthResponse;
import dev.kangoo.pirullometro.domain.response.youtube.YoutubeVideoResponse;
import dev.kangoo.pirullometro.entity.VideoEntity;
import dev.kangoo.pirullometro.mappers.VideoMapper;
import dev.kangoo.pirullometro.repository.VideoMetadataRepository;
import dev.kangoo.pirullometro.rest.YouTubeRestClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.StreamSupport;


@Component
public class VideoDataService {

    private static final String RETRIEVE_PARTS = "contentDetails,snippet";
    private static final String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v=";

    private final YouTubeRestClient youTubeRestClient;
    private final VideoMetadataRepository repository;
    private final VideoMapper videoMapper;

    public VideoDataService(YouTubeRestClient youTubeRestClient, VideoMetadataRepository repository, VideoMapper videoMapper) {
        this.youTubeRestClient = youTubeRestClient;
        this.repository = repository;
        this.videoMapper = videoMapper;
    }

    public ChannelAnalyticsResponse getChannelAnalytics(){
        List<VideoEntity> allVideos = StreamSupport
                .stream(this.repository.findAll().spliterator(), false).toList();

        long videosLengthSumInSeconds = allVideos.stream()
                .mapToLong(videoEntity -> Duration.parse(videoEntity.getDuration()).getSeconds())
                .sum();

        long totalHours = videosLengthSumInSeconds / 3600;
        long totalVideos = allVideos.size();


        return ChannelAnalyticsResponse.builder()
                .totalHours((int) totalHours)
                .totalVideos((int) totalVideos)
                .build();
    }

    public VideoEntity getVideoInformation(String videoId) {
        YoutubeVideoResponse videoDetails = this.youTubeRestClient.getVideoDetails(RETRIEVE_PARTS, videoId);
        return this.videoMapper.toEntity(videoDetails);
    }


    /**
     * Retrieves a random video from the repository and constructs a response containing its URL.
     *
     * @return a {@code RandomVideoResponse} containing the URL of a randomly selected video
     *         from the repository.
     */
    public RandomVideoResponse getRandomVideo() {
        long count = this.repository.count();
        int randomIndex = new Random().nextInt((int) count);

        String videoId = this.repository.findFirstById((count - randomIndex - 1)).getVideoId();
        String videoUrl = YOUTUBE_WATCH_URL + videoId;

        return new RandomVideoResponse(videoUrl);
    }

    public VideoLengthResponse convertVideoLength(String url) {
        String videoId = url.substring(32);

        YoutubeVideoResponse videoDetails = this.youTubeRestClient.getVideoDetails(RETRIEVE_PARTS, videoId);
        String duration = videoDetails.getItems().getFirst().getContentDetails().getDuration();

        float averageMinutes = this.getChannelAnalytics().getAverageMinutes();

        long durationInSeconds = Duration.parse(duration).getSeconds();
        float convertedDuration = (float) durationInSeconds / 60 / averageMinutes;

        return new VideoLengthResponse((short) convertedDuration);
    }
}
