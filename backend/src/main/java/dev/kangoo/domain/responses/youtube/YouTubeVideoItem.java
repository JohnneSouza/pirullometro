package dev.kangoo.domain.responses.youtube;

public class YouTubeVideoItem {

    private YouTubeSnippet snippet;
    private YouTubeContentDetails contentDetails;

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
