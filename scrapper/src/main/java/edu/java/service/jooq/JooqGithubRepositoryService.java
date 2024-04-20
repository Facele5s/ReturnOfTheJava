package edu.java.service.jooq;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubRepository;
import edu.java.scrapper.domain.jooq.JooqGithubRepoRepository;
import edu.java.service.GithubRepositoryService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqGithubRepositoryService implements GithubRepositoryService {
    private final JooqGithubRepoRepository repoRepository;

    @Override
    public GithubRepository add(Long id, String userName, String name) throws BadRequestException {
        return repoRepository.add(id, userName, name);
    }

    @Override
    public Collection<GithubRepository> getAll() {
        return repoRepository.findAll();
    }

    @Override
    public GithubRepository getById(Long id) throws NotFoundException {
        return repoRepository.find(id);
    }

    @Override
    public Collection<GithubRepository> getByUserName(String userName) {
        return repoRepository.findByUserName(userName);
    }

    @Override
    public GithubRepository getByName(String name) {
        return repoRepository.findByName(name);
    }

    @Override
    public GithubRepository remove(Long id) throws NotFoundException {
        return repoRepository.remove(id);
    }

    @Override
    public GithubRepository removeByName(String name) {
        return repoRepository.removeByName(name);
    }
}
