package edu.java.service.jdbc;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubCommit;
import edu.java.scrapper.domain.jdbc.JdbcGithubCommitDao;
import edu.java.service.GithubCommitService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

@RequiredArgsConstructor
public class JdbcGithubCommitService implements GithubCommitService {
    private static final String MSG_COMMIT_ADDED = "The commit is already added";
    private static final String MSG_COMMIT_NOT_FOUND = "The commit was not found";

    private static final String DESC_COMMIT_EXISTS = "You can't add the commit twice";
    private static final String DESC_COMMIT_NOT_EXISTS = "The commit does not exist";

    private final JdbcGithubCommitDao commitDao;

    @Override
    public GithubCommit add(String sha, Long repoId, String author, OffsetDateTime createdAt)
     throws BadRequestException {
        try {
            return commitDao.add(sha, repoId, author, createdAt);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException(
                MSG_COMMIT_ADDED,
                DESC_COMMIT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubCommit> getAll() {
        return commitDao.findAll();
    }

    @Override
    public GithubCommit getBySha(String sha) throws NotFoundException {
        try {
            return commitDao.find(sha);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_COMMIT_NOT_FOUND,
                DESC_COMMIT_NOT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubCommit> getByRepo(Long repoId) throws NotFoundException {
        try {
            return commitDao.findByRepo(repoId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_COMMIT_NOT_FOUND,
                DESC_COMMIT_NOT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubCommit> getByAuthor(String author) throws NotFoundException {
        try {
            return commitDao.findByAuthor(author);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_COMMIT_NOT_FOUND,
                DESC_COMMIT_NOT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubCommit> getNewer(OffsetDateTime dateTime) {
        return commitDao.findNewer(dateTime);
    }

    @Override
    public GithubCommit getLast() {
        return commitDao.findLast();
    }

    @Override
    public GithubCommit remove(String sha) throws NotFoundException {
        try {
            return commitDao.remove(sha);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_COMMIT_NOT_FOUND,
                DESC_COMMIT_NOT_EXISTS
            );
        }
    }
}
