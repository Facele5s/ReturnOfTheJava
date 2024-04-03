package edu.java.repository;

import edu.java.entity.Chat;
import edu.java.entity.Link;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Collection<Chat> findChatEntitiesByLinksContains(Link link);
}
