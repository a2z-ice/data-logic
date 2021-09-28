CREATE TABLE project
(
    id            BIGINT                      NOT NULL,
    title         VARCHAR(255) NULL,
    `description` LONGTEXT NULL,
    type          VARCHAR(255) NULL,
    status        VARCHAR(20) DEFAULT 'DRAFT' NOT NULL,
    CONSTRAINT pk_project PRIMARY KEY (id)
);

CREATE TABLE section
(
    id            BIGINT NOT NULL,
    `description` LONGTEXT NULL,
    title         VARCHAR(255) NULL,
    project_id    BIGINT NULL,
    CONSTRAINT pk_section PRIMARY KEY (id)
);

ALTER TABLE section
    ADD CONSTRAINT FK_SECTION_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id);