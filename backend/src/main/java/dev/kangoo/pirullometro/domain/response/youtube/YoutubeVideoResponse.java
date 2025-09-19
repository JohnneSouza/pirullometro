package dev.kangoo.pirullometro.domain.response.youtube;

import java.util.List;

public class YoutubeVideoResponse {

    private List<YouTubeVideoItem> items;

    public List<YouTubeVideoItem> getItems() {
        return this.items;
    }

    public void setItems(List<YouTubeVideoItem> items) {
        this.items = items;
    }
}
