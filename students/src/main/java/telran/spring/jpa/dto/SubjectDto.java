package telran.spring.jpa.dto;

public class SubjectDto {

	public int suid;
	public String subject;

	public SubjectDto(int suid, String subject) {
		super();
		this.suid = suid;
		this.subject = subject;
	}

	public SubjectDto() {

	}
}
