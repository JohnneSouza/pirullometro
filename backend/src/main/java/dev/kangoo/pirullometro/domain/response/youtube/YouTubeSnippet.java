package dev.kangoo.pirullometro.domain.response.youtube;

import java.util.List;

public class YouTubeSnippet {

    private String title;
    private String publishedAt;
    private YouTubeThumbnails thumbnails;
    private List<String> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public YouTubeThumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(YouTubeThumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
