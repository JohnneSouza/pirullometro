package dev.kangoo.configuration;

import dev.kangoo.domain.entity.VideoEntity;
import dev.kangoo.repository.VideoRepository;
import dev.kangoo.service.GitHubOperations;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class DataLoader {

    private final VideoRepository videoRepository;
    private final GitHubOperations gitHubOperations;
    private final GitHubApiConfig gitHubApiConfig;

    public DataLoader(VideoRepository videoRepository, GitHubOperations gitHubOperations, GitHubApiConfig gitHubApiConfig1) {
        this.videoRepository = videoRepository;
        this.gitHubOperations = gitHubOperations;
        this.gitHubApiConfig = gitHubApiConfig1;
    }

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        loadVideosFromCsv();
    }

    private void loadVideosFromCsv() {
        String path = this.gitHubApiConfig.path();
        String repo = this.gitHubApiConfig.repository();
        String owner = this.gitHubApiConfig.username();

        Map<String, Object> contentData = this.gitHubOperations.getContentData(owner, repo, path);

        byte[] decodedBytes = Base64.getDecoder()
                .decode(((String) contentData.get("content"))
                        .replaceAll("\\s", ""));
        String csvString = new String(decodedBytes, StandardCharsets.UTF_8);

        BufferedReader reader = new BufferedReader(new StringReader(csvString));

        reader.lines()
            .skip(1) // skip headers
            // Remove the tags section (handles quoted tags containing commas), then split
            .map(line -> {
                // Find the indices of the first three and last two commas
                int first = line.indexOf(',');
                int second = line.indexOf(',', first + 1);
                int third = line.indexOf(',', second + 1);

                // The tags column is between third comma and second to last comma
                // Find the last and penultimate commas
                int last = line.lastIndexOf(',');
                int secondLast = line.lastIndexOf(',', last - 1);

                // Build a new line without the tags field
                if (first < 0 || second < 0 || third < 0 || secondLast < 0 || last < 0) {
                    return null;
                }
                // Keep: id, title, publishedAt, thumbnails.high, length
                // Remove tags, i.e., skip from 'third+1' up to and including 'secondLast'
                String newLine = line.substring(0, third + 1) + line.substring(secondLast + 1);
                return newLine.split(",", -1);
            })
            .filter(parts -> parts != null && parts.length == 5)
            .map(parts -> {
                VideoEntity video = new VideoEntity();
                video.setVideoId(parts[0].trim());
                video.setTitle(parts[1].trim());
                video.setPublishedAt(parts[2].trim());
                video.setThumbnailUrlHigh(parts[3].trim());
                video.setDuration(parts[4].trim());
                return video;
            })
            .forEach(this.videoRepository::addVideo);
    }
}