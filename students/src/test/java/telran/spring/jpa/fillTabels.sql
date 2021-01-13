DELETE FROM students;
DELETE FROM subjects;
DELETE FROM marks;

insert into students (stid,name) VALUES
(1,	'Moshe'),
(2,	'Sara'),
(3,	'Izhak'),
(4,	'Lilit');

insert into subjects (suid,subject) VALUES
(1	,'Java'),
(2	,'CSS'),
(3	,'React'),
(4	,'Java Technologies');

insert into marks ( stid,suid, mark) VALUES
( 1,	1,	80),
( 1,	2,	75),
( 1,	3,	60),
( 2,	1,	85),
( 2,	2,	80),
( 2,	3,	75),
( 3,	1,	95),
( 3,	2,	100),
( 3,	3,	100),
( 1, 	4,	80),

( 1,	1,	70),
( 1,	1,	45),
( 1,	1,	70),
( 1,	1,	45),

( 2,	1,	63),
( 2,	1,	48),
( 2,	1,	32),

( 3,	1,	39),
( 3,	1,	88),
( 3,	1,	99),

( 4,	2,	40),
( 4,	3,	41);

