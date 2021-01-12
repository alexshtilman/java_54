package telran.spring.jpa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.spring.jpa.service.interfaces.Subjects;

@RestController
@RequestMapping("/subjects")
public class SubjectsController {
	private static final String MARKS = "/marks";
	private static final String COUNT = "/count";

	@Autowired
	Subjects subjects;

	@GetMapping(MARKS + COUNT)
	public List<String> subjectsMarksHighest(@RequestParam(name = "n_subjects", defaultValue = "0") int n_Subjects) {
		if (n_Subjects == 0) {
			return subjects.getSubjectsHighestMarks();
		}
		return subjects.getTopSubjectsHighestMarks(n_Subjects);
	}

	@PutMapping("")
	public void averagingSubjectMarks() {
		subjects.setAveragingSubjectMarks();
	}
}
