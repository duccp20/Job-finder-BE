package com.example.jobfinder.utils.enumeration;

public enum ERole {
	
	Admin("Role_Admin"),
	Candidate("Role_Candidate"),
	HR("Role_HR");

	private final String NAME;

	ERole(String NAME) {
		this.NAME = NAME;
	}

	@Override
	public String toString() {
		return NAME;
	}
}
