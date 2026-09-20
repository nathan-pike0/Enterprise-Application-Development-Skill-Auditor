CREATE TABLE role_allocation (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(35) NOT NULL UNIQUE
);

CREATE TABLE skill (
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(35) NOT NULL,
    description VARCHAR(300) NOT NULL,
    category VARCHAR(35) NOT NULL,
    status VARCHAR(35) NOT NULL
);

CREATE TABLE skill_portfolio (
    id VARCHAR(36) PRIMARY KEY,
    staff_member_id VARCHAR(36) NOT NULL,
    skill_id VARCHAR(36) NOT NULL,
    skill_level VARCHAR(35) NOT NULL,
    expiry_date VARCHAR(35),
    status VARCHAR(35) NOT NULL,
    verified_by_id VARCHAR(36),
    rejected_by_id VARCHAR(36),
    submitted_at VARCHAR(35) NOT NULL,
    updated_at VARCHAR(35) NOT NULL
);

CREATE TABLE portfolio_note (
    id   VARCHAR(36) PRIMARY KEY,
    portfolio_id VARCHAR(36) NOT NULL,
    note VARCHAR(300) NOT NULL,
    added_by_id VARCHAR(36) NOT NULL,
    added_at VARCHAR(36) NOT NULL
);


CREATE TABLE staff_member (
    id VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(36) NOT NULL,
    last_name VARCHAR(36) NOT NULL,
    email VARCHAR(35) NOT NULL,
    hire_date VARCHAR(35),
    department_id VARCHAR(36) NOT NULL,
    line_manager_id VARCHAR(36),
    job_role VARCHAR(36) NOT NULL,
    role_start_date VARCHAR(35),
    job_level VARCHAR(35),
    employment_type VARCHAR(35) NOT NULL,
    employment_status VARCHAR(35) NOT NULL
);

CREATE TABLE event_store(
    id INT AUTO_INCREMENT PRIMARY KEY,
    occurred_on DATE NOT NULL,
    event_body VARCHAR(66666) NOT NULL,
    event_type VARCHAR(150) NOT NULL,
    status VARCHAR(20) NOT NULL,
    retry_count INT DEFAULT 0 NOT NULL
);