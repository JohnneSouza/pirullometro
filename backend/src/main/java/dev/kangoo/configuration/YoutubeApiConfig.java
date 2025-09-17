package dev.kangoo.configuration;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "youtube.api")
public interface YoutubeApiConfig {

    String channel();

}
