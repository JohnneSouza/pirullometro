package dev.kangoo.pirullometro.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBuilders {

    @Bean
    public WebClient youTubeWebClientProvider(
            WebClient.Builder webClientBuilder,
            @Value("${youtube.api.base-url}") String baseUrl) {

        return webClientBuilder.baseUrl(baseUrl).build();
    }
}
