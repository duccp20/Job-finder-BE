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
}
