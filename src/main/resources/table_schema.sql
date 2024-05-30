CREATE TABLE task_user (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    login_status VARCHAR(10) NOT NULL
);

CREATE TABLE task (
    task_id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    claim_status VARCHAR(255) NOT NULL,
    reward NUMERIC(19,6) NOT NULL,
    difficulty VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES task_user (user_id)
);