CREATE TABLE IF NOT EXISTS users (
    chat_id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    added_at TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE NULL
);

CREATE TABLE IF NOT EXISTS links (
    link_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    added_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE NULL
);

CREATE TABLE IF NOT EXISTS user_links (
    user_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE NULL,
    FOREIGN KEY (user_id) REFERENCES Users(chat_id),
    FOREIGN KEY (link_id) REFERENCES Links(link_id),
    PRIMARY KEY (user_id, link_id)
);