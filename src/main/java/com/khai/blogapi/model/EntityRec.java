package com.khai.blogapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entities")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityRec extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entities_id_seq")
	@SequenceGenerator(name = "entities_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
    @NotEmpty
    private String name;

	@OneToMany(mappedBy = "entity", fetch = FetchType.EAGER)
    private List<County> counties = new ArrayList<>();

	@PreRemove
    public void checkCountyAssociationBeforeRemoval() {
        if (!this.counties.isEmpty()) {
            throw new RuntimeException("Can't remove a entity that has counties !");
        }
    }
	
	public void addCounty(County county) {
        counties.add(county);
        ((County) counties).setEntity(this);
    }

    public void removeCounty(County county) {
        counties.remove(county);
        ((County) counties).setEntity(null);
    }

	@JsonIgnore
	public List<County> getCounties() {
		return counties == null ? null : new ArrayList<>(this.counties);
	}

	public void setCounties(List<County> counties) {
		if(counties == null) {
			this.counties = null;
		}else {
			this.counties= counties;
		}
	}

}
