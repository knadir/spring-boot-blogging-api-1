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
@Table(name = "cost_places")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostPlace extends UserDateAudit {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "cost_places_id_seq"
  )
  @SequenceGenerator(name = "cost_places_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "name")
  @NotEmpty
  private String name;

  @OneToMany(mappedBy = "costPlace", fetch = FetchType.EAGER)
  private List<Employer> employers = new ArrayList<>();

  @PreRemove
  public void checkCostPlaceAssociationBeforeRemoval() {
    if (!this.employers.isEmpty()) {
      throw new RuntimeException(
        "Can't remove a cost place that has employers !"
      );
    }
  }

  public void addEmployer(Employer employer) {
    employers.add(employer);
    ((Employer) employers).setCostPlace(this);
  }

  public void removeEmployer(Employer employer) {
    employers.remove(employer);
    ((Employer) employers).setCostPlace(null);
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
