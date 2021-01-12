package telran.spring.jpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.spring.jpa.dto.StudentsSubjectMarks;
import telran.spring.jpa.dto.SubjectDto;
import telran.spring.jpa.entities.Mark;
import telran.spring.jpa.entities.Student;
import telran.spring.jpa.entities.Subject;
import telran.spring.jpa.repo.MarkRepository;
import telran.spring.jpa.repo.StudentRepository;
import telran.spring.jpa.repo.SubjectRepository;
import telran.spring.jpa.service.interfaces.Subjects;

@Service
public class SubjectsJpaImpl implements Subjects {

	@Autowired
	SubjectRepository subjectsRepo;

	@Autowired
	MarkRepository marksRepo;

	@Autowired
	StudentRepository studentsRepo;

	@Override
	public List<String> getSubjectsHighestMarks() {
		return subjectsRepo.findSubjectsHighestMarks();
	}

	@Override
	public void addSubject(SubjectDto subjectDto) {
		Subject subject = new Subject(subjectDto.suid, subjectDto.subject);
		subjectsRepo.save(subject);
	}

	@Override
	public List<String> getTopSubjectsHighestMarks(int nSubjects) {
		return subjectsRepo.findTopSubjectsHighestMarks(nSubjects);
	}

	@Override
	@Transactional
	public void setAveragingSubjectMarks() {
		List<StudentsSubjectMarks> marks = marksRepo.countAllMarksGroupBySubject();
		for (StudentsSubjectMarks m : marks) {
			marksRepo.deleteByStidAndSuid(m.getStid(), m.getSuid());
		}
		for (StudentsSubjectMarks m : marks) {
			Student student = studentsRepo.findById(m.getStid()).orElse(null);
			Subject subject = subjectsRepo.findById(m.getSuid()).orElse(null);
			Mark mark = new Mark(student, subject, m.getAvg());
			marksRepo.save(mark);
		}
	}

}
