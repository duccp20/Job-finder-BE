package com.example.jobfinder.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "candidate")
public class Candidate extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	private User user;
	
	@Column(name = "university")
	private String university;

	@Column(name = "CV", length = 500)
	private String CV;

	@Column(name = "reference_letter", length = 5000)
	private String referenceLetter;

	@Column(name = "desired_job", length = 1000)
	private String desiredJob;

	@Column(name = "desired_working_province")
	private String desiredWorkingProvince;

	@OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<CandidateMajor> candidateMajors = new ArrayList<>();

	@OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<CandidatePosition> candidatePositions = new ArrayList<>();

	@OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<CandidateSchedule> candidateSchedules = new ArrayList<>();

	@Column(name = "searchable")
	boolean searchable; // permit hr to search

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@EqualsAndHashCode(callSuper = true)
	@Entity
	@Table(name = "company")
	public static class Company extends Auditable {

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

		@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
		@Fetch(FetchMode.SUBSELECT)
		private List<CompanyRate> companyRates = new ArrayList<>();

	}

	@AllArgsConstructor
	@Data
	@EqualsAndHashCode(callSuper = true)
	@NoArgsConstructor
	@Entity
	@Table(name = "company_rate")
	public static class CompanyRate extends Auditable {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private long id;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "user_id")
		private User user;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "company_id")
		private Company company;

		@Column(name = "score")
		private int score;

		@Column(name = "title")
		private String title;

		@Column(name = "comment")
		private String comment;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "status_id", nullable = false)
		private Status status;

	}
}
