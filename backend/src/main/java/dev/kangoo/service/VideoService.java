package dev.kangoo.service;

import dev.kangoo.domain.requests.VideoConversionRequest;
import dev.kangoo.domain.responses.ChannelLengthInformation;
import dev.kangoo.domain.responses.RandomVideoResponse;
import dev.kangoo.domain.responses.VideoLengthResponse;

public interface VideoService {

    ChannelLengthInformation getChannelLengthInformation();

    RandomVideoResponse getRandomVideo();

    VideoLengthResponse getConvertedVideoLength(VideoConversionRequest request);

}
