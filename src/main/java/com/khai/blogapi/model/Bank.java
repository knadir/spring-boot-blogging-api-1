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
@Table(name = "banks")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banks_id_seq")
	@SequenceGenerator(name = "banks_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	@NotEmpty
	private String name;

	@OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
	private List<Employer> employers = new ArrayList<>();

	@PreRemove
	public void checkCountyAssociationBeforeRemoval() {
		if (!this.employers.isEmpty()) {
			throw new RuntimeException("Can't remove a bank that has employers !");
		}
	}

	public void addEmployer(Employer employer) {
		employers.add(employer);
		((Employer) employers).setBank(this);
	}

	public void removeEmployer(Employer employer) {
		employers.remove(employer);
		((Employer) employers).setBank(null);
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
