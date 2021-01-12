package telran.spring.jpa.dto;

public class MarkDto {
	public int stid;
	public int suid;
	public int mark;

	public MarkDto() {
	}

	public MarkDto(int stid, int suid, int mark) {
		this.stid = stid;
		this.suid = suid;
		this.mark = mark;
	}

}