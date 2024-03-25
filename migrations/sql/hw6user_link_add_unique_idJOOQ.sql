CREATE TABLE user_links_temp (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE NULL,
    FOREIGN KEY (user_id) REFERENCES Users(chat_id),
    FOREIGN KEY (link_id) REFERENCES Links(link_id)
);

INSERT INTO user_links_temp (user_id, link_id, deleted_at)
SELECT user_id, link_id, deleted_at
FROM user_links;

DROP TABLE user_links;

CREATE TABLE user_links (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE NULL,
    FOREIGN KEY (user_id) REFERENCES Users(chat_id),
    FOREIGN KEY (link_id) REFERENCES Links(link_id)
);

INSERT INTO user_links (user_id, link_id, deleted_at)
SELECT user_id, link_id, deleted_at
FROM user_links_temp;

DROP TABLE user_links_temp;
