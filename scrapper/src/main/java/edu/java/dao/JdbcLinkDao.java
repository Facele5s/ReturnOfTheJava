package edu.java.dao;

import edu.java.entity.jdbc.Link;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@SuppressWarnings("LineLength")
public class JdbcLinkDao {
    private static final String QUERY_ADD =
        "INSERT INTO link (url, updated_at, checked_at) VALUES (?, ?, ?) RETURNING *";
    private static final String QUERY_ADD_COMBINATION = "INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)";
    private static final String QUERY_CHECK_EXISTENCE = "SELECT COUNT(1) FROM link WHERE url = ?";
    private static final String QUERY_REMOVE = "DELETE FROM link WHERE id = ? RETURNING *";
    private static final String QUERY_UNTRACK = "DELETE FROM chat_link WHERE chat_id = ? AND link_id = ?";
    private static final String QUERY_FIND_ALL = "SELECT * FROM link";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM link WHERE id = ?";
    private static final String QUERY_FIND_BY_CHAT = "SELECT * FROM link WHERE id IN "
        + "(SELECT link_id FROM chat_link WHERE chat_id = ?)";
    private static final String QUERY_FIND_BY_URL = "SELECT * FROM link WHERE url = ?";
    private static final String QUERY_FIND_LONG_UNCHECKED = "SELECT * FROM link WHERE checked_at < ?";
    private static final String QUERY_COUNT_CHATS_FOR_LINK = "SELECT COUNT(*) FROM chat_link WHERE link_id = ?";
    private static final String QUERY_UPDATE_LINK = "UPDATE link SET updated_at = ? WHERE id = ? RETURNING *";
    private static final String QUERY_CHECK_LINK = "UPDATE link SET checked_at = ? WHERE id = ? RETURNING *";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public Link add(Long chatId, URI url) {
        Link link;

        Long linkCount = jdbcTemplate.queryForObject(
            QUERY_CHECK_EXISTENCE,
            Long.class,
            url.toString()
        );

        if (linkCount > 0) {
            link = jdbcTemplate.queryForObject(
                QUERY_FIND_BY_URL,
                new BeanPropertyRowMapper<>(Link.class),
                url.toString()
            );
        } else {
            link = jdbcTemplate.queryForObject(
                QUERY_ADD,
                new BeanPropertyRowMapper<>(Link.class),
                url.toString(),
                OffsetDateTime.now(),
                OffsetDateTime.now()
            );
        }

        jdbcTemplate.update(
            QUERY_ADD_COMBINATION,
            chatId,
            link.getId()
        );

        return link;
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
    public Link untrack(Long chatId, URI url) {
        Link link = jdbcTemplate.queryForObject(
            QUERY_FIND_BY_URL,
            new BeanPropertyRowMapper<>(Link.class),
            url.toString()
        );

        jdbcTemplate.update(
            QUERY_UNTRACK,
            chatId,
            link.getId()
        );

        Long chatsCount = jdbcTemplate.queryForObject(
            QUERY_COUNT_CHATS_FOR_LINK,
            Long.class,
            link.getId()
        );

        if (chatsCount == 0) {
            jdbcTemplate.queryForObject(
                QUERY_REMOVE,
                new BeanPropertyRowMapper<>(Link.class),
                link.getId()
            );
        }

        return link;
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
    public Link findByUrl(URI url) {
        return jdbcTemplate.queryForObject(
            QUERY_FIND_BY_URL,
            new BeanPropertyRowMapper<>(Link.class),
            url.toString()
        );
    }

    @Transactional
    public Collection<Link> findLongUnchecked(Duration duration) {
        return jdbcTemplate.query(
            QUERY_FIND_LONG_UNCHECKED,
            new BeanPropertyRowMapper<>(Link.class),
            Timestamp.from(OffsetDateTime.now().minusSeconds(duration.getSeconds()).toInstant())
        );
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
