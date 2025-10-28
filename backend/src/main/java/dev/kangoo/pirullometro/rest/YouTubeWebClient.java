package dev.kangoo.pirullometro.rest;


import dev.kangoo.pirullometro.domain.response.youtube.YoutubeVideoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class YouTubeRestClient {

    private final String youtubeApiKey;
    private final WebClient youTubeWebClient;

    public YouTubeRestClient(@Value("${youtube.api.key}") String apiKey,
                             WebClient youTubeWebClient) {
        this.youTubeWebClient = youTubeWebClient;
        this.youtubeApiKey = apiKey;
    }

    public YoutubeVideoResponse getVideoDetails(String part, String videoId) {
        return this.youTubeWebClient.get()
                .uri("/youtube/v3/videos?part={part}&id={id}&key={key}", part, videoId, this.youtubeApiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(YoutubeVideoResponse.class)
                .block();
    }
}