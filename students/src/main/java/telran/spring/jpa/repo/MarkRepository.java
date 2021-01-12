package telran.spring.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.spring.jpa.dto.IntervalMarks;
import telran.spring.jpa.dto.StudentsSubjectMarks;
import telran.spring.jpa.entities.Mark;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

	@Query(value = "select mark from marks_subjects " + "where subject=:subject " + "group by mark "
			+ "order by count(*) desc limit :n_marks", nativeQuery = true)
	List<Integer> findTopMarksEncountered(@Param("n_marks") int nMarks, @Param("subject") String subject);

	@Query(value = "select :interval as interval ," + "m.mark/:interval as intervalNum, "
			+ "count(*) as countOfOccurencies " + "from marks m group by intervalNum order by 2", nativeQuery = true)

	List<IntervalMarks> findIntervalsMarks(@Param("interval") int interval);

	@Modifying
	@Query(value = "DELETE from marks  where stid = " + "(select stid from students where name = :name) and "
			+ "suid = (select suid from subjects where subject = :subject)", nativeQuery = true)
	void deleteMarks(@Param("name") String name, @Param("subject") String subject);

	@Query(value = "SELECT  count(*) as namegroup " + "from students_marks_subjects "
			+ "where subject = :subject_name and name = :student_name " + "group by subject", nativeQuery = true)
	Long countMarksGroupByStudentNameAndSubject(@Param("student_name") String name,
			@Param("subject_name") String subject);

	@Query(value = "SELECT  stid,suid, count(*) as count, ROUND(AVG(mark)) as avg from students_marks_subjects group by stid,suid having count(*) > 1", nativeQuery = true)
	List<StudentsSubjectMarks> countAllMarksGroupBySubject();

	@Modifying
	@Query(value = "DELETE from marks where stid = :stid and suid=:suid", nativeQuery = true)
	void deleteByStidAndSuid(@Param("stid") int stid, @Param("suid") int suid);
}
