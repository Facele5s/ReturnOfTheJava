package edu.java.scrapper.domain.jooq;

import edu.java.entity.GithubPull;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.codegen.Tables.GITHUB_PULL;

@Repository
@RequiredArgsConstructor
public class JooqGithubPullRepository {
    private final DSLContext dslContext;

    @Transactional
    public GithubPull add(Long id, Long repoId, OffsetDateTime createdAt) {
        return dslContext
            .insertInto(GITHUB_PULL, GITHUB_PULL.ID, GITHUB_PULL.REPO_ID, GITHUB_PULL.CREATED_AT)
            .values(id, repoId, createdAt)
            .returning(GITHUB_PULL.fields())
            .fetchOneInto(GithubPull.class);
    }

    @Transactional
    public Collection<GithubPull> findAll() {
        return dslContext
            .selectFrom(GITHUB_PULL)
            .fetchInto(GithubPull.class);
    }

    @Transactional
    public GithubPull find(Long id) {
        return dslContext
            .selectFrom(GITHUB_PULL)
            .where(GITHUB_PULL.ID.eq(id))
            .fetchOneInto(GithubPull.class);
    }

    @Transactional
    public Collection<GithubPull> findByRepo(Long repoId) {
        return dslContext
            .selectFrom(GITHUB_PULL)
            .where(GITHUB_PULL.REPO_ID.eq(repoId))
            .fetchInto(GithubPull.class);
    }

    @Transactional
    public Collection<GithubPull> findNewer(OffsetDateTime dateTime) {
        return dslContext
            .selectFrom(GITHUB_PULL)
            .where(GITHUB_PULL.CREATED_AT.greaterThan(dateTime))
            .fetchInto(GithubPull.class);
    }

    @Transactional
    public GithubPull findLast() {
        return dslContext
            .selectFrom(GITHUB_PULL)
            .orderBy(GITHUB_PULL.CREATED_AT.desc())
            .limit(1)
            .fetchOneInto(GithubPull.class);
    }

    @Transactional
    public GithubPull remove(Long id) {
        return dslContext
            .deleteFrom(GITHUB_PULL)
            .where(GITHUB_PULL.ID.eq(id))
            .returning(GITHUB_PULL.fields())
            .fetchOneInto(GithubPull.class);
    }
}
