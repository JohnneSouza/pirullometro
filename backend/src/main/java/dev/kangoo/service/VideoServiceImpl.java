package dev.kangoo.service;

import dev.kangoo.domain.requests.VideoConversionRequest;
import dev.kangoo.domain.responses.ChannelLengthInformation;
import dev.kangoo.domain.responses.RandomVideoResponse;
import dev.kangoo.domain.responses.VideoLengthResponse;
import dev.kangoo.repository.VideoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Inject
    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public ChannelLengthInformation getChannelLengthInformation() {
        return this.videoRepository.getChannelLengthInformation();
    }

    @Override
    public RandomVideoResponse getRandomVideo() {
        return this.videoRepository.getRandomVideo();
    }

    @Override
    public VideoLengthResponse getConvertedVideoLength(VideoConversionRequest request) {
        String videoId = request.getVideoUrl().substring(32);
        return this.videoRepository.getConvertedVideoLength(videoId);
    }

}