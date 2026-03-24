-- tags table
CREATE TABLE IF NOT EXISTS tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL CONSTRAINT uq_tags_name UNIQUE,
    slug VARCHAR(120) NOT NULL CONSTRAINT uq_tags_slug UNIQUE,
    description VARCHAR(300),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);