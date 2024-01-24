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
@Table(name = "activities")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends UserDateAudit {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "activities_id_seq"
  )
  @SequenceGenerator(name = "activities_id_seq", allocationSize = 1)
  private Long id;
  
  @Column(name = "id_old")
  @NotEmpty
  private String idOld;

  @Column(name = "name")
  @NotEmpty
  private String name;

  @OneToMany(mappedBy = "activity", fetch = FetchType.EAGER)
  private List<Department> departments = new ArrayList<>();

  @PreRemove
  public void checkActivityAssociationBeforeRemoval() {
    if (!this.departments.isEmpty()) {
      throw new RuntimeException("Can't remove a activity that has departments !");
    }
  }

  public void addDepartment(Department department) {
    departments.add(department);
    ((Department) departments).setActivity(this);
  }

  public void removeDepartment(Department department) {
    departments.remove(department);
    ((Department) departments).setActivity(null);
  }

  @JsonIgnore
  public List<Department> getDepartments() {
    return departments == null ? null : new ArrayList<>(this.departments);
  }

  public void setDepartments(List<Department> departments) {
    if (departments == null) {
      this.departments = null;
    } else {
      this.departments = departments;
    }
  }
}
