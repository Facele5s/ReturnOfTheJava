package edu.java.scrapper.domain.jdbc;

import edu.java.entity.GithubRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcGithubRepositoryDao {
    private static final String QUERY_ADD = "INSERT INTO github_repository"
        + " (id, user_name, name) VALUES (?, ?, ?) RETURNING *";
    private static final String QUERY_FIND_ALL = "SELECT * FROM github_repository";
    private static final String QUERY_FIND = "SELECT * FROM github_repository WHERE id = ?";
    private static final String QUERY_FIND_BY_USER_NAME = "SELECT * FROM github_repository WHERE user_name = ?";
    private static final String QUERY_FIND_BY_NAME = "SELECT * FROM github_repository WHERE name = ?";
    private static final String QUERY_REMOVE = "DELETE FROM github_repository WHERE id = ?";
    private static final String QUERY_REMOVE_BY_NAME = "DELETE FROM github_repository WHERE name = ?";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public GithubRepository add(Long id, String userName, String name) {
        return jdbcTemplate.queryForObject(
            QUERY_ADD,
            new BeanPropertyRowMapper<>(GithubRepository.class),
            id,
            userName,
            name
        );
    }

    @Transactional
    public Collection<GithubRepository> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(GithubRepository.class));
    }

    @Transactional
    public GithubRepository find(Long id) {
        return jdbcTemplate.queryForObject(
            QUERY_FIND,
            new BeanPropertyRowMapper<>(GithubRepository.class),
            id
        );
    }

    @Transactional
    public Collection<GithubRepository> findByUserName(String userName) {
        return jdbcTemplate.query(
            QUERY_FIND_BY_USER_NAME,
            new BeanPropertyRowMapper<>(GithubRepository.class),
            userName
        );
    }

    @Transactional
    public GithubRepository findByName(String name) {
        return jdbcTemplate.queryForObject(
            QUERY_FIND_BY_NAME,
            new BeanPropertyRowMapper<>(GithubRepository.class),
            name
        );
    }

    @Transactional
    public GithubRepository remove(Long id) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE,
            new BeanPropertyRowMapper<>(GithubRepository.class),
            id
        );
    }

    @Transactional
    public GithubRepository removeByName(String name) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE_BY_NAME,
            new BeanPropertyRowMapper<>(GithubRepository.class),
            name
        );
    }
}
