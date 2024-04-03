package edu.java.repository;

import edu.java.entity.Chat;
import edu.java.entity.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Link findByUrl(URI url);

    Collection<Link> findLinksByChatsContains(Chat chat);

    Collection<Link> findLinksByCheckedAtBefore(OffsetDateTime dateTime);

    boolean existsByUrl(URI url);
}
