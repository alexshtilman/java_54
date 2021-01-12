package telran.spring.jpa.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "students")
public class Student {
	@Override
	public String toString() {
		return "Student [stid=" + stid + ", name=" + name + "]";
	}

	@Id
	int stid;
	@Column(unique = true, nullable = false)
	String name;

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	List<Mark> marks;

	public Student() {

	}

	public Student(int stid, String name) {
		this.stid = stid;
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + stid;
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
		Student other = (Student) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stid != other.stid)
			return false;
		return true;
	}

}
