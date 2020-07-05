INSERT INTO courses VALUES
	('MATH2300', 3, 'Math', 'Multivariable Calculus', 30, 30, 15, 20),
	('MATH2410', 3, 'Math', 'Linear Algebra', 29, 30, 0, 15),
    ('MATH2810', 3, 'Math', 'Statistics', 12, 40, 0, 15),
    ('CS1101', 3, 'Computer_Science', 'Programming and Problem Solving', 45, 45, 15, 15),
    ('CS2201', 3, 'Computer_Science', 'Program Design and Data Structures', 45, 45, 12, 25),
    ('LAT1101', 3, 'Latin', 'Beginning Latin I', 14, 15, 0, 5);

INSERT INTO users VALUES
    ('Nicholas_Zeppos_01', 'Nicholas_Zeppos', 'nicholas1', 'admin', 'nicholas.zeppos@vanderbilt.edu'),
	('Charlie_Xu_01', 'Charlie_Xu', 'charlie1', 'student', 'charlie.xu@vanderbilt.edu'),
    ('Charlie_Xu_02', 'Charlie_Xu', 'charlie2', 'student', 'charlie.xu.1@vanderbilt.edu'),
    ('Frank_Tian_01', 'Frank_Tian', 'frank1', 'student', 'frank.tian@vanderbilt.edu'),
    ('Acar_Ary_01', 'Acar_Ary', 'acar1', 'student', 'acar.art@vanderbilt.edu'),
    ('John_Rafter_01', 'John_Rafter', 'john1', 'instructor', 'john.rafter@vanderbilt.edu'),
    ('Lori_Rafter_03', 'Lori_Rafter', 'lori3', 'instructor', 'lori.rafter.2@vanderbilt.edu'),
    ('Dan_Arena_01', 'Dan_Arena', 'dan1', 'instructor', 'dan.arena@vanderbilt.edu'),
    ('Daniel_Solomon_01', 'Daniel_Solomon', 'daniel1', 'instructor', 'daniel.solomon@vanderbilt.edu');

INSERT INTO schedules VALUES
	('Charlie_Xu_01', 'MATH2300', 'waitlisted'),
    ('Charlie_Xu_01', 'MATH2810', 'enrolled'),
    ('Charlie_Xu_01', 'LAT1101', 'cart'),
    ('Charlie_Xu_01', 'CS1101', 'watching'),
    ('Charlie_Xu_02', 'MATH2810', 'enrolled'),
    ('Frank_Tian_01', 'CS2201', 'enrolled'),
    ('Frank_Tian_01', 'MATH2300', 'waitlisted'),
    ('John_Rafter_01', 'MATH2300', 'instructing'),
    ('John_Rafter_01', 'MATH2410', 'instructing'),
    ('Dan_Arena_01', 'CS1101', 'instructing'),
    ('Dan_Arena_01', 'CS2201', 'instructing'),
    ('Daniel_Solomon_01', 'LAT1101', 'instructing');