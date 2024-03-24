package edu.java.repository;

import edu.java.entity.LinkEntity;
import java.net.URI;
import java.time.Duration;
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

    @Query(value = "SELECT * FROM link WHERE checked_at < ?", nativeQuery = true)
    Collection<LinkEntity> findLongUnchecked(Duration duration);

    @Query(value = "SELECT * FROM link WHERE chat_id = ?", nativeQuery = true)
    Collection<LinkEntity> findByChat(Long chatId);
}
