package dev.kangoo.domain.responses.youtube;

public class YouTubeSnippet {

    private String title;
    private String publishedAt;
    private YouTubeThumbnails thumbnails;

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
}
