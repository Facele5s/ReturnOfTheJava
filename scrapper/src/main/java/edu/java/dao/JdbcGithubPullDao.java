package edu.java.dao;

import edu.java.entity.GithubPull;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcGithubPullDao {
    private static final String QUERY_ADD = "INSERT INTO github_pull"
        + " (id, repo_id, created_at) VALUES (?, ?, ?) RETURNING *";
    private static final String QUERY_FIND_ALL = "SELECT * FROM github_pull";
    private static final String QUERY_FIND = "SELECT * FROM github_pull WHERE id = ?";
    private static final String QUERY_FIND_BY_REPO = "SELECT * FROM github_pull WHERE repo_id = ?";
    private static final String QUERY_FIND_NEWER = "SELECT * FROM github_pull WHERE created_at > ?";
    private static final String QUERY_REMOVE = "DELETE FROM github_pull WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public GithubPull add(Long id, Long repoId, OffsetDateTime createdAt) {
        return jdbcTemplate.queryForObject(
            QUERY_ADD,
            new BeanPropertyRowMapper<>(GithubPull.class),
            id,
            repoId,
            createdAt
        );
    }

    @Transactional
    public Collection<GithubPull> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(GithubPull.class));
    }

    @Transactional
    public GithubPull find(Long id) {
        return jdbcTemplate.queryForObject(
            QUERY_FIND,
            new BeanPropertyRowMapper<>(GithubPull.class),
            id
        );
    }

    @Transactional
    public Collection<GithubPull> findByRepo(Long repoId) {
        return jdbcTemplate.query(QUERY_FIND_BY_REPO, new BeanPropertyRowMapper<>(GithubPull.class), repoId);
    }

    @Transactional
    public Collection<GithubPull> findNewer(OffsetDateTime dateTime) {
        return jdbcTemplate.query(
            QUERY_FIND_NEWER,
            new BeanPropertyRowMapper<>(GithubPull.class),
            dateTime.toInstant()
        );
    }

    @Transactional
    public GithubPull remove(Long id) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE,
            new BeanPropertyRowMapper<>(GithubPull.class),
            id
        );
    }
}
