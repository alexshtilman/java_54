package telran.spring.jpa.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.spring.jpa.service.interfaces.Students;

@RestController
public class StudentsRandomController {
	private static final String INIT = "/init";
	private static final String INIT_N_STUDENTS = "35";
	private static final String INIT_N_SUBJECTS = "20";

	private static final String MIN_MARK = "60";
	private static final String MAX_MARK = "100";

	private static final String INIT_N_MARKS = "200";
	private static final String UNIQUE_MARKS = "true";

	private static final Logger logger = LoggerFactory.getLogger(StudentsRandomController.class);

	@Autowired
	Students students;

	@GetMapping(INIT)
	public String initDB(@RequestParam(name = "n_students", defaultValue = INIT_N_STUDENTS) int nStudents,
			@RequestParam(name = "n_subjects", defaultValue = INIT_N_SUBJECTS) int nSubjects,
			@RequestParam(name = "n_marks", defaultValue = INIT_N_MARKS) int nMarks,
			@RequestParam(name = "uique_marks", defaultValue = UNIQUE_MARKS) boolean uiqueMarks,
			@RequestParam(name = "min_mark", defaultValue = MIN_MARK) int minMark,
			@RequestParam(name = "max_mark", defaultValue = MAX_MARK) int maxMark) {
		logger.info("Replasing all students, subjects, marks...");
		String result = students.generateRandomData(nStudents, nSubjects, nMarks, uiqueMarks, minMark, maxMark);
		logger.info(result);
		return result;
	}
}
