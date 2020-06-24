-- migrate:up

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255)                   NOT NULL,
    phone      VARCHAR(255)                   NOT NULL,
    created_at TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_phone UNIQUE (phone)
);

INSERT INTO users(name, phone)
VALUES ('John', '1234567890');

-- migrate:down

DROP TABLE users;