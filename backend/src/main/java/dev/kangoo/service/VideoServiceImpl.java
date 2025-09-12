package dev.kangoo.service;

import dev.kangoo.domain.entity.VideoEntity;
import dev.kangoo.domain.requests.VideoConversionRequest;
import dev.kangoo.domain.responses.ChannelLengthInformation;
import dev.kangoo.domain.responses.RandomVideoResponse;
import dev.kangoo.domain.responses.VideoLengthResponse;
import dev.kangoo.repository.VideoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

@ApplicationScoped
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Inject
    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public ChannelLengthInformation getChannelLengthInformation() {
        return this.videoRepository.getChannelLengthInformation();
    }

    @Override
    public RandomVideoResponse getRandomVideo() {
        return this.videoRepository.getRandomVideo();
    }

    @Override
    public VideoLengthResponse getConvertedVideoLength(VideoConversionRequest request) {
        String videoId = request.getVideoUrl().substring(32);
        return this.videoRepository.getConvertedVideoLength(videoId);
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

}