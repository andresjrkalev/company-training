CREATE TABLE course_participants (
  course_id INTEGER REFERENCES course (id),
  participants_id INTEGER REFERENCES participant (id)
);