package dev.kangoo.pirullometro.mappers;

import dev.kangoo.pirullometro.domain.response.youtube.YoutubeVideoResponse;
import dev.kangoo.pirullometro.entity.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    @Mapping(source = "items.first.id", target = "videoId")
    @Mapping(source = "items.first.snippet.publishedAt", target = "publishedAt")
    @Mapping(source = "items.first.snippet.title", target = "title")
    @Mapping(source = "items.first.snippet.thumbnails.high.url", target = "thumbnailUrlHigh")
    @Mapping(source = "items.first.contentDetails.duration", target = "duration")
    @Mapping(source = "items.first.snippet.tags", target = "tags")
    VideoEntity toEntity(YoutubeVideoResponse response);

    default String mapTags(List<String> tags){
        return String.join(",", tags);
    }

}
