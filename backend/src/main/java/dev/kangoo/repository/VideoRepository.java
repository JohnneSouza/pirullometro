package dev.kangoo.repository;

import dev.kangoo.domain.entity.VideoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Random;

@ApplicationScoped
public class VideoRepository implements PanacheRepository<VideoEntity> {

    public List<VideoEntity> getAllVideos() {
        return this.findAll().stream().toList();
    }

    public void addVideo(VideoEntity video) {
        this.persist(video);
    }

    public VideoEntity getRandomVideo() {
        long count = count();

        int randomIndex = new Random().nextInt((int) count);
        return this.findAll().page(randomIndex, 1).firstResult();
    }
}