CREATE OR REPLACE VIEW students_marks_subjects as
SELECT  s.stid,
        sb.suid,
        s.name,
        sb.subject,
        m.mark
FROM    students s
    JOIN marks m ON m.stid = s.stid
    JOIN subjects sb ON m.suid = sb.suid;
    
CREATE OR REPLACE VIEW marks_subjects as
SELECT sb.suid,
       sb.subject,
       m.mark
FROM   marks m
    JOIN subjects sb ON m.suid = sb.suid;
