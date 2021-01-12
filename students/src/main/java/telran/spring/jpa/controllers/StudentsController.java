package telran.spring.jpa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.spring.jpa.dto.StudentRaw;
import telran.spring.jpa.dto.StudentsMarksCount;
import telran.spring.jpa.service.interfaces.Students;

@RestController
@RequestMapping("/students")
public class StudentsController {

	private static final String MARKS = "/marks";
	private static final String COUNT = "/count";
	private static final String BEST = "/best";

	@Autowired
	Students students;

	@GetMapping(BEST)
	public List<String> bestStudentNames(@RequestParam(name = "n_students", defaultValue = "0") int nStudents,
			@RequestParam(name = "subject", required = false) String subject) {

		if (nStudents == 0 && subject == null) {
			return students.bestStudents();
		}
		if (nStudents != 0 && subject == null) {
			return students.bestStudents(nStudents);
		}
		if (nStudents == 0 && subject != null) {
			return students.studentsSubject(subject);
		}
		return students.bestStudentsSubect(nStudents, subject);

	}

	@GetMapping(MARKS + COUNT)
	public List<StudentsMarksCount> studentsMarksCount() {
		return students.getStudentsMarksCount();
	}

	@GetMapping("")
	public StudentRaw findStudentByName(@RequestParam(name = "name", required = true) String name) {
		return students.findStudentByName(name);
	}

	@GetMapping("/{stid}")
	public StudentRaw findStudentByStid(@PathVariable int stid) {
		return students.findByStid(stid);
	}

	@DeleteMapping("")
	public void deleteStudentByName(@RequestParam(name = "name", required = true) String name) {
		students.deleteStudentByName(name);
	}

	@DeleteMapping("/{stid}")
	public void deleteStudentByStid(@PathVariable int stid) {
		students.deleteStudentByStid(stid);
	}
}
