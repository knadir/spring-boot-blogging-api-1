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
@Table(name = "employers")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employer extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employers_id_seq")
	@SequenceGenerator(name = "employers_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "first_name")
	@NotEmpty
	private String firstName;

	@Column(name = "last_name")
	@NotEmpty
	private String lastName;

	@Column(name = "father_name")
	@NotEmpty
	private String fatherName;

	@Column(name = "identification_number")
	@NotEmpty
	private String identificationNumber;

	@Column(name = "place_born")
	@NotEmpty
	private String placeBorn;

	@Column(name = "place_addr")
	@NotEmpty
	private String placeAddr;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "municipality_born_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MUNICIPALITY_EMPLOYER"))
	private Municipality municipalityBorn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "municipality_addr_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MUNICIPALITY_EMPLOYER_ADDR"))
	private Municipality municipalityAddr;


	// public Employer(String name, County county) {
	// 	this.name = name;
	// 	this.county = county;
	// }
}
