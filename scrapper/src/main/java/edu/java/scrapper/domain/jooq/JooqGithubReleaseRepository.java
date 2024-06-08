package edu.java.scrapper.domain.jooq;

import edu.java.entity.GithubRelease;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.codegen.Tables.GITHUB_RELEASE;

@Repository
@RequiredArgsConstructor
public class JooqGithubReleaseRepository {
    private final DSLContext dslContext;

    @Transactional
    public GithubRelease add(Long id, Long repoId, OffsetDateTime createdAt) {
        return dslContext
            .insertInto(GITHUB_RELEASE, GITHUB_RELEASE.ID, GITHUB_RELEASE.REPO_ID, GITHUB_RELEASE.PUBLISHED_AT)
            .values(id, repoId, createdAt)
            .returning(GITHUB_RELEASE.fields())
            .fetchOneInto(GithubRelease.class);
    }

    @Transactional
    public Collection<GithubRelease> findAll() {
        return dslContext
            .selectFrom(GITHUB_RELEASE)
            .fetchInto(GithubRelease.class);
    }

    @Transactional
    public GithubRelease find(Long id) {
        return dslContext
            .selectFrom(GITHUB_RELEASE)
            .where(GITHUB_RELEASE.ID.eq(id))
            .fetchOneInto(GithubRelease.class);
    }

    @Transactional
    public Collection<GithubRelease> findByRepo(Long repoId) {
        return dslContext
            .selectFrom(GITHUB_RELEASE)
            .where(GITHUB_RELEASE.REPO_ID.eq(repoId))
            .fetchInto(GithubRelease.class);
    }

    @Transactional
    public Collection<GithubRelease> findNewer(OffsetDateTime dateTime) {
        return dslContext
            .selectFrom(GITHUB_RELEASE)
            .where(GITHUB_RELEASE.PUBLISHED_AT.greaterThan(dateTime))
            .fetchInto(GithubRelease.class);
    }

    @Transactional
    public GithubRelease findLast() {
        return dslContext
            .selectFrom(GITHUB_RELEASE)
            .orderBy(GITHUB_RELEASE.PUBLISHED_AT.desc())
            .limit(1)
            .fetchOneInto(GithubRelease.class);
    }

    @Transactional
    public GithubRelease remove(Long id) {
        return dslContext
            .deleteFrom(GITHUB_RELEASE)
            .where(GITHUB_RELEASE.ID.eq(id))
            .returning(GITHUB_RELEASE.fields())
            .fetchOneInto(GithubRelease.class);
    }
}
