package dev.kangoo.pirullometro.rest;


import dev.kangoo.pirullometro.domain.response.youtube.YouTubeSearchListResponse;
import dev.kangoo.pirullometro.domain.response.youtube.YouTubeVideoListResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class YouTubeWebClient {

    private final String youtubeApiKey;
    private final WebClient youTubeWebClient;

    private static final String ORDER = "date";
    private static final int MAX_RESULTS = 10;

    public YouTubeWebClient(@Value("${youtube.api.key}") String apiKey,
                            @Qualifier("youTubeWebClientProvider") WebClient youTubeWebClient) {
        this.youTubeWebClient = youTubeWebClient;
        this.youtubeApiKey = apiKey;
    }

    public YouTubeVideoListResponse getVideoDetails(String part, String videoId){
        return this.youTubeWebClient.get()
                .uri("/youtube/v3/videos?part={part}&id={id}&key={key}", part, videoId, this.youtubeApiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(YouTubeVideoListResponse.class)
                .block();
    }

    public YouTubeSearchListResponse getVideosByChannelId(String part, String channelId){
        return this.youTubeWebClient.get()
                .uri("/youtube/v3/search?channelId={channelId}&order={order}&maxResults={maxResults}&part={part}&key={key}",
                        channelId, ORDER, MAX_RESULTS, part, this.youtubeApiKey)
                .retrieve()
                .bodyToMono(YouTubeSearchListResponse.class)
                .block();
    }
}