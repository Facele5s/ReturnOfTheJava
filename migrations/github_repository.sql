CREATE TABLE IF NOT EXISTS Github_repository
(
    id BIGINT,
    user_name TEXT NOT NULL,
    repo_name TEXT NOT NULL,

    PRIMARY KEY (id)
);
