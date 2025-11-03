package dev.kangoo.pirullometro.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.bson.types.ObjectId;

import java.util.Date;

@Document(collection = "videos")
public class VideoEntity {

    @Id
    private ObjectId id;

    @Field(name = "id")
    private String videoId;

    private String title;

    private Date publishedAt;

    @Field(name = "thumbnails_high")
    private String thumbnailUrlHigh;

    private String length;

    private String tags;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

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

    public Date getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getThumbnailUrlHigh() {
        return this.thumbnailUrlHigh;
    }

    public void setThumbnailUrlHigh(String thumbnailUrlHigh) {
        this.thumbnailUrlHigh = thumbnailUrlHigh;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}