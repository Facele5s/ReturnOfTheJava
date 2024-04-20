package edu.java.scrapper.domain.jooq;

import edu.java.entity.GithubRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.codegen.Tables.GITHUB_REPOSITORY;

@Repository
@RequiredArgsConstructor
public class JooqGithubRepoRepository {
    private final DSLContext dslContext;

    @Transactional
    public GithubRepository add(Long id, String userName, String name) {
        return dslContext
            .insertInto(GITHUB_REPOSITORY, GITHUB_REPOSITORY.ID, GITHUB_REPOSITORY.USER_NAME, GITHUB_REPOSITORY.NAME)
            .values(id, userName, name)
            .returning(GITHUB_REPOSITORY.fields())
            .fetchOneInto(GithubRepository.class);
    }

    @Transactional
    public Collection<GithubRepository> findAll() {
        return dslContext
            .selectFrom(GITHUB_REPOSITORY)
            .fetchInto(GithubRepository.class);
    }

    @Transactional
    public GithubRepository find(Long id) {
        return dslContext
            .selectFrom(GITHUB_REPOSITORY)
            .where(GITHUB_REPOSITORY.ID.eq(id))
            .fetchOneInto(GithubRepository.class);
    }

    @Transactional
    public Collection<GithubRepository> findByUserName(String userName) {
        return dslContext
            .selectFrom(GITHUB_REPOSITORY)
            .where(GITHUB_REPOSITORY.USER_NAME.eq(userName))
            .fetchInto(GithubRepository.class);
    }

    @Transactional
    public GithubRepository findByName(String name) {
        return dslContext
            .selectFrom(GITHUB_REPOSITORY)
            .where(GITHUB_REPOSITORY.NAME.eq(name))
            .fetchOneInto(GithubRepository.class);
    }

    @Transactional
    public GithubRepository remove(Long id) {
        return dslContext
            .deleteFrom(GITHUB_REPOSITORY)
            .where(GITHUB_REPOSITORY.ID.eq(id))
            .returning(GITHUB_REPOSITORY.fields())
            .fetchOneInto(GithubRepository.class);
    }

    @Transactional
    public GithubRepository removeByName(String name) {
        return dslContext
            .deleteFrom(GITHUB_REPOSITORY)
            .where(GITHUB_REPOSITORY.NAME.eq(name))
            .returning(GITHUB_REPOSITORY.fields())
            .fetchOneInto(GithubRepository.class);
    }
}
