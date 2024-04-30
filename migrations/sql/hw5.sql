ALTER TABLE links
ADD IF NOT EXISTS last_activity TIMESTAMP WITH TIME ZONE NOT NULL;

ALTER TABLE links
ADD IF NOT EXISTS site_id INT NOT NULL;

CREATE TABLE IF NOT EXISTS links_sites (
    id INT PRIMARY KEY,
    domain_name VARCHAR(255) NOT NULL
);

ALTER TABLE links
ADD CONSTRAINT FK_Links_Sites FOREIGN KEY (site_id) REFERENCES links_sites(id);

INSERT INTO links_sites (id, domain_name)
VALUES (0, 'unsupported');
INSERT INTO links_sites (id, domain_name)
VALUES (1, 'stackoverflow.com');
INSERT INTO links_sites (id, domain_name)
VALUES (2, 'github.com');

ALTER TABLE users
DROP COLUMN username;
