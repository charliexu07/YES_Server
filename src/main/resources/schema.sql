DROP TABLE If EXISTS courses;
CREATE TABLE courses (
	  course_id          VARCHAR(10)    PRIMARY KEY,
	  course_hour        INT            NOT NULL,
	  course_category    VARCHAR(255)   NOT NULL,
 	  description        VARCHAR(255)   NOT NULL,
	  total_enrolled     INT            NOT NULL,
	  class_capacity     INT            NOT NULL,
	  total_on_waitlist  INT            NOT NULL,
	  waitlist_capacity  INT            NOT NULL
);

DROP TABLE If EXISTS users;
CREATE TABLE users (
	  user_id            VARCHAR(255)   PRIMARY KEY,
	  name               VARCHAR(255)   NOT NULL,
	  password           VARCHAR(255)   NOT NULL,
	  user_role          VARCHAR(10)    NOT NULL      CHECK(user_role IN ('admin', 'student', 'instructor')),
	  email              VARCHAR(255)   NOT NULL
);

DROP TABLE If EXISTS schedules;
CREATE TABLE schedules (
	  user_id            VARCHAR(255)   NOT NULL,
	  course_id          VARCHAR(10)    NOT NULL,
	  course_status      VARCHAR(255)   DEFAULT NULL  CHECK(course_status IN ('enrolled', 'waitlisted', 'watching', 'instructing, cart')),
      FOREIGN KEY (course_id)
		REFERENCES courses(course_id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	 FOREIGN KEY (user_id)
		REFERENCES users(user_id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);