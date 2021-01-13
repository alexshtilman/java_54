package telran.spring.jpa.service.interfaces;

import java.util.List;

import telran.spring.jpa.dto.IntervalMarksDto;
import telran.spring.jpa.dto.MarkDto;
import telran.spring.jpa.dto.StudentsSubjectMarks;

public interface Marks {
	List<Integer> getTopMarksEncountered(int nMarks, String subject);

	Long getCountMarksByStudentNameAndSubjectName(String name, String subject);

	List<StudentsSubjectMarks> getAllCountsMarks();

	List<IntervalMarksDto> getIntervalsMarks(int interval);

	void addMark(MarkDto markDto);

	void deleteMarks(String name, String subject);
}
