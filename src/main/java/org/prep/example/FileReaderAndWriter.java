package org.prep.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class FileReaderAndWriter {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    ObjectMapper mapper = new ObjectMapper();
    public static final long DAY = 24 * 60 * 60 * 1000;


    public <T>T readInputData(String fileName, Class<T> clazz) {
        StringBuffer jsonStringBuffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonStringBuffer.append(line);
            }
            System.out.println("Finished reading file");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonStringBuffer != null) {

            try {
                mapper.setDateFormat(DATE_FORMAT);
                T tdata = mapper.readValue(jsonStringBuffer.toString(), clazz);
                return tdata;
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public <T> String jsonCountries(T data) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }
        return "";
    }

    public void writeToFile(String fileName, String toWrite) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            PrintWriter pw = new PrintWriter(fileOutputStream);
            pw.println(toWrite);
            pw.flush();
            pw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
