package org.prep.example;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StatsExample {

    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        String jsonString = readInputData(args[0]);
        if (jsonString != null) {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            JsonArray array = jsonObject.getAsJsonArray("partners");
            System.out.println(array.size());

            Map<String, Map<String, Integer>> countryDate = new TreeMap<String,
                                Map<String, Integer>>();
            for(int i=0; i<array.size(); i++) {
                JsonObject arrayElement = array.get(i).getAsJsonObject();
                String country = arrayElement.get("country").getAsString();
                System.out.println("Country: " + country);
                Map<String, Integer> dateCount = countryDate.get(country);
                JsonArray dates = arrayElement.getAsJsonArray("availableDates");
                if (dateCount == null) {
                    dateCount = new HashMap();
                    countryDate.put(country, dateCount);
                }
                Iterator<JsonElement> dateIt = dates.iterator();
                while( dateIt.hasNext()) {
                    String date = dateIt.next().getAsString();
                    Integer counter = dateCount.get(date);
                    if (counter == null) {
                        counter = 0;
                    }
                    counter++;
                    dateCount.put(date, counter);
                }
            }

            int size = 2;
            for (Map.Entry<String, Map<String, Integer>> entry: countryDate.entrySet()) {
                Map<String, Integer> sortedByCount = entry.getValue().entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1,
                                LinkedHashMap::new));
                Map<String, Integer> restricted = sortedByCount.entrySet().stream().limit(2)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                entry.setValue(restricted);
            }

            System.out.println(countryDate);
        }
    }

    public static String readInputData(String fileName) {
        StringBuffer jsonStringBuffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonStringBuffer.append(line);
            }
            System.out.println("Finished reading file: " + fileName);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonStringBuffer != null) {
            return jsonStringBuffer.toString();
        }
        return null;
    }

}
