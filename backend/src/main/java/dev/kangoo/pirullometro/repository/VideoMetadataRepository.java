package dev.kangoo.pirullometro.repository;


import dev.kangoo.pirullometro.entity.VideoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoMetadataRepository extends CrudRepository<VideoEntity, Long> {

    @Query("SELECT v.videoId FROM Video v WHERE v.videoId IN :videoIds")
    List<String> findExistingVideoIds(@Param("videoIds") List<String> videoIds);

    /**
     * Returns a list of video IDs that are not present in the database.
     *
     * @param videoIds the list of video IDs to check for existence in the database
     * @return a list of video IDs that are missing from the database
     */
    default List<String> getMissingVideos(List<String> videoIds) {
        List<String> existing = findExistingVideoIds(videoIds);
        return videoIds.stream()
                .filter(id -> !existing.contains(id))
                .toList();
    }

    VideoEntity findFirstById(Long id);
}