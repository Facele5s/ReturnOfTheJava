CREATE TABLE IF NOT EXISTS Link
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id BIGINT NOT NULL,
    url TEXT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    checked_at TIMESTAMP WITH TIME ZONE,

    PRIMARY KEY (id),
    FOREIGN KEY (chat_id) REFERENCES chat(id)
);
