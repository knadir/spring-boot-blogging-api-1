package com.khai.blogapi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_schedules")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkSchedule extends UserDateAudit {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "work_schedules_id_seq"
  )
  @SequenceGenerator(name = "work_schedules_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "basis")
  @NotEmpty
  private BigDecimal basis;

  @Column(name = "norm_percent")
  @NotNull
  private BigDecimal normPercent;

  @Column(name = "date_of_termination")
  private Date dateOfTermination;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "empolyer_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_EMPLOYER_WORK_SCHEDULE")
  )
  private Employer employer;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "cost_place_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_COST_PLACE_WORK_SCHEDULE")
  )
  private CostPlace costPlace;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "department_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_DEPARTMENT_WORK_SCHEDULE")
  )  
  private Department department;
}
