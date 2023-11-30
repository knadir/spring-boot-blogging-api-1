package com.khai.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "genders")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gender extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genders_id_seq")
	@SequenceGenerator(name = "genders_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	@NotEmpty
	private String name;

	@OneToMany(mappedBy = "gender", fetch = FetchType.EAGER)
	private List<Employer> employers = new ArrayList<>();

	@PreRemove
	public void checkCountyAssociationBeforeRemoval() {
		if (!this.employers.isEmpty()) {
			throw new RuntimeException("Can't remove a gender that has employers !");
		}
	}

	public void addEmployer(Employer employer) {
		employers.add(employer);
		((Employer) employers).setGender(this);
	}

	public void removeEmployer(Employer employer) {
		employers.remove(employer);
		((Employer) employers).setGender(null);
	}

	@JsonIgnore
	public List<Employer> getEmployers() {
		return employers == null ? null : new ArrayList<>(this.employers);
	}

	public void setEmployers(List<Employer> employers) {
		if (employers == null) {
			this.employers = null;
		} else {
			this.employers = employers;
		}
	}
}
