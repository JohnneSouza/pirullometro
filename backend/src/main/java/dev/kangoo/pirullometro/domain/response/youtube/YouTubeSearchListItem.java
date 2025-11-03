package dev.kangoo.pirullometro.domain.response.youtube;

public class YouTubeSearchListItem {

    private YouTubeSearchListId id;
    private YouTubeSearchListSnippet snippet;
    private YouTubeContentDetails contentDetails;

    public YouTubeSearchListId getId() {
        return id;
    }

    public void setId(YouTubeSearchListId id) {
        this.id = id;
    }

    public YouTubeSearchListSnippet getSnippet() {
        return snippet;
    }

    public void setSnippet(YouTubeSearchListSnippet snippet) {
        this.snippet = snippet;
    }

    public YouTubeContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(YouTubeContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }

}
