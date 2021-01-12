package telran.spring.jpa.service.interfaces;

import java.util.List;

import telran.spring.jpa.dto.StudentDto;
import telran.spring.jpa.dto.StudentRaw;
import telran.spring.jpa.dto.StudentsMarksCount;

public interface Students {

	void addStudent(StudentDto studentDto);

	void deleteStudentByName(String name);

	void deleteStudentByStid(int stid);

	String generateRandomData(int nStudents, int nSubjects, int nMarks, boolean uniqueMarks, int minMark, int maxMark);

	List<String> bestStudents();

	List<String> bestStudents(int nStudents);

	List<String> studentsSubject(String subject);

	List<String> bestStudentsSubect(int nStudents, String subject);

	List<StudentsMarksCount> getStudentsMarksCount();

	StudentRaw findStudentByName(String name);

	StudentRaw findByStid(int stid);
}
