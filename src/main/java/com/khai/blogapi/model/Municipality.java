package com.khai.blogapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "municipalities")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Municipality extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "municipalities_id_seq")
	@SequenceGenerator(name = "municipalities_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	@NotEmpty
	private String name;

	@OneToMany(mappedBy = "municipalityBorn", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Employer> employerBorns = new ArrayList<>();

	@OneToMany(mappedBy = "municipalityAddr", fetch = FetchType.EAGER)
	private List<Employer> employerAddrs= new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "county_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_COUNTY_MUNICIPALITY"))
	private County county;

	@PreRemove
	public void checkEmployerBornsAssociationBeforeRemoval() {
		if (!this.employerBorns.isEmpty() || !this.employerAddrs.isEmpty()) {
			throw new RuntimeException("Can't remove a municipality that has employers municipalities !");
		}
	}

	// public Municipality(String name, County county) {
	// this.name = name;
	// this.county = county;
	// }
}
