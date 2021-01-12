package telran.spring.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject {
	@Id
	int suid;
	@Column(unique = true, nullable = false)
	String subject;

	public Subject() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + suid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (suid != other.suid)
			return false;
		return true;
	}

	public Subject(int suid, String subject) {
		super();
		this.suid = suid;
		this.subject = subject;
	}

}
