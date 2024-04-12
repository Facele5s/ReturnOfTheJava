CREATE TABLE IF NOT EXISTS Github_release
(
    id BIGINT,
    repo_id BIGINT,
    published_at TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (repo_id) REFERENCES github_repository(id) ON DELETE CASCADE
);
