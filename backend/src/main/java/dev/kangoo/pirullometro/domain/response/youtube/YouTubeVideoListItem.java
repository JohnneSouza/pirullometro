package dev.kangoo.pirullometro.domain.response.youtube;

public class YouTubeVideoListItem {

    private String id;
    private YouTubeSnippet snippet;
    private YouTubeContentDetails contentDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public YouTubeSnippet getSnippet() {
        return snippet;
    }

    public void setSnippet(YouTubeSnippet snippet) {
        this.snippet = snippet;
    }

    public YouTubeContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(YouTubeContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }

}
