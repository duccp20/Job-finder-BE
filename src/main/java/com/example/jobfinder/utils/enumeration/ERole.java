package com.example.jobfinder.utils.enumeration;

public enum ERole {
    Admin("Role_Admin"),
    Candidate("Role_Candidate"),
    HR("Role_HR");

    private final String NAME;

    public static int adminRole = 1;
    public static int candidateRole = 2;
    public static int hrRole = 3;

    ERole(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
