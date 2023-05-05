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

	@Column(name = "name")
	@NotEmpty
	private String name;

	@Column(name = "first_name")
	@NotEmpty
	private String firstName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "municipality_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MUNICIPALITY_EMPLOYER"))
	private Municipality municipality;


	// public Employer(String name, County county) {
	// 	this.name = name;
	// 	this.county = county;
	// }
}
