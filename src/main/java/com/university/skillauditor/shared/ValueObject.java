package com.university.skillauditor.shared;

public abstract class ValueObject {

    protected static void argumentNotEmpty(String value, String errorMessage) {
        DomainAssertions.argumentNotEmpty(value, errorMessage);
    }

    protected static void argumentLength(String value, int min, int max,
                                         String errorMessage) {
        DomainAssertions.argumentLength(value, min, max, errorMessage);
    }
}   