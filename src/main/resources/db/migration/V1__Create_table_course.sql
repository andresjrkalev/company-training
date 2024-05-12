CREATE TABLE course (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  title VARCHAR,
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  description VARCHAR,
  maximum_number_of_participants INTEGER
);