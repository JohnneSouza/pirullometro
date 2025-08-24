package dev.kangoo.domain.requests;

import dev.kangoo.validation.ValidYouTubeUrl;

public class VideoConversionRequest {

    @ValidYouTubeUrl
    private String videoUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
