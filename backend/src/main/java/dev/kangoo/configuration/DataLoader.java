package dev.kangoo.configuration;

import dev.kangoo.domain.entity.VideoEntity;
import dev.kangoo.repository.VideoRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class DataLoader {

    private static final String CSV_FILE_PATH = "/youtube_videos.csv";

    private final VideoRepository videoRepository;

    public DataLoader(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        loadVideosFromCsv();
    }

    private void loadVideosFromCsv() {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(CSV_FILE_PATH)))
        );

        reader.lines()
            .skip(1) // skip headers
            .map(line -> line.split(",", -1))
            .filter(parts -> parts.length == 6)
            .map(parts -> {
                VideoEntity video = new VideoEntity();
                video.setVideoId(parts[0].trim());
                video.setTitle(parts[1].trim());
                video.setPublishedAt(parts[2].trim());
                video.setThumbnailUrlHigh(parts[4].trim());
                video.setDuration(parts[5].trim());
                return video;
            })
            .forEach(videoRepository::addVideo);
    }
}