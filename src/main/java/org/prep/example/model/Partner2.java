package org.prep.example.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class Partner2 {

    public String firstName;
    public String lastName;
    public String email;
    public String country;

    public Set<String> availableDates;

    public Partner2() {}

    public String getCountry() { return country; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public Set<String> getAvailableDates() { return availableDates; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partner2)) return false;
        Partner2 partner2 = (Partner2) o;
        return getCountry().equals(partner2.getCountry()) &&
                getAvailableDates().equals(partner2.getAvailableDates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry(), getAvailableDates());
    }
}
