package dev.kangoo.pirullometro.mappers;

import dev.kangoo.pirullometro.domain.response.youtube.YouTubeSearchListItem;
import dev.kangoo.pirullometro.domain.response.youtube.YouTubeVideoListResponse;
import dev.kangoo.pirullometro.entity.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    SimpleDateFormat SIMPLE_DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Mapping(source = "items.first.id", target = "videoId")
    @Mapping(source = "items.first.snippet.publishedAt", target = "publishedAt")
    @Mapping(source = "items.first.snippet.title", target = "title")
    @Mapping(source = "items.first.snippet.thumbnails.high.url", target = "thumbnailUrlHigh")
    @Mapping(source = "items.first.contentDetails.duration", target = "length")
    @Mapping(source = "items.first.snippet.tags", target = "tags")
    @Mapping(target = "id", ignore = true)
    VideoEntity toEntity(YouTubeVideoListResponse response);



    @Mapping(source = "id.videoId", target = "videoId")
    @Mapping(source = "snippet.publishedAt", target = "publishedAt")
    @Mapping(source = "snippet.title", target = "title")
    @Mapping(source = "snippet.thumbnails.high.url", target = "thumbnailUrlHigh")
    @Mapping(source = "contentDetails.duration", target = "length")
    @Mapping(target = "id", ignore = true)
    VideoEntity toEntity(YouTubeSearchListItem videoItem);

    default String mapTags(List<String> tags){
        return String.join(",", tags);
    }

    default Date mapStringToDate(String dateString) {
        try {
            return SIMPLE_DATE_FORMATER.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date: " + dateString, e);
        }
    }

}
