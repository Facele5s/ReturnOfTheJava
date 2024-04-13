package edu.java.dao;

import edu.java.entity.GithubRelease;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcGithubReleaseDao {
    private static final String QUERY_ADD = "INSERT INTO github_release"
        + " (id, repo_id, published_at) VALUES (?, ?, ?) RETURNING *";
    private static final String QUERY_FIND_ALL = "SELECT * FROM github_release";
    private static final String QUERY_FIND = "SELECT * FROM github_release WHERE id = ?";
    private static final String QUERY_FIND_BY_REPO = "SELECT * FROM github_release WHERE repo_id = ?";
    private static final String QUERY_FIND_NEW = "SELECT * FROM github_release WHERE published_at > ?";
    private static final String QUERY_REMOVE = "DELETE FROM github_release WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public GithubRelease add(Long id, Long repoId, OffsetDateTime createdAt) {
        return jdbcTemplate.queryForObject(
            QUERY_ADD,
            new BeanPropertyRowMapper<>(GithubRelease.class),
            id,
            repoId,
            createdAt
        );
    }

    @Transactional
    public Collection<GithubRelease> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(GithubRelease.class));
    }

    @Transactional
    public GithubRelease find(Long id) {
        return jdbcTemplate.queryForObject(
            QUERY_FIND,
            new BeanPropertyRowMapper<>(GithubRelease.class),
            id
        );
    }

    @Transactional
    public Collection<GithubRelease> findByRepo(Long repoId) {
        return jdbcTemplate.query(QUERY_FIND_BY_REPO, new BeanPropertyRowMapper<>(GithubRelease.class), repoId);
    }

    @Transactional
    public Collection<GithubRelease> findNew(OffsetDateTime dateTime) {
        return jdbcTemplate.query(QUERY_FIND_NEW, new BeanPropertyRowMapper<>(GithubRelease.class), dateTime);
    }

    @Transactional
    public GithubRelease remove(Long id) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE,
            new BeanPropertyRowMapper<>(GithubRelease.class),
            id
        );
    }
}
