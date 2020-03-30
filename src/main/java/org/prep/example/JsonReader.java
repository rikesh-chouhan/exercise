package org.prep.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonReader {

    public String readInputData(String fileName) {
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
