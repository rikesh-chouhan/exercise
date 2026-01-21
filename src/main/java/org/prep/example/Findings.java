package org.prep.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategyBuilder;
import com.opencsv.exceptions.CsvException;
import org.prep.example.model.SecIssue;
import org.prep.example.model.SecSeverity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Findings {

  static class IssueSeverity extends SecIssue {
    public String getSeverity() {
      return severity;
    }

    protected String severity;

    IssueSeverity() {
      super();
    }

    IssueSeverity(SecIssue toCopy) {
      this.id = toCopy.getId();
      this.parentId = toCopy.getParentId();
      this.ruleId = toCopy.getParentId();
      this.status = toCopy.getStatus();
    }

    @Override
    public String toString() {
      return "IssueSeverity{" +
          "severity='" + severity + '\'' +
          ", id='" + id + '\'' +
          ", parentId='" + parentId + '\'' +
          ", status='" + status + '\'' +
          ", ruleId='" + ruleId + '\'' +
          '}';
    }
  }

  public static void main(String[] args) {
//      List<String[]> rows = readCsv(filename);
//      for (String[] each : rows) {
//        System.out.println(String.join(",", each));
//      }
    List<SecIssue> secIssues = null;
    List<SecSeverity> severities = null;
    List<IssueSeverity> issueSeverities = null;
    if (args.length > 0) {
      String filename = args[0];
      secIssues = getFindings(filename);
      //iterate(findings);
    }
    if (args.length > 1) {
      System.out.println("==================");
      String filename = args[1];
      severities = getSeverity(filename);
      //iterate(severities);
      issueSeverities = new ArrayList<>();
      for (SecIssue secIssue : secIssues) {
        for (SecSeverity severity : severities) {
          if (secIssue.getRuleId().equals(severity.getId())) {
            try {
              IssueSeverity is = compose(secIssue, severity);
              issueSeverities.add(is);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
    iterate(issueSeverities);
    Map<Integer, Aggregate> aggregate = issueSeverities.stream()
        .collect(Collectors.groupingBy(item -> item.getParentId()))
        .entrySet().stream()
        .map(entry -> aggregate(entry.getKey(), entry.getValue()))
        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()))
        ;
    System.out.println(JsonUtils.prettyPrintObject(aggregate));
  }

  static class Aggregate {
    public Integer count;
    public Integer id;
  }

  public static Map.Entry<Integer, Aggregate> aggregate(Integer parentId, List<IssueSeverity> children) {
    Aggregate result = new Aggregate();
    result.count = children.size();
    result.id = 0;
    Collections.sort(children, (is1, is2) -> Integer.compare(is1.getId(), is1.getId()));
    for (IssueSeverity is: children) {
      if (is.getSeverity().equals("HIGH") && is.getId() > result.id && !is.getStatus().equals("fixed")) {
        result.id = is.getId();
      } else if (is.getStatus().equals("unresolved") && is.getId() > result.id) {
        result.id = is.getId();
      }
    }

    return Map.entry(parentId, result);
  }

  public static IssueSeverity compose(SecIssue issue, SecSeverity severity) throws IOException, ClassNotFoundException {
      IssueSeverity is = new IssueSeverity(issue);
      is.severity = severity.getSeverity();
      return is;
  }

  public static void iterate(Iterable<? extends Object> iterator) {
    if (iterator != null) {
      iterator.forEach(System.out::println);
    }
  }
  public static List<String[]> readCsv(String filename) {
    List<String[]> array = new ArrayList<>();
    try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filename))
        //.withSkipLines(0)
        .build()
    ) {
      array = csvReader.readAll();
    } catch(CsvException cve) {
      cve.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return array;
  }

  public static List<SecIssue> getFindings(String filename) {
    HeaderColumnNameTranslateMappingStrategy headerColumnNameTranslateMappingStrategy = new HeaderColumnNameTranslateMappingStrategyBuilder<Findings>()
        .build();
    Map<String, String> mapping = Map.of("id", "id", "parent_id", "ParentId", "status", "status", "rule_id", "ruleId");
    headerColumnNameTranslateMappingStrategy.setType(SecIssue.class);
    headerColumnNameTranslateMappingStrategy.setColumnMapping(mapping);
    CsvToBean converter = new CsvToBean();
    List<SecIssue> objects;
    try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filename))
        .build()
    ) {
      converter.setCsvReader(csvReader);
      converter.setMappingStrategy(headerColumnNameTranslateMappingStrategy);
      objects = converter.parse();
    } catch (IOException ioe) {
      ioe.printStackTrace();
      objects = Collections.emptyList();
    }

    return objects;
  }

  public static List<SecSeverity> getSeverity(String filename) {
    HeaderColumnNameTranslateMappingStrategy headerColumnNameTranslateMappingStrategy =
        new HeaderColumnNameTranslateMappingStrategyBuilder<SecSeverity>()
        .build();
    Map<String, String> mapping = Map.of("id", "id", "severity", "severity");
    headerColumnNameTranslateMappingStrategy.setType(SecSeverity.class);
    headerColumnNameTranslateMappingStrategy.setColumnMapping(mapping);
    CsvToBean converter = new CsvToBean();
    List<SecSeverity> objects;
    try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filename))
        .build()
    ) {
      converter.setCsvReader(csvReader);
      converter.setMappingStrategy(headerColumnNameTranslateMappingStrategy);
      objects = converter.parse();
    } catch (IOException ioe) {
      ioe.printStackTrace();
      objects = Collections.emptyList();
    }

    return objects;
  }

}
