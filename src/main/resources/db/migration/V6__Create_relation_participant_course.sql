CREATE TABLE participant_courses (
  participant_id INTEGER REFERENCES participant(id),
  courses_id INTEGER REFERENCES course(id)
);