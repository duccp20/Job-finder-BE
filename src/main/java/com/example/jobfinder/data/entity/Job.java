package com.example.jobfinder.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "job")
public class Job extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "description", length = 5000)
	private String description;

	@OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<JobMajor> jobMajors = new ArrayList<>();

	@OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<JobPosition> jobPositions = new ArrayList<>();

//	@OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
//	@Fetch(FetchMode.SUBSELECT)
//	private List<JobSchedule> jobSchedules = new ArrayList<>();

	@Column(name = "amount")
	private int amount;

	@Column(name = "salary_min")
	private long salaryMin;

	@Column(name = "salary_max")
	private long salaryMax;

	@Column(name = "requirement", length = 5000)
	private String requirement;

	@Column(name = "other_info", length = 5000)
	private String otherInfo;

	@Column(name = "start")
	private Date startDate;

	@Column(name = "end")
	private Date endDate;

	@Column(name = "location")
	private String location;

	@OneToOne
	@JoinColumn(name = "status_id")
	private Status status;

	@OneToMany(mappedBy = "job",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	private List<CandidateApplication> candidateApplications = new ArrayList<>();


}
