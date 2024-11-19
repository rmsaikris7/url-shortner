CREATE TABLE IF NOT EXISTS short_url (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    key VARCHAR(7) UNIQUE NOT NULL,
    long_url TEXT NOT NULL,
    version INT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL
);