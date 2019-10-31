package org.prep.example.model;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Partner {

    public String firstName;
    public String lastName;
    public String email;
    public String country;

    public Set<Date> availableDates;

    public Partner() {}

    public Set<Date> sortedDates() {
        return new TreeSet(availableDates);
    }
}
