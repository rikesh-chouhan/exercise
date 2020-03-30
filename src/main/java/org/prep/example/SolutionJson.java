package org.prep.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


class SolutionJson {
    public static final String ERROR = "Error";

    public static void main(String[] args) {
        String fetchURL = "https://api.filtered.ai/q/get-partner-availability"; //Utils.getURL();
        String postURL = "https://api.filtered.ai/q/save-partner-availability";
        // Begin writing your code below.
        // Output must be written to the console
        try {
            // get json data
            String data = fetchUrlData(fetchURL);
            JsonObject object = JsonParser.parseString(data).getAsJsonObject();
            // fetch availability object and collect individual partner entries
            JsonArray jsonArray = object.getAsJsonArray("availability");
            List<Partner> partners = new ArrayList(jsonArray.size());
            for (int i=0; i<jsonArray.size(); i++) {
                JsonObject element = jsonArray.get(i).getAsJsonObject();
                String date = element.get("date").getAsString();
                JsonObject partnerObject = element.get("partner").getAsJsonObject();String country = partnerObject.get("country").getAsString();
                // System.out.println(date + " -- " + country);
                Partner partner = new Partner(country, date);
                partners.add(partner);
            }
            // System.out.println("Found " + partners.size() + " partners");
            // Group by country and then group by frequency of dates
            Map<String, Map<String, Long>> countryDateMap = partners.stream()
                    .collect(Collectors.groupingBy(Partner::getCountry, TreeMap::new,Collectors.groupingBy(Partner::getDate, LinkedHashMap::new, Collectors.counting())))
                    ;
            // System.out.println(countryDateMap);

            // prepare data for final output
            for(Map.Entry<String, Map<String, Long>> entry : countryDateMap.entrySet()) {
                // sort entry map by reverse frequency
                Map<String, Long> sortedMap = entry.getValue().entrySet().stream()
                        .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()) == 0 ?
                                e1.getKey().compareTo(e2.getKey()) : e2.getValue().compareTo(e1.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

                // Each country is restricted to 2 dates
                int restrictedSize = 2;
                Map<String, Long> replacementMap = sortedMap.entrySet().stream()
                        .limit(2)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

                entry.setValue(replacementMap);
            }
            // countryDateMap now contains data that needs to be posted
            // System.out.println(countryDateMap);

            System.out.println(postData(postURL, countryDateMap));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ERROR);
        }
    }

    private static String fetchUrlData(String passedUrl) throws Exception {
        URL url = new URL(passedUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        br.close();
        return buffer.toString();
    }

    private static String postData(String postUrl, Map<String, Map<String, Long>> data) throws Exception {
        StringBuilder outputJson = new StringBuilder("{");
        for(Map.Entry<String, Map<String, Long>> entry: data.entrySet()) {
            outputJson.append("")
                    .append("\"").append(entry.getKey()).append("\":");
            outputJson.append(" [");
            for(String key: entry.getValue().keySet()) {
                outputJson.append("\"").append(key).append("\",");
            }
            outputJson.deleteCharAt(outputJson.length()-1);
            outputJson.append("],");
        }
        outputJson.deleteCharAt(outputJson.length()-1);
        outputJson.append("}");
        // System.out.println(outputJson.toString());

        URL theUrl = new URL(postUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) theUrl.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setDoOutput(true);

        OutputStream os = urlConnection.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        byte[] outData = outputJson.toString().getBytes("utf-8");
        pw.println(outputJson.toString());
        pw.flush();
        // os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
        String line = null;
        StringBuilder response = new StringBuilder();
        while((line = br.readLine()) != null) {
            response.append(line.trim());
        }
        br.close();
        os.close();

        return response.toString();
    }

}

class Partner {
    private String country;
    private String date;

    public Partner(String theCountry, String theDate) {
        this.country = theCountry;
        this.date = theDate;
    }

    public String getCountry() {
        return country;
    }

    public String getDate() {
        return date;
    }

    public String toString() {
        return "\"country\":\"" + getCountry() + "\", \"date\": \"" + getDate() +"\"\n";
    }
}