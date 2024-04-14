package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubRepository;
import java.util.Collection;

public interface GithubRepositoryService {
    GithubRepository add(Long id, String userName, String name) throws BadRequestException;

    Collection<GithubRepository> getAll();

    GithubRepository getById(Long id) throws NotFoundException;

    Collection<GithubRepository> getByUserName(String userName) throws NotFoundException;

    GithubRepository getByName(String name) throws NotFoundException;

    GithubRepository remove(Long id) throws NotFoundException;

    GithubRepository removeByName(String name) throws NotFoundException;
}
