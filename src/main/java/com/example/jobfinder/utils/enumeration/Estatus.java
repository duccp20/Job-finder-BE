package com.example.jobfinder.utils.enumeration;

public enum Estatus {
    Active("Active"),
    Not_Active("Not Active"),
    Lock("Lock"),
    Disable("Disable"),
    Delete("Delete");

    public static int activeStatus = 1;
    public static int notActiveStatus = 2;
    public static int lockStatus = 3;
    public static int disableStatus = 4;
    public static int deleteStatus = 5;

    private final String NAME;

    Estatus(String string) {
        this.NAME = string;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
