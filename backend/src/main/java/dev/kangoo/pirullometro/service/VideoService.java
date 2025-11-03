package dev.kangoo.pirullometro.service;

import dev.kangoo.pirullometro.domain.response.ChannelAnalyticsResponse;
import dev.kangoo.pirullometro.domain.response.RandomVideoResponse;
import dev.kangoo.pirullometro.domain.response.VideoLengthResponse;
import dev.kangoo.pirullometro.domain.response.youtube.YouTubeSearchListResponse;
import dev.kangoo.pirullometro.domain.response.youtube.YouTubeVideoListResponse;
import dev.kangoo.pirullometro.entity.VideoEntity;
import dev.kangoo.pirullometro.mappers.VideoMapper;
import dev.kangoo.pirullometro.repository.VideoRepository;
import dev.kangoo.pirullometro.rest.YouTubeWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Random;


@Component
public class VideoService {

    private static final String PARTS_TO_RETRIEVE_SINGLE = "contentDetails,snippet";
    private static final String PARTS_TO_RETRIEVE_MULTIPLE = "snippet,id";
    private static final String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v=";

    private final YouTubeWebClient youTubeWebClient;
    private final VideoRepository repository;
    private final VideoMapper videoMapper;
    private final String channelId;

    public VideoService(YouTubeWebClient youTubeWebClient, VideoRepository repository, VideoMapper videoMapper,
                        @Value("${youtube.api.channel-id}") String channelId) {
        this.youTubeWebClient = youTubeWebClient;
        this.repository = repository;
        this.videoMapper = videoMapper;
        this.channelId = channelId;
    }

    public ChannelAnalyticsResponse getChannelAnalytics(){
        List<VideoEntity> allVideos = this.repository.findAll().stream().toList();

        long videosLengthSumInSeconds = allVideos.stream()
                .mapToLong(videoEntity -> Duration.parse(videoEntity.getLength()).getSeconds())
                .sum();

        long totalHours = videosLengthSumInSeconds / 3600; // Transform seconds into hours
        long totalVideos = allVideos.size();
        float averageMinutes = 60 * ((float) totalHours / totalVideos);

        return ChannelAnalyticsResponse.builder()
                .totalHours((int) totalHours)
                .totalVideos((int) totalVideos)
                .averageTimeInMin(averageMinutes)
                .build();
    }

    public VideoEntity getVideoInformation(String videoId) {
        YouTubeVideoListResponse videoDetails = this.youTubeWebClient.getVideoDetails(PARTS_TO_RETRIEVE_SINGLE, videoId);
        return this.videoMapper.toEntity(videoDetails);
    }

    public List<VideoEntity> getLastVideos(){
        YouTubeSearchListResponse response = this.youTubeWebClient
                .getVideosByChannelId(PARTS_TO_RETRIEVE_MULTIPLE, this.channelId);

        return response.getItems().stream().map(item -> item.getId().getVideoId())
                .map(this::getVideoInformation)
                .toList();
    }

    /**
     * Returns a list of video IDs that are not present in the database.
     *
     * @param videoIds the list of video IDs to check for existence in the database
     * @return a list of video IDs that are missing from the database
     */
    public List<String> getMissingVideos(List<String> videoIds) {
        List<String> existing = this.repository.findExistingVideoIds(videoIds);
        return videoIds.stream()
                .filter(id -> !existing.contains(id))
                .toList();
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

        String videoId = this.repository.findAll().get(randomIndex).getVideoId();
        String videoUrl = YOUTUBE_WATCH_URL + videoId;

        return new RandomVideoResponse(videoUrl);
    }

    public VideoLengthResponse convertVideoLength(String url) {
        String videoId = url.substring(32);

        YouTubeVideoListResponse videoDetails = this.youTubeWebClient.getVideoDetails(PARTS_TO_RETRIEVE_SINGLE, videoId);
        String duration = videoDetails.getItems().getFirst().getContentDetails().getDuration();

        float averageMinutes = this.getChannelAnalytics().getAverageTimeInMinutes();

        long durationInSeconds = Duration.parse(duration).getSeconds();
        float convertedDuration = (float) durationInSeconds / 60 / averageMinutes;

        return new VideoLengthResponse((short) convertedDuration);
    }

    public void saveAll(List<VideoEntity> videoDetailsList) {
        this.repository.saveAll(videoDetailsList);
    }

    public boolean existsByVideoId(String videoId) {
        return this.repository.existsVideoEntitiesByVideoId(videoId);
    }
}
