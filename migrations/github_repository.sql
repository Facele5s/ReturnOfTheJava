CREATE TABLE IF NOT EXISTS Github_repository
(
    id BIGINT,
    link_id BIGINT,
    user_name TEXT NOT NULL,
    name TEXT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY(link_id) REFERENCES link(id) ON DELETE CASCADE
);
