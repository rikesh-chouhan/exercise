package org.prep.example;

import org.prep.example.model.Countries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.prep.example.model.Attendee;
import org.prep.example.model.CountryPartner;
import org.prep.example.model.DateCountry;
import org.prep.example.model.Partner;
import org.prep.example.model.Partners;

public class HubSpotDataReader extends FileReaderAndWriter {


    public static void main(String[] args) {
        if (args.length > 0) {
            HubSpotDataReader hubSpotDataReader = new HubSpotDataReader();
            Partners partners = hubSpotDataReader.readInputData(args[0], Partners.class);
            List<Partner> partnerList = partners != null ? partners.partners : Collections.emptyList();
            System.out.println("Got partner list: " + partnerList.size());
            Set<String> names = partnerList.stream().map(partner -> partner.country).collect(Collectors.toSet());
            System.out.println("country names: "+names);
            Map<List<Date>, Partner> datePartnerMap = hubSpotDataReader.provideDateMap(partnerList);
            TreeMap<DateCountry, List<Partner>> datePartnerListMap = new TreeMap((e1, e2) -> {
                return ((DateCountry) e1).date.compareTo(((DateCountry) e2).date);
            });
            for (Partner partner : partnerList) {
                for (Date aDate : partner.availableDates) {
                    if (aDate != null) {
                        DateCountry dc = new DateCountry();
                        dc.date = aDate;
                        dc.name = partner.country;
                        datePartnerMap.forEach((dates, entry) -> {
                            if (dates.contains(aDate) && !entry.equals(partner)) {
                                List<Partner> partnersEntry = datePartnerListMap.get(dc);
                                if (partnersEntry == null) {
                                    partnersEntry = new ArrayList();
                                    DateCountry dc2 = new DateCountry();
                                    dc2.date = aDate;
                                    dc2.name = partner.country;
                                    datePartnerListMap.put(dc2, partnersEntry);
                                }
                                partnersEntry.add(entry);
                            }
                        });
                        if (!datePartnerListMap.containsKey(dc)) {
                            datePartnerListMap.put(dc, Collections.emptyList());
                        }
                    }
                }
            }

            System.out.println("first entry size: " + datePartnerListMap.firstEntry().getValue().size()
                    + " date: " + datePartnerListMap.firstEntry().getKey());

            System.out.println("last entry size: " + datePartnerListMap.lastEntry().getValue().size()
                    + " date: " + datePartnerListMap.lastEntry().getKey());

            TreeMap<List<Partner>, DateCountry> reverseMap =
                    new TreeMap((e1, e2) -> {
                        return ((List) e2).size() > ((List) e1).size() ? 1 : ((List) e2).size() == ((List) e1).size() ? 0 : -1;
                    });

            datePartnerListMap.forEach((date, list) -> reverseMap.put(list, date));

            System.out.println("first entry: " + reverseMap.firstEntry().getKey().size() + " date: " + reverseMap.firstEntry().getValue());

            System.out.println("last entry: " + reverseMap.lastEntry().getKey().size() + " date: " + reverseMap.lastEntry().getValue());

            Map<String, CountryPartner> countryPartnerMap = new HashMap();
            Map<Partner, Map<Date, List<Partner>>> dateSortedMap = hubSpotDataReader.obtainDatePartnerMap(partnerList);

            dateSortedMap.forEach((eachP, map) -> {
                CountryPartner countryPartner = countryPartnerMap.get(eachP.country);
                Map.Entry<Date, List<Partner>> entry = null;
                if (map.size() > 0) {
                    entry = map.entrySet().iterator().next();
                    System.out.println(
                            "map size: " + map.size() +
                                    " partner: " + eachP.country + " -name- " + eachP.firstName +
                                    " date: " + DATE_FORMAT.format(entry.getKey()) +
                                    " partner size: " + entry.getValue().size());
                } else {
                    System.out.println("partner with no matching dates: " + eachP.country);
                }
                if (entry == null) {
                    if (countryPartner == null) {
                        countryPartner = new CountryPartner();
                        countryPartner.country = eachP.country;
                        countryPartner.date = eachP.sortedDates().iterator().next();
                        countryPartner.partners = Collections.EMPTY_LIST;
                    } else {
                        // this entry is null, but previous incarnation was not so leave it
                    }
                } else {
                    if (countryPartner == null) {
                        countryPartner = new CountryPartner();
                        countryPartner.country = eachP.country;
                        countryPartner.date = entry.getKey();
                        countryPartner.partners = entry.getValue();
                    } else {
                        if (countryPartner.partners.size() < entry.getValue().size()) {
                            countryPartner.partners = entry.getValue();
                            countryPartner.date = entry.getKey();
                        } else if (countryPartner.partners.size() == entry.getValue().size()
                                && countryPartner.date != null && countryPartner.date.after(entry.getKey())) {
                            countryPartner.partners = entry.getValue();
                            countryPartner.date = entry.getKey();
                        }
                    }
                }
                countryPartnerMap.put(eachP.country, countryPartner);
            });

            Countries countries = hubSpotDataReader.provideCountries(reverseMap);
            String jsonString = hubSpotDataReader.jsonCountries(countries);
            if (args.length > 1) {
                hubSpotDataReader.writeToFile(args[1], jsonString);
            } else {
                System.out.println("%%%%%%%%%%");
                System.out.println(jsonString);
                System.out.println("%%%%%%%%%%");
            }

            if (args.length > 2) {
                Countries fromCPMap = hubSpotDataReader.provideCountriesForCountryPartner(countryPartnerMap);
                String jsonCountries = hubSpotDataReader.jsonCountries(fromCPMap);
                hubSpotDataReader.writeToFile(args[2], jsonCountries);
            }

        } else {
            System.out.println("Did not find any input to process");
        }
    }


