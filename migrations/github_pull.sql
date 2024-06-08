CREATE TABLE IF NOT EXISTS Github_pull
(
    id BIGINT,
    repo_id BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (repo_id) REFERENCES github_repository(id) ON DELETE CASCADE
);
