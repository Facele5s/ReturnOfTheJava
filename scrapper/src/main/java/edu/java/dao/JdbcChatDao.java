package edu.java.dao;

import edu.java.entity.Chat;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcChatDao {
    private static final String QUERY_ADD = "INSERT INTO chat (registration_date) VALUES (?) RETURNING *";
    private static final String QUERY_REMOVE = "DELETE FROM chat WHERE id = ? RETURNING *";
    private static final String QUERY_FIND_ALL = "SELECT * FROM chat";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM chat WHERE id = ?";
    private static final String QUERY_FIND_BY_CHAT =
        "SELECT * FROM chat WHERE id IN (SELECT chat_id FROM chat_link WHERE link_id = ?)";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public Chat add(OffsetDateTime registrationDate) {
        return jdbcTemplate.queryForObject(
            QUERY_ADD,
            new BeanPropertyRowMapper<>(Chat.class),
            registrationDate
        );
    }

    @Transactional
    public Chat remove(Long chatId) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE,
            new BeanPropertyRowMapper<>(Chat.class),
            chatId
        );
    }

    @Transactional
    public Collection<Chat> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(Chat.class));
    }

    @Transactional
    public Chat findById(Long chatId) {
        return jdbcTemplate.queryForObject(
            QUERY_FIND_BY_ID,
            new BeanPropertyRowMapper<>(Chat.class),
            chatId
        );
    }

    @Transactional
    public Collection<Chat> findByLink(Long linkId) {
        return jdbcTemplate.query(QUERY_FIND_BY_CHAT, new BeanPropertyRowMapper<>(Chat.class), linkId);
    }
}
