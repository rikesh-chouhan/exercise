package org.prep.example.model;

import java.util.Date;

public class DateCountry {
    public String name;
    public Date date;

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (name != null) {
            buffer.append("name:").append("\"").append(name).append("\"");
        }
        if (buffer.length()>0) {
            buffer.append(",");
        }
        if (date != null) {
            buffer.append("date:").append("\"").append(date).append("\"");
        }
        return buffer.toString();
    }
}
