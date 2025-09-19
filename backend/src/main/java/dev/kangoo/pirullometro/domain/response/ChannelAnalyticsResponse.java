package dev.kangoo.pirullometro.domain.response;

public class ChannelAnalyticsResponse {

    private int totalHours;
    private int totalVideos;
    private float averageHours;
    private float averageMinutes;

    private ChannelAnalyticsResponse() {
    }

    public int getTotalHours() {
        return this.totalHours;
    }

    public int getTotalVideos() {
        return this.totalVideos;
    }

    public float getAverageHours() {
        return this.averageHours;
    }

    public float getAverageMinutes() {
        return this.averageMinutes;
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

        public Builder averageHours(float averageHours) {
            this.response.averageHours = averageHours;
            return this;
        }

        public Builder averageMinutes(float averageMinutes) {
            this.response.averageMinutes = averageMinutes;
            return this;
        }

        public ChannelAnalyticsResponse build() {
            return this.response;
        }
    }

}
