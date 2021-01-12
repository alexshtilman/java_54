package telran.spring.jpa.service.impl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.spring.jpa.dto.StudentDto;
import telran.spring.jpa.dto.StudentRaw;
import telran.spring.jpa.dto.StudentsMarksCount;
import telran.spring.jpa.entities.Mark;
import telran.spring.jpa.entities.Student;
import telran.spring.jpa.entities.Subject;
import telran.spring.jpa.repo.MarkRepository;
import telran.spring.jpa.repo.StudentRepository;
import telran.spring.jpa.repo.SubjectRepository;
import telran.spring.jpa.service.interfaces.Students;

@Service
public class StudentsJpaImpl implements Students {

	@Autowired
	StudentRepository studentsRepo;

	@Autowired
	SubjectRepository subjectsRepo;

	@Autowired
	MarkRepository marksRepo;

	@Override
	public void addStudent(StudentDto studentDto) {
		Student student = new Student(studentDto.stid, studentDto.name);
		studentsRepo.save(student);
	}

	@Override
	public String generateRandomData(int nStudents, int nSubjects, int nMarks, boolean uniqueMarks, int minMark,
			int maxMark) {

		if (nStudents > mokkNames.length) {
			return (String.format("can't generate more than %d students", mokkNames.length));
		}
		if (nSubjects > mokkSubjects.length) {
			return (String.format("can't generate more than %d subjects", mokkSubjects.length));
		}
		if (nStudents * nSubjects < nMarks) {
			return (String.format("can't generate %d marks for %d stundents with %d subjects", nMarks, nStudents,
					nSubjects));
		}

		String[] studentNames = generateRandomData(mokkNames, nStudents);
		String[] randomSubjects = generateRandomData(mokkSubjects, nSubjects);

		List<Student> newStudents = IntStream.iterate(0, i -> i + 1).limit(nStudents)
				.mapToObj(i -> new Student(i + 1, studentNames[i])).collect(Collectors.toList());

		List<Subject> newSubjects = IntStream.iterate(0, i -> i + 1).limit(nSubjects)
				.mapToObj(i -> new Subject(i + 1, randomSubjects[i])).collect(Collectors.toList());

		List<Mark> newMarks;
		if (uniqueMarks) {
			newMarks = Stream
					.generate(() -> new Mark(newStudents.get(getRandomInt(0, nStudents)),
							newSubjects.get(getRandomInt(0, nSubjects)), getRandomInt(minMark, maxMark)))
					.distinct().limit(nMarks).collect(Collectors.toList());
		} else {
			newMarks = Stream
					.generate(() -> new Mark(newStudents.get(getRandomInt(0, nStudents)),
							newSubjects.get(getRandomInt(0, nSubjects)), getRandomInt(minMark, maxMark)))
					.limit(nMarks).collect(Collectors.toList());
		}
		marksRepo.deleteAll();
		subjectsRepo.deleteAll();
		studentsRepo.deleteAll();

		studentsRepo.saveAll(newStudents);
		subjectsRepo.saveAll(newSubjects);
		marksRepo.saveAll(newMarks);
		return String.format("Generated %d students, %d subjects, %d marks", nStudents, nSubjects, nMarks);
	}

	public int getRandomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(max - min) + min;
	}

	public String[] generateRandomData(String[] data, int limit) {
		return Stream.generate(() -> data[getRandomInt(0, data.length)]).distinct().limit(limit).toArray(String[]::new);
	}

	private static final String[] mokkNames = { "Adin", "Adon", "Aitan", "Avidan", "Avior", "Azriel", "Benzion", "Cain",
			"Chedva", "Drorit", "Eina", "Galina", "Hannah", "Hodaya", "Hyman Anat", "Johan", "Kalanit", "Lemuel", "Lev",
			"Lewi", "Lior", "Machum", "Moshe", "Nitzana", "Noga", "Nurit", "Ofra", "Orah", "Roza", "Shimshon ", "Ya'el",
			"Yehudi", "Yered", "Yoel", "Yonat", };
	private static final String[] mokkSubjects = { "Java", "Java Core", "Spring Boot", "Spring MVC", "Python",
			"Node.js", "Go", "C", "MSSQL", "Mongo", "Postgre", "Redis", "Dart", "React", "Vue", "Angular", "CSS",
			"HTML", "JavaScript", "LESS", "XML", "Adobe Flash" };

	@Override
	public List<String> bestStudents() {
		return studentsRepo.findBestStudents();
	}

	@Override
	public List<String> bestStudents(int nStudents) {
		return studentsRepo.findTopBestStudents(nStudents);
	}

	@Override
	public List<String> studentsSubject(String subject) {

		return studentsRepo.findBestStudentsSubject(subject);
	}

	@Override
	public List<String> bestStudentsSubect(int nStudents, String subject) {
		return studentsRepo.findTopBestStudentsSubect(nStudents, subject);
	}

	@Override
	public List<StudentsMarksCount> getStudentsMarksCount() {
		return studentsRepo.findStudentsMarksCount();
	}

	@Override
	@Transactional
	public void deleteStudentByName(String name) {
		/*
		 * Student student = students.findByName(name); if (student != null) {
		 * students.delete(student); }
		 */
		studentsRepo.deleteStudentByName(name);
	}

	@Override
	public StudentRaw findStudentByName(String name) {
		return studentsRepo.findByName(name);
	}

	@Override
	public StudentRaw findByStid(int stid) {
		return studentsRepo.findByStid(stid);
	}

	@Override
	@Transactional
	public void deleteStudentByStid(int stid) {
		studentsRepo.deleteByStid(stid);
	}

}
