package edu.java.service.jooq;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubCommit;
import edu.java.scrapper.domain.jooq.JooqGithubCommitRepository;
import edu.java.service.GithubCommitService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqGithubCommitService implements GithubCommitService {
    private final JooqGithubCommitRepository commitRepository;

    @Override
    public GithubCommit add(String sha, Long repoId, String author, OffsetDateTime createdAt)
        throws BadRequestException {
        return commitRepository.add(sha, repoId, author, createdAt);
    }

    @Override
    public Collection<GithubCommit> getAll() {
        return commitRepository.findAll();
    }

    @Override
    public GithubCommit getBySha(String sha) {
        return commitRepository.find(sha);
    }

    @Override
    public Collection<GithubCommit> getByRepo(Long repoId) throws NotFoundException {
        return commitRepository.findByRepo(repoId);
    }

    @Override
    public Collection<GithubCommit> getByAuthor(String author) {
        return commitRepository.findByAuthor(author);
    }

    @Override
    public Collection<GithubCommit> getNewer(OffsetDateTime dateTime) {
        return commitRepository.findNewer(dateTime);
    }

    @Override
    public GithubCommit getLast() {
        return commitRepository.findLast();
    }

    @Override
    public GithubCommit remove(String sha) throws NotFoundException {
        return commitRepository.remove(sha);
    }
}
