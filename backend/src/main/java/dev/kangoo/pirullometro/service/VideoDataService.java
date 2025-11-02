package dev.kangoo.pirullometro.service;

import dev.kangoo.pirullometro.domain.response.ChannelAnalyticsResponse;
import dev.kangoo.pirullometro.domain.response.RandomVideoResponse;
import dev.kangoo.pirullometro.domain.response.VideoLengthResponse;
import dev.kangoo.pirullometro.domain.response.youtube.YoutubeVideoResponse;
import dev.kangoo.pirullometro.entity.VideoEntity;
import dev.kangoo.pirullometro.mappers.VideoMapper;
import dev.kangoo.pirullometro.repository.VideoDataRepository;
import dev.kangoo.pirullometro.rest.YouTubeWebClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Random;


@Component
public class VideoDataService {

    private static final String PARTS_TO_RETRIEVE = "contentDetails,snippet";
    private static final String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v=";

    private final YouTubeWebClient youTubeWebClient;
    private final VideoDataRepository repository;
    private final VideoMapper videoMapper;

    public VideoDataService(YouTubeWebClient youTubeWebClient, VideoDataRepository repository, VideoMapper videoMapper) {
        this.youTubeWebClient = youTubeWebClient;
        this.repository = repository;
        this.videoMapper = videoMapper;
    }

    public ChannelAnalyticsResponse getChannelAnalytics(){
        List<VideoEntity> allVideos = this.repository.findAll().stream().toList();

        long videosLengthSumInSeconds = allVideos.stream()
                .mapToLong(videoEntity -> Duration.parse(videoEntity.getLength()).getSeconds())
                .sum();

        long totalHours = videosLengthSumInSeconds / 3600;
        long totalVideos = allVideos.size();


        return ChannelAnalyticsResponse.builder()
                .totalHours((int) totalHours)
                .totalVideos((int) totalVideos)
                .build();
    }

    public VideoEntity getVideoInformation(String videoId) {
        YoutubeVideoResponse videoDetails = this.youTubeWebClient.getVideoDetails(PARTS_TO_RETRIEVE, videoId);
        return this.videoMapper.toEntity(videoDetails);
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

        YoutubeVideoResponse videoDetails = this.youTubeWebClient.getVideoDetails(PARTS_TO_RETRIEVE, videoId);
        String duration = videoDetails.getItems().getFirst().getContentDetails().getDuration();

        float averageMinutes = this.getChannelAnalytics().getAverageMinutes();

        long durationInSeconds = Duration.parse(duration).getSeconds();
        float convertedDuration = (float) durationInSeconds / 60 / averageMinutes;

        return new VideoLengthResponse((short) convertedDuration);
    }

    public void saveAll(List<VideoEntity> videoDetailsList) {
        this.repository.saveAll(videoDetailsList);
    }
}
