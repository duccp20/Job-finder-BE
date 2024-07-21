package com.example.jobfinder.data.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "companies")
public class Company extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "website")
    private String website;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "tax")
    private String tax;

    @Column(name = "location")
    private String location;

    @Column(name = "personnel_size")
    private String personnelSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    //    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    //    @Fetch(FetchMode.SUBSELECT)
    //    private List<CompanyRate> companyRates = new ArrayList<>();

}
