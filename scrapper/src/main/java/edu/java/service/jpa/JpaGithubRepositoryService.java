package edu.java.service.jpa;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubRepository;
import edu.java.repository.JpaGithubRepoRepository;
import edu.java.service.GithubRepositoryService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaGithubRepositoryService implements GithubRepositoryService {
    private final JpaGithubRepoRepository repoRepository;

    @Override
    public GithubRepository add(Long id, String userName, String name) throws BadRequestException {
        GithubRepository repository = new GithubRepository();
        repository.setId(id);
        repository.setUserName(userName);
        repository.setName(name);

        repoRepository.save(repository);

        return repoRepository.findById(id).get();
    }

    @Override
    public Collection<GithubRepository> getAll() {
        return repoRepository.findAll();
    }

    @Override
    public GithubRepository getById(Long id) throws NotFoundException {
        return repoRepository.findById(id).get();
    }

    @Override
    public Collection<GithubRepository> getByUserName(String userName) throws NotFoundException {
        return repoRepository.findByUserName(userName);
    }

    @Override
    public GithubRepository getByName(String name) throws NotFoundException {
        return repoRepository.findByName(name);
    }

    @Override
    public GithubRepository remove(Long id) throws NotFoundException {
        GithubRepository repository = repoRepository.findById(id).get();

        repoRepository.delete(repository);

        return repository;
    }

    @Override
    public GithubRepository removeByName(String name) throws NotFoundException {
        GithubRepository repository = repoRepository.findByName(name);

        repoRepository.delete(repository);

        return repository;
    }
}
