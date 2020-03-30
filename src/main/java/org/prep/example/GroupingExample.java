package org.prep.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.prep.example.model.Partner2;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class GroupingExample {

    public static void main(String[] args) {

        if (args.length > 0) {
            String jsonString = new JsonReader().readInputData(args[0]);
            if (jsonString != null) {
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                JsonArray array = jsonObject.getAsJsonArray("partners");
                LinkedHashSet<Partner2> partnerTree = new LinkedHashSet();
                for(int i=0; i<array.size(); i++) {
                    JsonObject arrayElement = array.get(i).getAsJsonObject();
                    Partner2 partner = new Partner2();
                    partner.firstName = arrayElement.get("firstName").getAsString();
                    partner.lastName = arrayElement.get("lastName").getAsString();
                    partner.email = arrayElement.get("email").getAsString();
                    partner.country = arrayElement.get("country").getAsString();
                    JsonArray dates = arrayElement.getAsJsonArray("availableDates");
                    partner.availableDates = new TreeSet<>();
                    Iterator<JsonElement> dateIt = dates.iterator();
                    while( dateIt.hasNext()) {
                        String date = dateIt.next().getAsString();
                        partner.availableDates.add(date);
                    }
                    partnerTree.add(partner);
                }

                System.out.println(partnerTree.stream().collect(Collectors.groupingBy(Partner2::getCountry,
                        Collectors.groupingBy(Partner2::getAvailableDates, Collectors.counting()))));
                Object countryMap = partnerTree.stream ()
                        //.filter(p -> p.availableDates.size() > 0)
                        //.sorted((e1, e2) -> e1.country.compareTo(e2.country))
                        .collect(Collectors.groupingBy(Partner2::getCountry, Collectors.counting()))
                        ;

                System.out.println(countryMap);

            }
        } else {
            System.out.println("Did not find any input");
        }
    }

}
