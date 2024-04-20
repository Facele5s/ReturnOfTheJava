package edu.java.service.jdbc;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubRepository;
import edu.java.scrapper.domain.jdbc.JdbcGithubRepositoryDao;
import edu.java.service.GithubRepositoryService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

@RequiredArgsConstructor
public class JdbcGithubRepositoryService implements GithubRepositoryService {
    private static final String MSG_REPOSITORY_ADDED = "The repository is already added";
    private static final String MSG_REPOSITORY_NOT_FOUND = "The repository was not found";

    private static final String DESC_REPOSITORY_EXISTS = "You can't add the repository twice";
    private static final String DESC_REPOSITORY_NOT_EXISTS = "The repository does not exist";

    private final JdbcGithubRepositoryDao repositoryDao;

    @Override
    public GithubRepository add(Long id, String userName, String name)
        throws BadRequestException {
        try {
            return repositoryDao.add(id, userName, name);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException(
                MSG_REPOSITORY_ADDED,
                DESC_REPOSITORY_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubRepository> getAll() {
        return repositoryDao.findAll();
    }

    @Override
    public GithubRepository getById(Long id) throws NotFoundException {
        try {
            return repositoryDao.find(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_REPOSITORY_NOT_FOUND,
                DESC_REPOSITORY_NOT_EXISTS
            );
        }
    }

    @Override
    public Collection<GithubRepository> getByUserName(String userName) throws NotFoundException {
        try {
            return repositoryDao.findByUserName(userName);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_REPOSITORY_NOT_FOUND,
                DESC_REPOSITORY_NOT_EXISTS
            );
        }
    }

    @Override
    public GithubRepository getByName(String name) throws NotFoundException {
        try {
            return repositoryDao.findByName(name);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_REPOSITORY_NOT_FOUND,
                DESC_REPOSITORY_NOT_EXISTS
            );
        }
    }

    @Override
    public GithubRepository remove(Long id) throws NotFoundException {
        try {
            return repositoryDao.remove(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_REPOSITORY_NOT_FOUND,
                DESC_REPOSITORY_NOT_EXISTS
            );
        }
    }

    @Override
    public GithubRepository removeByName(String name) throws NotFoundException {
        try {
            return repositoryDao.removeByName(name);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_REPOSITORY_NOT_FOUND,
                DESC_REPOSITORY_NOT_EXISTS
            );
        }
    }
}
