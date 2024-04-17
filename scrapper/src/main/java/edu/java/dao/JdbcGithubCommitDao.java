package edu.java.dao;

import edu.java.entity.GithubCommit;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcGithubCommitDao {
    private static final String QUERY_ADD = "INSERT INTO github_commit"
        + " (sha, repo_id, author, created_at) VALUES (?, ?, ?, ?) RETURNING *";
    private static final String QUERY_FIND_ALL = "SELECT * FROM github_commit";
    private static final String QUERY_FIND = "SELECT * FROM github_commit WHERE sha = ?";
    private static final String QUERY_FIND_BY_REPO = "SELECT * FROM github_commit WHERE repo_id = ?";
    private static final String QUERY_FIND_BY_AUTHOR = "SELECT * FROM github_commit WHERE author = ?";
    private static final String QUERY_FIND_NEWER = "SELECT * FROM github_commit WHERE created_at > ?";
    private static final String QUERY_FIND_LAST = "SELECT * FROM github_commit ORDER BY created_at DESC LIMIT 1";
    private static final String QUERY_REMOVE = "DELETE FROM github_commit WHERE sha = ? RETURNING *";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public GithubCommit add(String sha, Long repoId, String author, OffsetDateTime createdAt) {
        return jdbcTemplate.queryForObject(
            QUERY_ADD,
            new BeanPropertyRowMapper<>(GithubCommit.class),
            sha,
            repoId,
            author,
            createdAt
        );
    }

    @Transactional
    public Collection<GithubCommit> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(GithubCommit.class));
    }

    @Transactional
    public GithubCommit find(String sha) {
        return jdbcTemplate.queryForObject(
            QUERY_FIND,
            new BeanPropertyRowMapper<>(GithubCommit.class),
            sha
        );
    }

    @Transactional
    public Collection<GithubCommit> findByRepo(Long repoId) {
        return jdbcTemplate.query(QUERY_FIND_BY_REPO, new BeanPropertyRowMapper<>(GithubCommit.class), repoId);
    }

    @Transactional
    public Collection<GithubCommit> findByAuthor(String author) {
        return jdbcTemplate.query(QUERY_FIND_BY_AUTHOR, new BeanPropertyRowMapper<>(GithubCommit.class), author);
    }

    @Transactional
    public Collection<GithubCommit> findNewer(OffsetDateTime dateTime) {
        return jdbcTemplate.query(
            QUERY_FIND_NEWER,
            new BeanPropertyRowMapper<>(GithubCommit.class),
            dateTime.toInstant()
        );
    }

    @Transactional
    public GithubCommit findLast() {
        return jdbcTemplate.queryForObject(
            QUERY_FIND_LAST,
            new BeanPropertyRowMapper<>(GithubCommit.class)
        );
    }

    @Transactional
    public GithubCommit remove(String sha) {
        return jdbcTemplate.queryForObject(
            QUERY_REMOVE,
            new BeanPropertyRowMapper<>(GithubCommit.class),
            sha
        );
    }
}
