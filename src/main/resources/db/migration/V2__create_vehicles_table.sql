CREATE TABLE vehicles(
    id              BIGSERIAL PRIMARY KEY,
    plate           VARCHAR(15) NOT NULL UNIQUE,
    brand           VARCHAR(50) NOT NULL,
    model           VARCHAR(50) NOT NULL, 
    year            INT NOT NULL,
    mileage         INT NOT NULL DEFAULT 0 CHECK (mileage > 0),
    status          VARCHAR(20) NOT NULL,
    owner_id        BIGINT  NOT NULL REFERENCES users (id),
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now()               
);