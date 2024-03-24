package edu.java.repository;

import edu.java.entity.ChatEntity;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {
    @Query(value = "SELECT * FROM chat WHERE id IN (SELECT chat_id FROM chat_link WHERE link_id = ?)",
           nativeQuery = true)
    Collection<ChatEntity> findByLinkId(Long linkId);
}
