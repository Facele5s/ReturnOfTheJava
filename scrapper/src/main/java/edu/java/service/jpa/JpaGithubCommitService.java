package edu.java.service.jpa;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubCommit;
import edu.java.scrapper.domain.jpa.JpaGithubCommitRepository;
import edu.java.service.GithubCommitService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaGithubCommitService implements GithubCommitService {
    private final JpaGithubCommitRepository commitRepository;

    @Override
    public GithubCommit add(String sha, Long repoId, String author, OffsetDateTime createdAt)
        throws BadRequestException {
        GithubCommit commit = new GithubCommit();
        commit.setSha(sha);
        commit.setRepoId(repoId);
        commit.setAuthor(author);
        commit.setCreatedAt(createdAt);

        commitRepository.save(commit);

        return commitRepository.findBySha(sha);
    }

    @Override
    public Collection<GithubCommit> getAll() {
        return commitRepository.findAll();
    }

    @Override
    public GithubCommit getBySha(String sha) throws NotFoundException {
        return commitRepository.findBySha(sha);
    }

    @Override
    public Collection<GithubCommit> getByRepo(Long repoId) throws NotFoundException {
        return commitRepository.findByRepoId(repoId);
    }

    @Override
    public Collection<GithubCommit> getByAuthor(String author) throws NotFoundException {
        return commitRepository.findByAuthor(author);
    }

    @Override
    public Collection<GithubCommit> getNewer(OffsetDateTime dateTime) {
        return commitRepository.findByCreatedAtAfter(dateTime);
    }

    @Override
    public GithubCommit getLast() {
        return commitRepository.findTopByOrderByCreatedAtDesc();
    }

    @Override
    public GithubCommit remove(String sha) throws NotFoundException {
        GithubCommit commit = commitRepository.findBySha(sha);

        commitRepository.delete(commit);

        return commit;
    }
}
