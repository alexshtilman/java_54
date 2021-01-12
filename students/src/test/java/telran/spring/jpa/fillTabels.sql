DELETE FROM marks;
DELETE FROM students;
DELETE FROM subjects;

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

insert into marks (id, stid,suid, mark) VALUES
(1, 1,	1,	80),
(2, 1,	2,	75),
(3, 1,	3,	60),
(4, 2,	1,	85),
(5, 2,	2,	80),
(6, 2,	3,	75),
(7, 3,	1,	95),
(8, 3,	2,	100),
(9, 3,	3,	100),
(10, 1, 4,	80),

(11, 1,	1,	70),
(12, 1,	1,	45),
(13, 1,	1,	70),
(14, 1,	1,	45),

(15, 2,	1,	63),
(16, 2,	1,	48),
(17, 2,	1,	32),

(18, 3,	1,	39),
(19, 3,	1,	88),
(20, 3,	1,	99),

(21, 4,	2,	40),
(22, 4,	3,	41);
