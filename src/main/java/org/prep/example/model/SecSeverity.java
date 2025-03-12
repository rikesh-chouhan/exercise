package org.prep.example.model;

public class SecSeverity {
  Integer id;
  String severity;

  public SecSeverity() {}

  public Integer getId() {
    return id;
  }

  public String getSeverity() {
    return severity;
  }

  @Override
  public String toString() {
    return "SecSeverity{" +
        "id='" + id + '\'' +
        ", severity='" + severity + '\'' +
        '}';
  }
}
