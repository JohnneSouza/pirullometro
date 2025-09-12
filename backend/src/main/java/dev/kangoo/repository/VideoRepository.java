package dev.kangoo.repository;

import dev.kangoo.rest.YouTubeRestClient;
import dev.kangoo.configuration.YoutubeApiConfig;
import dev.kangoo.domain.entity.VideoEntity;
import dev.kangoo.domain.responses.ChannelLengthInformation;
import dev.kangoo.domain.responses.RandomVideoResponse;
import dev.kangoo.domain.responses.VideoLengthResponse;
import dev.kangoo.domain.responses.youtube.YoutubeVideoResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Random;

@ApplicationScoped
public class VideoRepository implements PanacheRepository<VideoEntity> {

    private final YouTubeRestClient youTubeRestClient;
    private final YoutubeApiConfig youtubeApiConfig;

    private static final String RETRIEVE_PARTS = "contentDetails,snippet";

    // TODO: Fix the url to be compatible with other formats.
    private static final String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v=";

    public VideoRepository(@RestClient YouTubeRestClient youTubeRestClient, YoutubeApiConfig youtubeApiConfig) {
        this.youTubeRestClient = youTubeRestClient;
        this.youtubeApiConfig = youtubeApiConfig;
    }

    public List<VideoEntity> getAllVideos() {
        return this.findAll().stream().toList();
    }

    public void addVideo(VideoEntity video) {
        this.persist(video);
    }

    public ChannelLengthInformation getChannelLengthInformation() {
        return null;
    }

    public RandomVideoResponse getRandomVideo() {
        long count = count();

        int randomIndex = new Random().nextInt((int) count);
        VideoEntity video = findAll().page(randomIndex, 1).firstResult();
        String videoUrl = YOUTUBE_WATCH_URL + video.getVideoId();
        return new RandomVideoResponse(videoUrl);
    }

    public VideoLengthResponse getConvertedVideoLength(String videoId) {
        YoutubeVideoResponse videoDetails = this.youTubeRestClient.getVideoDetails(
                RETRIEVE_PARTS, videoId, this.youtubeApiConfig.getApiKey());

        String duration = videoDetails.getItems().getFirst().getContentDetails().getDuration();

        long durationInSeconds = this.parseDurationToSeconds(duration);
        float convertedDuration = (float) (durationInSeconds / 60) / 42.5f;


        VideoLengthResponse videoLengthResponse = new VideoLengthResponse();
        videoLengthResponse.setPirullaDuration(convertedDuration);
        return videoLengthResponse;
    }

    /**
     * Convert ISO 8601 duration (e.g., PT15M30S) to seconds
     */
    private long parseDurationToSeconds(String isoDuration) {
        long seconds = 0;
        String s = isoDuration.replace("PT", "");
        StringBuilder number = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                int value = Integer.parseInt(number.toString());
                switch (c) {
                    case 'H' -> seconds += value * 3600L;
                    case 'M' -> seconds += value * 60L;
                    case 'S' -> seconds += value;
                }
                number = new StringBuilder();
            }
        }
        return seconds;
    }
}