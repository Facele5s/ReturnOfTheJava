package edu.java.repository;

import edu.java.entity.LinkEntity;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    @Query(value = "SELECT * FROM link WHERE chat_id = ? AND url = ?", nativeQuery = true)
    LinkEntity findByUrl(Long chatId, URI url);

    @Query(value = "SELECT * FROM link WHERE url = ?", nativeQuery = true)
    Collection<LinkEntity> findByUrl(URI uri);

    default Collection<LinkEntity> findLongUnchecked(Duration duration) {
        Timestamp timestamp = Timestamp
            .from(OffsetDateTime.now().minusSeconds(duration.getSeconds()).toInstant());

        return findLongUnchecked(timestamp);
    }

    @Query(value = "SELECT * FROM link WHERE checked_at < ?", nativeQuery = true)
    Collection<LinkEntity> findLongUnchecked(Timestamp timestamp);

    @Query(value = "SELECT * FROM link WHERE chat_id = ?", nativeQuery = true)
    Collection<LinkEntity> findByChat(Long chatId);
}
