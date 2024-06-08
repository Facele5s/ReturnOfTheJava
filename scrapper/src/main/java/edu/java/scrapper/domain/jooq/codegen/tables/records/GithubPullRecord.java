/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.codegen.tables.records;


import edu.java.scrapper.domain.jooq.codegen.tables.GithubPull;

import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class GithubPullRecord extends UpdatableRecordImpl<GithubPullRecord> implements Record3<Long, Long, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>GITHUB_PULL.ID</code>.
     */
    public void setId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>GITHUB_PULL.ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>GITHUB_PULL.REPO_ID</code>.
     */
    public void setRepoId(@Nullable Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>GITHUB_PULL.REPO_ID</code>.
     */
    @Nullable
    public Long getRepoId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>GITHUB_PULL.CREATED_AT</code>.
     */
    public void setCreatedAt(@NotNull OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>GITHUB_PULL.CREATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getCreatedAt() {
        return (OffsetDateTime) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row3<Long, Long, OffsetDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row3<Long, Long, OffsetDateTime> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return GithubPull.GITHUB_PULL.ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return GithubPull.GITHUB_PULL.REPO_ID;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field3() {
        return GithubPull.GITHUB_PULL.CREATED_AT;
    }

    @Override
    @NotNull
    public Long component1() {
        return getId();
    }

    @Override
    @Nullable
    public Long component2() {
        return getRepoId();
    }

    @Override
    @NotNull
    public OffsetDateTime component3() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public Long value1() {
        return getId();
    }

    @Override
    @Nullable
    public Long value2() {
        return getRepoId();
    }

    @Override
    @NotNull
    public OffsetDateTime value3() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public GithubPullRecord value1(@NotNull Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public GithubPullRecord value2(@Nullable Long value) {
        setRepoId(value);
        return this;
    }

    @Override
    @NotNull
    public GithubPullRecord value3(@NotNull OffsetDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public GithubPullRecord values(@NotNull Long value1, @Nullable Long value2, @NotNull OffsetDateTime value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GithubPullRecord
     */
    public GithubPullRecord() {
        super(GithubPull.GITHUB_PULL);
    }

    /**
     * Create a detached, initialised GithubPullRecord
     */
    @ConstructorProperties({ "id", "repoId", "createdAt" })
    public GithubPullRecord(@NotNull Long id, @Nullable Long repoId, @NotNull OffsetDateTime createdAt) {
        super(GithubPull.GITHUB_PULL);

        setId(id);
        setRepoId(repoId);
        setCreatedAt(createdAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised GithubPullRecord
     */
    public GithubPullRecord(edu.java.scrapper.domain.jooq.codegen.tables.pojos.GithubPull value) {
        super(GithubPull.GITHUB_PULL);

        if (value != null) {
            setId(value.getId());
            setRepoId(value.getRepoId());
            setCreatedAt(value.getCreatedAt());
            resetChangedOnNotNull();
        }
    }
}