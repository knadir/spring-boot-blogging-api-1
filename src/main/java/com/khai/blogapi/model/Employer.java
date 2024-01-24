package com.khai.blogapi.model;

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
@Table(name = "employers")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employer extends UserDateAudit {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "employers_id_seq"
  )
  @SequenceGenerator(name = "employers_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "id_old")
  @NotEmpty
  private String idOld;

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

  @Column(name = "id_number")
  @NotEmpty
  private String identNumber;

  @Column(name = "place_born")
  @NotEmpty
  private String placeBorn;

  @Column(name = "birthday")
  @NotNull
  private Date birthday;

  @Column(name = "date_of_termination")
  private Date dateOfTermination;

  @Column(name = "place_addr")
  @NotEmpty
  private String placeAddr;

  @Column(name = "street")
  @NotEmpty
  private String street;

  @Column(name = "street_number")
  @NotEmpty
  private String streetNumber;

  @Column(name = "bank_account")
  @NotEmpty
  private String bankAccount;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "municipality_born_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_MUNICIPALITY_EMPLOYER_BORN")
  )
  private Municipality municipalityBorn;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "municipality_addr_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_MUNICIPALITY_EMPLOYER_ADDR")
  )
  private Municipality municipalityAddr;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "gender_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_GENDER_EMPLOYER")
  )
  private Gender gender;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "qualification_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_QUALIFICATION_EMPLOYER")
  )
  private Qualification qualification;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "bank_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_BANK_EMPLOYER")
  )
  private Bank bank;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "department_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_DEPARTMENT_EMPLOYER")
  )  
  private Department department;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
    name = "cost_place_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_COST_PLACE_EMPLOYER")
  )
  private CostPlace costPlace;
}
