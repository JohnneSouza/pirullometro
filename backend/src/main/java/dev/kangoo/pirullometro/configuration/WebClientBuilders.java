package dev.kangoo.pirullometro.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Configuration
public class WebClientBuilders {

    @Bean
    public WebClient youTubeWebClient(
            WebClient.Builder webClientBuilder,
            @Value("${youtube.api.base-url}") String baseUrl,
            @Value("${youtube.api.key}") String apiKey) {

        return webClientBuilder.baseUrl(baseUrl)
                .defaultUriVariables(Map.of("key", apiKey)).build();
    }

    @Bean
    public WebClient gitHubWebClient(
            WebClient.Builder webClientBuilder,
            @Value("${github.api.base-url}") String baseUrl,
            @Value("${github.api.key}") String apikey) {

        String githubHeader = "application/vnd.github+json";

        return webClientBuilder.baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, githubHeader)
                .defaultHeader(HttpHeaders.AUTHORIZATION, apikey).build();
    }

}
