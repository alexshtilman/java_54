package telran.spring.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import telran.spring.jpa.dto.IntervalMarks;
import telran.spring.jpa.dto.StudentRaw;
import telran.spring.jpa.dto.StudentsMarksCount;
import telran.spring.jpa.service.interfaces.Marks;
import telran.spring.jpa.service.interfaces.Students;
import telran.spring.jpa.service.interfaces.Subjects;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class StudentsApplicationTests {

	private static final String FILL_TABELS_SQL = "fillTabels.sql";
	private static final String GET = "GET ";
	private static final String POST = "POST ";
	private static final String PUT = "PUT ";
	private static final String DELETE = "DELETE ";
	private static final String METHOD = "All endpoints by noun ";

	private static final String MARKS = "/marks";
	private static final String COUNT = "/count";
	private static final String STUDENTS = "/students";
	private static final String BEST = "/best";
	private static final String DISTRIBUTION = "/distribution";
	private static final String WIDESPREAD = "/widespread";
	private static final String SUBJECTS = "/subjects";

//	name	subject	mark
//	--------------------
//	Izhak	CSS		100
//	Izhak	React	100
//	Izhak	Java	99
//	Izhak	Java	95
//	Izhak	Java	88
//	Sara	Java	85
//	Moshe	Java	80
//	Sara	CSS		80
//	Moshe	Java Technologies	80
//	Moshe	CSS		75
//	Sara	React	75
//	Moshe	Java	70
//	Moshe	Java	70
//	Sara	Java	63
//	Moshe	React	60
//	Sara	Java	48
//	Moshe	Java	45
//	Moshe	Java	45
//	Izhak	Java	39
//	Sara	Java	32

	@Autowired
	MockMvc mockMvc;

	@Autowired
	Students students;

	@Autowired
	Marks marks;

	@Autowired
	Subjects subjects;

	@DisplayName("All beans successfully loaded")
	@Test
	void contextLoads() {
		assertNotNull(students);
		assertNotNull(marks);
		assertNotNull(subjects);
	}

	class ExpectedDto {
		public String value;
		public HttpStatus status;

		public ExpectedDto(String value, HttpStatus status) {
			super();
			this.value = value;
			this.status = status;
		}

	}

	public void assertGet(String url, ExpectedDto expected) throws Exception, UnsupportedEncodingException {
		MockHttpServletResponse responce = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn().getResponse();
		assertEquals(expected.status, HttpStatus.valueOf(responce.getStatus()));
		assertEquals(expected.value, responce.getContentAsString());
	}

	public void assertDelete(String url, ExpectedDto expected) throws Exception, UnsupportedEncodingException {
		MockHttpServletResponse responce = mockMvc.perform(MockMvcRequestBuilders.delete(url)).andReturn()
				.getResponse();
		assertEquals(expected.status, HttpStatus.valueOf(responce.getStatus()));
		assertEquals(expected.value, responce.getContentAsString());
	}

	public void assertPut(String url, ExpectedDto expected) throws Exception, UnsupportedEncodingException {
		MockHttpServletResponse responce = mockMvc.perform(MockMvcRequestBuilders.put(url)).andReturn().getResponse();
		assertEquals(expected.status, HttpStatus.valueOf(responce.getStatus()));
		assertEquals(expected.value, responce.getContentAsString());
	}

	@DisplayName(METHOD + GET)
	@Nested
	class testGET {

		@Nested
		class studentsController {

			@DisplayName(GET + STUDENTS + "?name=Moshe")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testFindStudentByName() throws Exception {
				assertGet(STUDENTS + "?name=Moshe", new ExpectedDto("{\"name\":\"Moshe\",\"stid\":1}", HttpStatus.OK));
				StudentRaw studentByName = students.findStudentByName("Moshe");
				assertEquals("Moshe", studentByName.getName());
				assertEquals(1, studentByName.getStid());
			}

			@DisplayName(GET + STUDENTS + "/1")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testFindStudentByStid() throws Exception {
				assertGet(STUDENTS + "/1", new ExpectedDto("{\"name\":\"Moshe\",\"stid\":1}", HttpStatus.OK));
				StudentRaw studentByName = students.findStudentByName("Moshe");
				assertEquals("Moshe", studentByName.getName());
				assertEquals(1, studentByName.getStid());
			}

			@DisplayName(GET + STUDENTS + BEST)
			@Test
			@Sql(FILL_TABELS_SQL)
			void testBestStudents() throws Exception {
				assertGet(STUDENTS + BEST, new ExpectedDto("[\"Izhak\"]", HttpStatus.OK));
				List<String> bestStudents = students.bestStudents();
				assertEquals(1, bestStudents.size());
				assertEquals("Izhak", bestStudents.get(0));
			}

			@DisplayName(GET + STUDENTS + BEST + "?limit=2")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testBestStudentsWithLimit() throws Exception {
				assertGet(STUDENTS + BEST + "?n_students=2", new ExpectedDto("[\"Izhak\",\"Moshe\"]", HttpStatus.OK));
				List<String> bestStudents = students.bestStudents(2);
				assertEquals(2, bestStudents.size());
				assertEquals("Izhak", bestStudents.get(0));
				assertEquals("Moshe", bestStudents.get(1));
			}

			@DisplayName(GET + STUDENTS + BEST + "?subject=Java")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testStudentsSubject() throws Exception {
				assertGet(STUDENTS + BEST + "?subject=Java", new ExpectedDto("[\"Izhak\"]", HttpStatus.OK));
				List<String> bestStudents = students.studentsSubject("Java");
				assertEquals(1, bestStudents.size());
				assertEquals("Izhak", bestStudents.get(0));
			}

			@DisplayName(GET + STUDENTS + BEST + "?subject=Java&n_students=5")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testBestStudentsSubect() throws Exception {
				assertGet(STUDENTS + BEST + "?subject=Java&n_students=5",
						new ExpectedDto("[\"Izhak\",\"Moshe\",\"Sara\"]", HttpStatus.OK));
				List<String> bestStudents = students.bestStudentsSubect(5, "Java");
				assertEquals(3, bestStudents.size());
				assertEquals("Izhak", bestStudents.get(0));
				assertEquals("Moshe", bestStudents.get(1));
				assertEquals("Sara", bestStudents.get(2));
			}

			@DisplayName(GET + STUDENTS + MARKS + COUNT)
			@Test
			@Sql(FILL_TABELS_SQL)
			void testStudentsMarksCount() throws Exception {
				assertGet(STUDENTS + MARKS + COUNT, new ExpectedDto(
						"[{\"name\":\"Moshe\",\"marksCount\":8}," + "{\"name\":\"Izhak\",\"marksCount\":6},"
								+ "{\"name\":\"Sara\",\"marksCount\":6}," + "{\"name\":\"Lilit\",\"marksCount\":2}]",
						HttpStatus.OK));
				List<StudentsMarksCount> bestStudents = students.getStudentsMarksCount();
				assertEquals(4, bestStudents.size());
				assertEquals("Moshe", bestStudents.get(0).getName());
				assertEquals(8L, bestStudents.get(0).getMarksCount());

				assertEquals("Izhak", bestStudents.get(1).getName());
				assertEquals(6L, bestStudents.get(1).getMarksCount());

				assertEquals("Sara", bestStudents.get(2).getName());
				assertEquals(6L, bestStudents.get(2).getMarksCount());

				assertEquals("Lilit", bestStudents.get(3).getName());
				assertEquals(2L, bestStudents.get(3).getMarksCount());

			}

		}

		@Nested
		class subjectsController {

			@DisplayName(GET + SUBJECTS + MARKS + COUNT)
			@Test
			@Sql(FILL_TABELS_SQL)
			void testGetSubjectsHighestMarks() throws Exception {
				assertGet(SUBJECTS + MARKS + COUNT, new ExpectedDto("[\"Java\"]", HttpStatus.OK));
				List<String> topSubject = subjects.getSubjectsHighestMarks();
				assertEquals(1, topSubject.size());
				assertEquals("Java", topSubject.get(0));
			}

			@DisplayName(GET + SUBJECTS + MARKS + COUNT + "?n_subjects=2")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testGetSubjectsHighestMarksLimit() throws Exception {
				assertGet(SUBJECTS + MARKS + COUNT + "?n_subjects=2",
						new ExpectedDto("[\"Java\",\"CSS\"]", HttpStatus.OK));
				List<String> topSubject = subjects.getTopSubjectsHighestMarks(2);
				assertEquals(2, topSubject.size());
				assertEquals("Java", topSubject.get(0));
				assertEquals("CSS", topSubject.get(1));
			}
		}

		@Disabled
		@Nested
		class marksController {

			@DisplayName(GET + MARKS + WIDESPREAD + "/Java?n_marks=3")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testGetMarksWideSpred() throws Exception {
				assertGet(MARKS + WIDESPREAD + "/Java?n_marks=3", new ExpectedDto("[45,70,88]", HttpStatus.OK));
				List<Integer> topMarks = marks.getTopMarksEncountered(3, "Java");
				assertEquals(3, topMarks.size());
				assertEquals(45, topMarks.get(0));
				assertEquals(70, topMarks.get(1));
				assertEquals(88, topMarks.get(2));
			}

			@DisplayName(GET + MARKS + DISTRIBUTION + "/?interval=10")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testGetDistribution() throws Exception {
				assertGet(MARKS + DISTRIBUTION + "/?interval=10",
						new ExpectedDto("[{\"countOfOccurencies\":2,\"max\":39,\"min\":30},"
								+ "{\"countOfOccurencies\":5,\"max\":49,\"min\":40},"
								+ "{\"countOfOccurencies\":2,\"max\":69,\"min\":60},"
								+ "{\"countOfOccurencies\":4,\"max\":79,\"min\":70},"
								+ "{\"countOfOccurencies\":5,\"max\":89,\"min\":80},"
								+ "{\"countOfOccurencies\":2,\"max\":99,\"min\":90},"
								+ "{\"countOfOccurencies\":2,\"max\":109,\"min\":100}]", HttpStatus.OK));
				List<IntervalMarks> distribution = marks.getIntervalsMarks(10);
				assertEquals(3, distribution.size());

				int[][] expected = { { 2, 30, 39 }, { 5, 40, 49 }, { 2, 60, 69 }, { 4, 70, 79 }, { 5, 80, 89 },
						{ 2, 90, 99 }, { 2, 100, 109 } };
				for (int i = 0; i < expected.length; i++) {
					assertEquals(expected[i][0], distribution.get(i).getCountOfOccurencies());
					assertEquals(expected[i][1], distribution.get(i).getMin());
					assertEquals(expected[i][2], distribution.get(i).getMax());
				}

			}
		}

	}

	@DisplayName(METHOD + DELETE)
	@Nested
	class testDELETE {

		@Nested
		class studentsController {

			@DisplayName(DELETE + STUDENTS + "/1")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testDeleteByStid() throws Exception {
				assertDelete(STUDENTS + "/1", new ExpectedDto("", HttpStatus.OK));
				students.deleteStudentByStid(1);
				assertGet(STUDENTS + "/1", new ExpectedDto("", HttpStatus.OK));
			}

			@DisplayName(DELETE + STUDENTS + "?name=Moshe")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testDeleteByName() throws Exception {
				assertDelete(STUDENTS + "?name=Moshe", new ExpectedDto("", HttpStatus.OK));
				students.deleteStudentByName("Moshe");
				assertGet(STUDENTS + "?name=Moshe", new ExpectedDto("", HttpStatus.OK));
			}
		}

		@Nested
		class subjectsController {

		}

		@Nested
		class marksController {
			@DisplayName(DELETE + MARKS + "/Java?name=Moshe")
			@Test
			@Sql(FILL_TABELS_SQL)
			void testDeleteByName() throws Exception {
				assertDelete(MARKS + "/Java?name=Moshe", new ExpectedDto("", HttpStatus.OK));
				marks.deleteMarks("Moshe", "Java");
				assertGet(MARKS + COUNT + "?subject_name=Java&student_name=Moshe", new ExpectedDto("", HttpStatus.OK));
			}
		}

	}

	@DisplayName(METHOD + PUT)
	@Nested
	class testPUT {

		@Nested
		class subjectsController {

			@Disabled
			@DisplayName(PUT + SUBJECTS)
			@Test
			@Sql(FILL_TABELS_SQL)
			void testPutSubjects() throws Exception {
				assertPut(SUBJECTS, new ExpectedDto("", HttpStatus.OK));

				assertGet(STUDENTS + MARKS + COUNT, new ExpectedDto(
						"[{\"name\":\"Moshe\",\"marksCount\":4}," + "{\"name\":\"Sara\",\"marksCount\":3},"
								+ "{\"name\":\"Izhak\",\"marksCount\":3}," + "{\"name\":\"Lilit\",\"marksCount\":2}]",
						HttpStatus.OK));
				subjects.setAveragingSubjectMarks();

			}
		}
	}
}
