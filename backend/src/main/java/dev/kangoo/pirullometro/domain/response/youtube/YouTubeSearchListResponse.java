package dev.kangoo.pirullometro.domain.response.youtube;

import java.util.List;

public class YouTubeSearchListResponse {

    private List<YouTubeSearchListItem> items;

    public List<YouTubeSearchListItem> getItems() {
        return this.items;
    }

    public void setItems(List<YouTubeSearchListItem> items) {
        this.items = items;
    }
}
