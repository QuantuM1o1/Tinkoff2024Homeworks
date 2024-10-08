CREATE TABLE IF NOT EXISTS users (
    tg_chat_id BIGINT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS users_archive (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    tg_chat_id BIGINT,
    added_at TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE NULL
);

CREATE TABLE IF NOT EXISTS links_sites (
    id INT PRIMARY KEY,
    domain_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS links (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    url TEXT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    last_activity TIMESTAMP WITH TIME ZONE NULL,
    site_id INT NOT NULL,
    answer_count INT,
    comment_count INT,
    CONSTRAINT fl_links_sites FOREIGN KEY (site_id) REFERENCES links_sites(id)
);

INSERT INTO links_sites (id, domain_name)
VALUES (0, 'unsupported')
ON CONFLICT DO NOTHING;
INSERT INTO links_sites (id, domain_name)
VALUES (1, 'stackoverflow.com')
ON CONFLICT DO NOTHING;
INSERT INTO links_sites (id, domain_name)
VALUES (2, 'github.com')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS users_links (
    user_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(tg_chat_id),
    FOREIGN KEY (link_id) REFERENCES Links(id),
    PRIMARY KEY (user_id, link_id)
);

CREATE TABLE IF NOT EXISTS users_links_archive (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,
    url TEXT NOT NULL,
    added_at TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE NULL
);
