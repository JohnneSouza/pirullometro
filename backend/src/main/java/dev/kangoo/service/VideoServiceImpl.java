package dev.kangoo.service;

import dev.kangoo.domain.entity.VideoEntity;
import dev.kangoo.domain.requests.VideoConversionRequest;
import dev.kangoo.domain.responses.RandomVideoResponse;
import dev.kangoo.domain.responses.VideoLengthResponse;
import dev.kangoo.domain.responses.youtube.YoutubeVideoResponse;
import dev.kangoo.repository.VideoRepository;
import dev.kangoo.rest.YouTubeRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

@ApplicationScoped
public class VideoServiceImpl implements VideoService {

    // TODO: Fix the url to be compatible with other formats.
    private static final String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v=";

    private static final String RETRIEVE_PARTS = "contentDetails,snippet";

    private final VideoRepository videoRepository;
    private final YouTubeRestClient youTubeRestClient;

    @Inject
    public VideoServiceImpl(@RestClient YouTubeRestClient youTubeRestClient,
                            VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
        this.youTubeRestClient = youTubeRestClient;
    }

    /**
     * Retrieves a random video from the video repository and constructs a full YouTube video URL.
     *
     * @return A {@code RandomVideoResponse} containing the URL of the randomly selected video.
     */
    @Override
    public RandomVideoResponse getRandomVideo() {
        VideoEntity video = this.videoRepository.getRandomVideo();
        String videoUrl = YOUTUBE_WATCH_URL + video.getVideoId();
        return new RandomVideoResponse(videoUrl);
    }

    @Override
    public VideoLengthResponse getConvertedVideoLength(VideoConversionRequest request) {
        String videoId = request.getVideoUrl().substring(32);
        YoutubeVideoResponse videoDetails = this.youTubeRestClient.getVideoDetails(RETRIEVE_PARTS, videoId);

        String duration = videoDetails.getItems().getFirst().getContentDetails().getDuration();

        long durationInSeconds = this.parseDurationToSeconds(duration);
        float convertedDuration = (float) (durationInSeconds / 60) / 42.5f;


        VideoLengthResponse videoLengthResponse = new VideoLengthResponse();
        videoLengthResponse.setPirullaDuration(convertedDuration);
        return videoLengthResponse;
    }

    @Override
    public Map<String, Object> getPirulla() {
        List<VideoEntity> allVideos = this.videoRepository.getAllVideos();
        int size = allVideos.size();

        LongStream longStream = allVideos.stream()
                .mapToLong(videoEntity -> Duration.parse(videoEntity.getDuration()).getSeconds());

        long totalSeconds = longStream.sum();

        long totalDays = totalSeconds / 86400;
        long totalHours = totalSeconds / 3600;
        // Calculate average duration in seconds
        long avgSeconds = totalSeconds / size;

        // Calculate hours, minutes and seconds for averaging
        long hours = avgSeconds / 3600;
        long minutes = (avgSeconds % 3600) / 60;
        long seconds = avgSeconds % 60;

        // Build a human-readable average duration string
        StringBuilder avgDurationBuilder = new StringBuilder();

        if (hours > 0)
            avgDurationBuilder.append(hours).append(" horas ");

        if (minutes > 0)
            avgDurationBuilder.append(minutes).append(" minutos ");

        if (seconds > 0)
            avgDurationBuilder.append(seconds).append(" segundos");

        String avgDuration = avgDurationBuilder.toString().trim();

        return Map.of(
                "pirullaDuration", avgDuration,
                "totalSeconds", totalSeconds,
                "totalDays", totalDays,
                "totalHours", totalHours,
                "totalVideos", size
        );
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