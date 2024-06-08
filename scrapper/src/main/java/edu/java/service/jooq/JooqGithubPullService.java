package edu.java.service.jooq;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubPull;
import edu.java.scrapper.domain.jooq.JooqGithubPullRepository;
import edu.java.service.GithubPullService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqGithubPullService implements GithubPullService {
    private final JooqGithubPullRepository pullRepository;

    @Override
    public GithubPull add(Long id, Long repoId, OffsetDateTime createdAt) throws BadRequestException {
        return pullRepository.add(id, repoId, createdAt);
    }

    @Override
    public Collection<GithubPull> getAll() {
        return pullRepository.findAll();
    }

    @Override
    public GithubPull getById(Long id) throws NotFoundException {
        return pullRepository.find(id);
    }

    @Override
    public Collection<GithubPull> getByRepo(Long repoId) throws NotFoundException {
        return pullRepository.findByRepo(repoId);
    }

    @Override
    public Collection<GithubPull> getNewer(OffsetDateTime dateTime) {
        return pullRepository.findNewer(dateTime);
    }

    @Override
    public GithubPull getLast() {
        return pullRepository.findLast();
    }

    @Override
    public GithubPull remove(Long id) throws NotFoundException {
        return pullRepository.remove(id);
    }
}
