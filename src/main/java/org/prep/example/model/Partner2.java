package org.prep.example.model;

import java.util.Set;
import java.util.TreeSet;

public class Partner2 {
    public String firstName;
    public String lastName;
    public String email;
    public String country;

    public Set<String> availableDates;

    public Partner2() {}

    public Set<String> sortedDates() {
        return new TreeSet(availableDates);
    }

}
