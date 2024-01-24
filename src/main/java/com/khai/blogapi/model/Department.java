package com.khai.blogapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departments_id_seq")
	@SequenceGenerator(name = "departments_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	@NotEmpty
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ACTIVITY_DEPARTMENT"))
	private Activity activity;

	@OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
	private List<Employer> employers = new ArrayList<>();

	@PreRemove
    public void checkEmployerAssociationBeforeRemoval() {
        if (!this.employers.isEmpty()) {
            throw new RuntimeException("Can't remove a department that has employers !");
        }
    }

	public void addEmployer(Employer employer) {
		employers.add(employer);
		((Employer) employers).setDepartment(this);
	}

	public Department(String name, List<Employer> employers) {
		this.name = name;
		this.employers = employers;
	}

	public void removeEmployer(Employer employer) {
		employers.remove(employer);
		((Employer) employers).setDepartment(null);
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
