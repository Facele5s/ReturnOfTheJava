package edu.java.dao;

import edu.java.entity.Link;
import java.net.URI;
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
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM link WHERE id = ?";
    private static final String QUERY_FIND_BY_CHAT = "SELECT * FROM link WHERE chat_id = ?";
    private static final String QUERY_FIND_BY_URL = "SELECT * FROM link WHERE url = ?";
    private static final String QUERY_FIND_LONG_UNCHECKED = "SELECT * FROM link WHERE checked_at < ?";
    private static final String QUERY_UPDATE_LINK = "UPDATE link SET updated_at = ? WHERE id = ?";
    private static final String QUERY_CHECK_LINK = "UPDATE link SET checked_at = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public Link add(Long chatId, URI url) {
        return jdbcTemplate.queryForObject(
            QUERY_ADD,
            new BeanPropertyRowMapper<>(Link.class),
            chatId,
            url
        );
    }

    @Transactional
    public Link remove(Long linkId) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE,
            new BeanPropertyRowMapper<>(Link.class),
            linkId
        );
    }

    @Transactional
    public Collection<Link> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(Link.class));
    }

    @Transactional
    public Link findById(Long linkId) {
        return jdbcTemplate.queryForObject(
            QUERY_FIND_BY_ID,
            new BeanPropertyRowMapper<>(Link.class),
            linkId
        );
    }

    @Transactional
    public Collection<Link> findByChat(Long chatId) {
        return jdbcTemplate.query(QUERY_FIND_BY_CHAT, new BeanPropertyRowMapper<>(Link.class), chatId);
    }

    @Transactional
    public Collection<Link> findByUrl(URI url) {
        return jdbcTemplate.query(QUERY_FIND_BY_URL, new BeanPropertyRowMapper<>(Link.class), url);
    }

    @Transactional
    public Collection<Link> findLongUnchecked(OffsetDateTime dateTime) {
        return jdbcTemplate.query(QUERY_FIND_LONG_UNCHECKED, new BeanPropertyRowMapper<>(Link.class), dateTime);
    }

    @Transactional
    public Link updateLink(Long linkId, OffsetDateTime updateDate) {
        return jdbcTemplate.queryForObject(
            QUERY_UPDATE_LINK,
            new BeanPropertyRowMapper<>(Link.class),
            updateDate,
            linkId
        );
    }

    @Transactional
    public Link checkLink(Long linkId, OffsetDateTime checkDate) {
        return jdbcTemplate.queryForObject(
            QUERY_CHECK_LINK,
            new BeanPropertyRowMapper<>(Link.class),
            checkDate,
            linkId
        );
    }
}
