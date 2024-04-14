package edu.java.service.jdbc;

import edu.java.dao.JdbcGithubReleaseDao;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubRelease;
import edu.java.service.GithubReleaseService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

@RequiredArgsConstructor
public class JdbcGithubReleaseService implements GithubReleaseService {
    private static final String MSG_RELEASE_ADDED = "The release is already added";
    private static final String MSG_RELEASE_NOT_FOUND = "The release was not found";

    private static final String DESC_RELEASE_EXISTS = "You can't add the release twice";
    private static final String DESC_RELEASE_NOT_EXISTS = "The release does not exist";

    private final JdbcGithubReleaseDao releaseDao;

    @Override
    public GithubRelease add(Long id, Long repoId, OffsetDateTime createdAt)
        throws BadRequestException {
        try {
            return releaseDao.add(id, repoId, createdAt);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException(
                MSG_RELEASE_ADDED,
                DESC_RELEASE_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubRelease> getAll() {
        return releaseDao.findAll();
    }

    @Override
    public GithubRelease getById(Long id) throws NotFoundException {
        try {
            return releaseDao.find(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_RELEASE_NOT_FOUND,
                DESC_RELEASE_NOT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubRelease> getByRepo(Long repoId) throws NotFoundException {
        try {
            return releaseDao.findByRepo(repoId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_RELEASE_NOT_FOUND,
                DESC_RELEASE_NOT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubRelease> getNewer(OffsetDateTime dateTime) {
        return releaseDao.findNewer(dateTime);
    }

    @Override
    public GithubRelease remove(Long id) throws NotFoundException {
        try {
            return releaseDao.remove(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_RELEASE_NOT_FOUND,
                DESC_RELEASE_NOT_EXISTS
            );
        }
    }
}
