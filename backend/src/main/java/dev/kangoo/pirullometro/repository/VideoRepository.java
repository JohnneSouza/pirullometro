package dev.kangoo.pirullometro.repository;


import dev.kangoo.pirullometro.entity.VideoEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends MongoRepository<VideoEntity, ObjectId> {

    @Query("{ 'videoId': { $in: ?0 } }")
    List<String> findExistingVideoIds(List<String> videoIds);

    @Query(value = "{}", fields = "{ 'videoId': 1, '_id': 0 }")
    List<String> findAllVideoIds();

    boolean existsVideoEntitiesByVideoId(String videoId);
}