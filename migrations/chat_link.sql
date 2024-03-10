CREATE TABLE IF NOT EXISTS Chat_Link
(
    chat_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,

    PRIMARY KEY (chat_id, link_id),
    FOREIGN KEY (chat_id) REFERENCES chat(id),
    FOREIGN KEY (link_id) REFERENCES link(id)
);
