package edu.java.scrapper.domain.jooq;

import edu.java.entity.GithubCommit;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.codegen.Tables.GITHUB_COMMIT;

@Repository
@RequiredArgsConstructor
public class JooqGithubCommitRepository {
    private final DSLContext dslContext;

    @Transactional
    public GithubCommit add(String sha, Long repoId, String author, OffsetDateTime createdAt) {
        return dslContext
            .insertInto(
                GITHUB_COMMIT,
                GITHUB_COMMIT.SHA,
                GITHUB_COMMIT.REPO_ID,
                GITHUB_COMMIT.AUTHOR,
                GITHUB_COMMIT.CREATED_AT
            )
            .values(sha, repoId, author, createdAt)
            .returning(GITHUB_COMMIT.fields())
            .fetchOneInto(GithubCommit.class);
    }

    @Transactional
    public Collection<GithubCommit> findAll() {
        return dslContext
            .selectFrom(GITHUB_COMMIT)
            .fetchInto(GithubCommit.class);
    }

    @Transactional
    public GithubCommit find(String sha) {
        return dslContext
            .selectFrom(GITHUB_COMMIT)
            .where(GITHUB_COMMIT.SHA.eq(sha))
            .fetchOneInto(GithubCommit.class);
    }

    @Transactional
    public Collection<GithubCommit> findByRepo(Long repoId) {
        return dslContext
            .selectFrom(GITHUB_COMMIT)
            .where(GITHUB_COMMIT.REPO_ID.eq(repoId))
            .fetchInto(GithubCommit.class);
    }

    @Transactional
    public Collection<GithubCommit> findByAuthor(String author) {
        return dslContext
            .selectFrom(GITHUB_COMMIT)
            .where(GITHUB_COMMIT.AUTHOR.eq(author))
            .fetchInto(GithubCommit.class);
    }

    @Transactional
    public Collection<GithubCommit> findNewer(OffsetDateTime dateTime) {
        return dslContext
            .selectFrom(GITHUB_COMMIT)
            .where(GITHUB_COMMIT.CREATED_AT.greaterThan(dateTime))
            .fetchInto(GithubCommit.class);
    }

    @Transactional
    public GithubCommit findLast() {
        return dslContext
            .selectFrom(GITHUB_COMMIT)
            .orderBy(GITHUB_COMMIT.CREATED_AT.desc())
            .limit(1)
            .fetchOneInto(GithubCommit.class);
    }

    @Transactional
    public GithubCommit remove(String sha) {
        return dslContext
            .deleteFrom(GITHUB_COMMIT)
            .where(GITHUB_COMMIT.SHA.eq(sha))
            .returning(GITHUB_COMMIT.fields())
            .fetchOneInto(GithubCommit.class);
    }
}
