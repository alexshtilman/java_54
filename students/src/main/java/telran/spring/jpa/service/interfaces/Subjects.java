package telran.spring.jpa.service.interfaces;

import java.util.List;

import telran.spring.jpa.dto.SubjectDto;

public interface Subjects {

	List<String> getSubjectsHighestMarks();

	List<String> getTopSubjectsHighestMarks(int nSubjects);

	void addSubject(SubjectDto subjectDto);

	void setAveragingSubjectMarks();
}
