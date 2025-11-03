package dev.kangoo.pirullometro.domain.response;

public class ChannelAnalyticsResponse {

    private int totalHours;
    private int totalVideos;
    private float averageTimeInMinutes;

    private ChannelAnalyticsResponse() {
    }

    public int getTotalHours() {
        return this.totalHours;
    }

    public int getTotalVideos() {
        return this.totalVideos;
    }

    public float getAverageTimeInMinutes() {
        return this.averageTimeInMinutes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ChannelAnalyticsResponse response;

        private Builder() {
            this.response = new ChannelAnalyticsResponse();
        }

        public Builder totalHours(int totalHours) {
            this.response.totalHours = totalHours;
            return this;
        }

        public Builder totalVideos(int totalVideos) {
            this.response.totalVideos = totalVideos;
            return this;
        }

        public Builder averageTimeInMin(float averageTimeInMinutes) {
            this.response.averageTimeInMinutes = averageTimeInMinutes;
            return this;
        }

        public ChannelAnalyticsResponse build() {
            return this.response;
        }
    }

}
