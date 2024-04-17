package edu.java.service.jdbc;

import edu.java.dao.JdbcGithubPullDao;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubPull;
import edu.java.service.GithubPullService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

@RequiredArgsConstructor
public class JdbcGithubPullService implements GithubPullService {
    private static final String MSG_PULL_ADDED = "The pull is already added";
    private static final String MSG_PULL_NOT_FOUND = "The pull was not found";

    private static final String DESC_PULL_EXISTS = "You can't add the pull twice";
    private static final String DESC_PULL_NOT_EXISTS = "The pull does not exist";

    private final JdbcGithubPullDao pullDao;

    @Override
    public GithubPull add(Long id, Long repoId, OffsetDateTime createdAt)
        throws BadRequestException {
        try {
            return pullDao.add(id, repoId, createdAt);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException(
                MSG_PULL_ADDED,
                DESC_PULL_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubPull> getAll() {
        return pullDao.findAll();
    }

    @Override
    public GithubPull getById(Long id) throws NotFoundException {
        try {
            return pullDao.find(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_PULL_NOT_FOUND,
                DESC_PULL_NOT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubPull> getByRepo(Long repoId) throws NotFoundException {
        try {
            return pullDao.findByRepo(repoId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_PULL_NOT_FOUND,
                DESC_PULL_NOT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubPull> getNewer(OffsetDateTime dateTime) {
        return pullDao.findNewer(dateTime);
    }

    @Override
    public GithubPull getLast() {
        return pullDao.findLast();
    }

    @Override
    public GithubPull remove(Long id) throws NotFoundException {
        try {
            return pullDao.remove(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_PULL_NOT_FOUND,
                DESC_PULL_NOT_EXISTS
            );
        }
    }
}
