package org.prep.example.model;

public class SecIssue {

  protected Integer id;
  protected Integer parentId;
  protected String status;
  protected Integer ruleId;

  @Override
  public String toString() {
    return "SecIssue{" +
        "id='" + id + '\'' +
        ", parentId='" + parentId + '\'' +
        ", status='" + status + '\'' +
        ", ruleId='" + ruleId + '\'' +
        '}';
  }

  public SecIssue() {}

  public Integer getId() {
    return id;
  }

  public Integer getParentId() {
    return parentId;
  }

  public String getStatus() {
    return status;
  }

  public Integer getRuleId() {
    return ruleId;
  }
}
