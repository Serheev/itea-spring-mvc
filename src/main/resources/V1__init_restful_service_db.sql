CREATE TABLE company
(
    id     BIGINT NOT NULL PRIMARY KEY UNIQUE,
    name   VARCHAR(255) NOT NULL,
    foundation_date DATE NOT NULL,
    cost   BIGINT,
    created_date BIGINT NOT NULL,
    UNIQUE (name)
);

CREATE TABLE project
(
    id     BIGINT NOT NULL PRIMARY KEY UNIQUE,
    name   VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date   DATE,
    cost       BIGINT,
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES company (id),
    UNIQUE (name)
);

CREATE TABLE employee
(
    id      BIGINT NOT NULL PRIMARY KEY UNIQUE,
    name    VARCHAR(255) NOT NULL,
    age     BIGINT,
    sex     VARCHAR(16),
    salary  BIGINT,
    onleave boolean,
    additional_info JSONB,
    car_id  BIGINT,
    project_id  BIGINT,
    FOREIGN KEY (car_id) REFERENCES car (id),
    FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE car
(
    id      BIGINT NOT NULL PRIMARY KEY UNIQUE,
    model   VARCHAR(255) NOT NULL,
    power   BIGINT NOT NULL,
    created_date BIGINT NOT NULL
);

CREATE SEQUENCE hibernate_sequence
INCREMENT 1
START 1;