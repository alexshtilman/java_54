package telran.spring.jpa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.spring.jpa.dto.IntervalMarks;
import telran.spring.jpa.dto.StudentsSubjectMarks;
import telran.spring.jpa.service.interfaces.Marks;

@RestController
@RequestMapping("/marks")
public class MarksController {
	private static final String DISTRIBUTION = "/distribution";
	private static final String SUBJECT = "/{subject}";
	private static final String WIDESPREAD = "/widespread";
	private static final String COUNT = "/count";
	private static final String ALL = "/all";
	@Autowired
	Marks marks;

	@GetMapping(WIDESPREAD + SUBJECT)
	public List<Integer> getMarksWideSpred(@RequestParam(name = "n_marks") int nMarks,
			@PathVariable("subject") String subject) {
		return marks.getTopMarksEncountered(nMarks, subject);
	}

	@GetMapping(DISTRIBUTION)
	public List<IntervalMarks> getDistribution(@RequestParam(name = "interval", defaultValue = "10") int interval) {
		return marks.getIntervalsMarks(interval);
	}

	@GetMapping(COUNT)
	public Long getCountMarksByStudentNameAndSubjectName(
			@RequestParam(name = "student_name", required = true) String name,
			@RequestParam(name = "subject_name", required = true) String subject) {
		return marks.getCountMarksByStudentNameAndSubjectName(name, subject);
	}

	@GetMapping(COUNT + ALL)
	public List<StudentsSubjectMarks> getAllCountsMarks() {
		return marks.getAllCountsMarks();
	}

	@DeleteMapping(SUBJECT)
	public void deleteMarks(@PathVariable("subject") String subject, @RequestParam(name = "name") String name) {
		marks.deleteMarks(name, subject);
	}
}
