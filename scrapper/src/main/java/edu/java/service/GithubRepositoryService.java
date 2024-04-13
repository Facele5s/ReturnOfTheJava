package edu.java.service;

import edu.java.entity.GithubRepository;
import java.util.Collection;

public interface GithubRepositoryService {
    GithubRepository add(Long id, String userName, String name);

    Collection<GithubRepository> getAll();

    GithubRepository getById(Long id);

    Collection<GithubRepository> getByUserName(String userName);

    GithubRepository getByName(String name);

    GithubRepository remove(Long id);

    GithubRepository removeByName(String name);
}
