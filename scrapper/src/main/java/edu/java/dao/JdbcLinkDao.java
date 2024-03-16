package edu.java.dao;

import edu.java.entity.Chat;
import edu.java.entity.Link;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcLinkDao {
    private static final String QUERY_ADD = "INSERT INTO link (chat_id, url) VALUES (?, ?) RETURNING *";
    private static final String QUERY_REMOVE = "DELETE FROM link WHERE id = ? RETURNING *";
    private static final String QUERY_FIND_ALL = "SELECT * FROM link";
    private static final String QUERY_FIND_BY_CHAT = "SELECT * FROM link WHERE id = ?";
    private static final String QUERY_FIND_BY_URL = "SELECT * FROM link WHERE url = ?";
    private static final String QUERY_FIND_LONG_UNCHECKED = "SELECT * FROM link WHERE checked_at < ?";
    private static final String QUERY_UPDATE_LINK = "UPDATE link SET updated_at = ? WHERE id = ?";
    private static final String QUERY_CHECK_LINK = "UPDATE link SET checked_at = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public Link add(Link link) {
        return jdbcTemplate.queryForObject(
            QUERY_ADD,
            new BeanPropertyRowMapper<>(Link.class),
            link.getChatId(),
            link.getUrl()
        );
    }

    @Transactional
    public Link remove(Link link) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE,
            new BeanPropertyRowMapper<>(Link.class),
            link.getId()
        );
    }

    @Transactional
    public Collection<Link> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(Link.class));
    }

    @Transactional
    public Collection<Link> findByChat(Chat chat) {
        return jdbcTemplate.query(QUERY_FIND_BY_CHAT, new BeanPropertyRowMapper<>(Link.class), chat.getId());
    }

    @Transactional
    public Collection<Link> findByUrl(Link link) {
        return jdbcTemplate.query(QUERY_FIND_BY_URL, new BeanPropertyRowMapper<>(Link.class), link.getUrl());
    }

    @Transactional
    public Collection<Link> findLongUnchecked(OffsetDateTime dateTime) {
        return jdbcTemplate.query(QUERY_FIND_LONG_UNCHECKED, new BeanPropertyRowMapper<>(Link.class), dateTime);
    }

    @Transactional
    public Link updateLink(Link link) {
        return jdbcTemplate.queryForObject(
            QUERY_UPDATE_LINK,
            new BeanPropertyRowMapper<>(Link.class),
            link.getId()
        );
    }

    @Transactional
    public Link checkLink(Link link) {
        return jdbcTemplate.queryForObject(
            QUERY_CHECK_LINK,
            new BeanPropertyRowMapper<>(Link.class),
            link.getId()
        );
    }

}
