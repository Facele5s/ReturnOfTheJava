/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.codegen.tables;


import edu.java.scrapper.domain.jooq.codegen.DefaultSchema;
import edu.java.scrapper.domain.jooq.codegen.Keys;
import edu.java.scrapper.domain.jooq.codegen.tables.records.GithubRepositoryRecord;

import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


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
public class GithubRepository extends TableImpl<GithubRepositoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>GITHUB_REPOSITORY</code>
     */
    public static final GithubRepository GITHUB_REPOSITORY = new GithubRepository();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<GithubRepositoryRecord> getRecordType() {
        return GithubRepositoryRecord.class;
    }

    /**
     * The column <code>GITHUB_REPOSITORY.ID</code>.
     */
    public final TableField<GithubRepositoryRecord, Long> ID = createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>GITHUB_REPOSITORY.USER_NAME</code>.
     */
    public final TableField<GithubRepositoryRecord, String> USER_NAME = createField(DSL.name("USER_NAME"), SQLDataType.VARCHAR(1000000000).nullable(false), this, "");

    /**
     * The column <code>GITHUB_REPOSITORY.NAME</code>.
     */
    public final TableField<GithubRepositoryRecord, String> NAME = createField(DSL.name("NAME"), SQLDataType.VARCHAR(1000000000).nullable(false), this, "");

    private GithubRepository(Name alias, Table<GithubRepositoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private GithubRepository(Name alias, Table<GithubRepositoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>GITHUB_REPOSITORY</code> table reference
     */
    public GithubRepository(String alias) {
        this(DSL.name(alias), GITHUB_REPOSITORY);
    }

    /**
     * Create an aliased <code>GITHUB_REPOSITORY</code> table reference
     */
    public GithubRepository(Name alias) {
        this(alias, GITHUB_REPOSITORY);
    }

    /**
     * Create a <code>GITHUB_REPOSITORY</code> table reference
     */
    public GithubRepository() {
        this(DSL.name("GITHUB_REPOSITORY"), null);
    }

    public <O extends Record> GithubRepository(Table<O> child, ForeignKey<O, GithubRepositoryRecord> key) {
        super(child, key, GITHUB_REPOSITORY);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public UniqueKey<GithubRepositoryRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_B;
    }

    @Override
    @NotNull
    public GithubRepository as(String alias) {
        return new GithubRepository(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public GithubRepository as(Name alias) {
        return new GithubRepository(alias, this);
    }

    @Override
    @NotNull
    public GithubRepository as(Table<?> alias) {
        return new GithubRepository(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public GithubRepository rename(String name) {
        return new GithubRepository(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public GithubRepository rename(Name name) {
        return new GithubRepository(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public GithubRepository rename(Table<?> name) {
        return new GithubRepository(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row3<Long, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super Long, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super Long, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}