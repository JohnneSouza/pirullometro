package dev.kangoo.pirullometro.domain.response;

public class VideoLengthResponse {

    private final short length;

    public VideoLengthResponse(short length) {
        this.length = length;
    }

    public short getLength() {
        return length;
    }
}
