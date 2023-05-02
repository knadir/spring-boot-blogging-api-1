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
@Table(name = "counties")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class County extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "counties_id_seq")
	@SequenceGenerator(name = "counties_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	@NotEmpty
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "entity_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ENTITY_COUNTY"))
	private EntityRec entity;

	@OneToMany(mappedBy = "county", fetch = FetchType.EAGER)
	private List<Municipality> municipalities = new ArrayList<>();

	@PreRemove
    public void checkMunicipalityAssociationBeforeRemoval() {
        if (!this.municipalities.isEmpty()) {
            throw new RuntimeException("Can't remove a county that has municipalities !");
        }
    }

	public void addMunicipality(Municipality municipality) {
		municipalities.add(municipality);
		((Municipality) municipalities).setCounty(this);
	}

	public County(String name, List<Municipality> municipalities) {
		this.name = name;
		this.municipalities = municipalities;
	}

	public void removeMunicipality(Municipality municipality) {
		municipalities.remove(municipality);
		((Municipality) municipalities).setCounty(null);
	}

	@JsonIgnore
	public List<Municipality> getMunicipalities() {
		return municipalities == null ? null : new ArrayList<>(this.municipalities);
	}

	public void setMunicipalities(List<Municipality> municipalities) {
		if (municipalities == null) {
			this.municipalities = null;
		} else {
			this.municipalities = municipalities;
		}
	}

}
