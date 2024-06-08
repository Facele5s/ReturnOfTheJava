CREATE TABLE IF NOT EXISTS Github_commit
(
    sha TEXT NOT NULL,
    repo_id BIGINT,
    author TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (sha),
    FOREIGN KEY (repo_id) REFERENCES github_repository(id) ON DELETE CASCADE
);
