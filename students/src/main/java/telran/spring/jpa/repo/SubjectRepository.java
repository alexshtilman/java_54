package telran.spring.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.spring.jpa.entities.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	@Query(value = "SELECT subject FROM marks_subjects GROUP BY subject HAVING sum(mark) >" + "(SELECT AVG(total) FROM "
			+ "(SELECT SUM(mark) as total from marks_subjects group by subject) as totals) "
			+ "order by sum(mark) desc", nativeQuery = true)
	List<String> findSubjectsHighestMarks();

	@Query(value = "SELECT subject FROM students_marks_subjects "
			+ "group by subject order by SUM(mark) desc,subject desc limit :n_subjects", nativeQuery = true)
	List<String> findTopSubjectsHighestMarks(@Param("n_subjects") int nSubjects);

	@Query(value = "SELECT subject FROM students_marks_subjects "
			+ "group by subject order by SUM(mark) desc,subject desc limit :n_subjects", nativeQuery = true)
	void setAveragingSubjectMarks();
}
