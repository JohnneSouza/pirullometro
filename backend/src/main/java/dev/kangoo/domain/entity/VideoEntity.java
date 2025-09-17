package dev.kangoo.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "videos")
public class VideoEntity extends PanacheEntity {

    @Column(nullable = false, unique = true)
    private String videoId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String publishedAt;

    @Column(name = "thumbnails_high")
    private String thumbnailUrlHigh;

    @Column(nullable = false)
    private String duration;

    @Column
    private String tags;

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getThumbnailUrlHigh() {
        return this.thumbnailUrlHigh;
    }

    public void setThumbnailUrlHigh(String thumbnailUrlHigh) {
        this.thumbnailUrlHigh = thumbnailUrlHigh;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}