package org.prep.example.model;

public class ShippingPkg {
    public Integer id;
    public Priority priority;

    public enum Priority {
        LOW,
        HIGH;
    }

}
