package dev.kangoo.pirullometro.domain.response;

public class RandomVideoResponse {

    private final String url;

    public RandomVideoResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
