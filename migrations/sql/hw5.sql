ALTER TABLE links
ADD IF NOT EXISTS last_activity TIMESTAMP WITH TIME ZONE NOT NULL;

ALTER TABLE links
ADD IF NOT EXISTS site_id INT NOT NULL;

CREATE TABLE IF NOT EXISTS links_sites (
    id INT PRIMARY KEY,
    site_name VARCHAR(255) NOT NULL
);

ALTER TABLE links
ADD CONSTRAINT FK_Links_Sites FOREIGN KEY (site_id) REFERENCES links_sites(id);

INSERT INTO links_sites (id, site_name)
VALUES (1, 'StackOverflowQuestion');
INSERT INTO links_sites (id, site_name)
VALUES (2, 'GitHubRepository');

ALTER TABLE users
DROP COLUMN username;