    public Map<List<Date>, Partner> provideDateMap(List<Partner> partners) {
        Map<List<Date>, Partner> map = new HashMap<>();
        partners.forEach(partner -> {
            List<Date> list = new ArrayList(partner.availableDates);
            Collections.sort(list);
            map.put(list, partner);
        });
        return map;
    }

    public Countries provideCountries(Map<List<Partner>, DateCountry> attendeeMap) {
        Countries countries = new Countries();
        attendeeMap.forEach((partners, dateCountry) -> {
            Attendee attendee = new Attendee();
            attendee.name = dateCountry.name;
            if (partners.size() > 0) {
                Set<String> attendees = new LinkedHashSet<>();
                for (Partner partner : partners) {
                    attendees.add(partner.email);
                }
                attendee.startDate = DATE_FORMAT.format(dateCountry.date);
                attendee.attendees = attendees;
                attendee.attendeeCount = attendees.size();
            } else {
                attendee.attendeeCount = 0;
                attendee.attendees = Collections.emptySet();
            }
            countries.countries.add(attendee);
        });

        return countries;
    }

    public Countries provideCountriesForCountryPartner(Map<String, CountryPartner> attendeeMap) {
        Countries countries = new Countries();
        attendeeMap.forEach((countryName, countryPartner) -> {
            Attendee attendee = new Attendee();
            attendee.name = countryPartner.country;
            if (countryPartner.partners.size() > 0) {
                Set<String> attendees = new LinkedHashSet<>();
                for (Partner partner : countryPartner.partners) {
                    attendees.add(partner.email);
                }
                attendee.startDate = DATE_FORMAT.format(countryPartner.date);
                attendee.attendees = attendees;
                attendee.attendeeCount = attendees.size();
            } else {
                attendee.attendeeCount = 0;
                attendee.attendees = Collections.emptySet();
            }
            countries.countries.add(attendee);
        });

        return countries;
    }

    public Map<Partner, Map<Date, List<Partner>>> obtainDatePartnerMap(List<Partner> partners) {
        Map<Partner, Map<Date, List<Partner>>> treeMap = new HashMap();

        partners.forEach(partner -> {
            if (partner.availableDates != null && partner.availableDates.size() > 0) {
                Map<Date, List<Partner>> innerMap = new TreeMap((date1, date2) -> {
                    return ((Date) date1).compareTo((Date) date2);
                });

                List<Date> list = new ArrayList(partner.availableDates);
                Collections.sort(list);
                list.forEach(singleDate -> {
                    List<Partner> matchingPartners = getMatchingDatePartners(singleDate, partners);
                    innerMap.put(singleDate, matchingPartners);
                });
                Date most = innerMap.keySet().iterator().next();
                List<Partner> mostPartners = Collections.emptyList();
                for (Map.Entry<Date, List<Partner>> entry : innerMap.entrySet()) {
                    if (mostPartners.size() < entry.getValue().size()) {
                        mostPartners = entry.getValue();
                        most = entry.getKey();
                    }
                }
                innerMap.clear();
                innerMap.put(most, mostPartners);
                treeMap.put(partner, innerMap);
            } else {
                treeMap.put(partner, Collections.EMPTY_MAP);
            }
        });
        return treeMap;
    }

    private List<Partner> getMatchingDatePartners(Date toMatch, List<Partner> list) {
        Date next = new Date(toMatch.getTime() + DAY);
        return list.stream().filter(partner ->
                partner.availableDates.contains(toMatch)
                        && partner.availableDates.contains(next)
        ).collect(Collectors.toList());
    }
}
