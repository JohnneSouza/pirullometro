package dev.kangoo.pirullometro.domain.response.youtube;

import java.util.List;

public class YouTubeVideoListResponse {

    private List<YouTubeVideoListItem> items;

    public List<YouTubeVideoListItem> getItems() {
        return this.items;
    }

    public void setItems(List<YouTubeVideoListItem> items) {
        this.items = items;
    }
}
