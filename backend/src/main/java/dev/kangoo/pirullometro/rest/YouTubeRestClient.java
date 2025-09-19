package dev.kangoo.pirullometro.rest;


import dev.kangoo.pirullometro.domain.response.youtube.YoutubeVideoResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class YouTubeRestClient {

    private final WebClient youTubeWebClient;

    public YouTubeRestClient(WebClient youTubeWebClient) {
        this.youTubeWebClient = youTubeWebClient;
    }

    public YoutubeVideoResponse getVideoDetails(String part, String videoId) {
        return this.youTubeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/youtube/v3/videos")
                        .queryParam("part", part)
                        .queryParam("id", videoId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(YoutubeVideoResponse.class)
                .block();
    }
}