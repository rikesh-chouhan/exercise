package org.prep.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.prep.example.model.Partner2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.prep.example.model.Partners2;

public class HubspotMapper2 extends FileReaderAndWriter {

    public static final long DAY = 24 * 60 * 60 * 1000;
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        if (args.length > 2) {
            String fileToReadFrom = args[0];
            String fileToWriteTo = args[1];
            HubspotMapper2 mapper2 = new HubspotMapper2();
            Partners2 partners = mapper2.readInputData(fileToReadFrom, Partners2.class);
            List<Partner2> partner2List = partners != null ? partners.partners : Collections.emptyList();
            List<String> dateList = partner2List.stream()
                    .map(partner2 -> partner2.availableDates)
                    .flatMap(dt2 -> dt2.stream())
                    .distinct()
                    .collect(Collectors.toList());

            List<AbstractMap.SimpleImmutableEntry> collect = partner2List.stream().map(p2 ->
                    p2.availableDates.stream()
                            .map(value2 -> new AbstractMap.SimpleImmutableEntry(value2, p2.country))
            ).flatMap(list2 -> list2).collect(Collectors.toList());

            dateList.forEach(System.out::println);
            System.out.println("*********************");

            Map<String, List<Date>> countryDatesMap = new HashMap();
            collect.forEach(dateCountryEntry -> {
                if (countryDatesMap.containsKey(dateCountryEntry.getValue())) {
                    try {
                        Date parsedDate = DATE_FORMAT.parse(dateCountryEntry.getKey().toString());
                        countryDatesMap.get(dateCountryEntry.getValue()).add(parsedDate);
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }
                } else {
                    final List<Date> dates = new ArrayList<>();
                    try {
                        Date parsedDate = DATE_FORMAT.parse(dateCountryEntry.getKey().toString());
                        dates.add(parsedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    countryDatesMap.put(dateCountryEntry.getValue().toString(), dates);
                }

            });
            System.out.println("%%%%%%%%%%%%%%%%%%%");

            countryDatesMap.forEach((key,value) -> {
                Collections.sort(value);
                System.out.println("key: "+key+ " value: "+value);
            });


        }
    }


}
