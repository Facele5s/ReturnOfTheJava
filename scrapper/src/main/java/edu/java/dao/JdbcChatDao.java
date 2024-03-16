package edu.java.dao;

import edu.java.entity.Chat;
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

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public Chat add(Chat chat) {
        return jdbcTemplate.queryForObject(
            QUERY_ADD,
            new BeanPropertyRowMapper<>(Chat.class),
            chat.getId(),
            chat.getRegistrationDate()
        );
    }

    @Transactional
    public Chat remove(Chat chat) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE,
            new BeanPropertyRowMapper<>(Chat.class),
            chat.getId()
        );
    }

    @Transactional
    public Collection<Chat> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(Chat.class));
    }
}
