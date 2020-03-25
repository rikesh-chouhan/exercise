package org.prep.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Processing {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("-----------------");
            String input = br.readLine();
            int dataSize = Integer.parseInt(input);
            input = br.readLine();
            int transferSpeed = Integer.parseInt(input);
            double time = dataSize / transferSpeed;
            input = br.readLine();
            int numArchivers = Integer.parseInt(input);
            int archivers[][] = new int[numArchivers][2];
            for (int i=0; i<numArchivers; i++) {
                input = br.readLine();
                String[] array = input.split(" ");
                archivers[i][0] = Integer.parseInt(array[0]);
                archivers[i][1] = Integer.parseInt(array[1]);
                double currTime1 = (dataSize/archivers[i][0]);
                double currTime2 = (dataSize * (((double)archivers[i][1]/100)/transferSpeed));
                double currTime3 = (dataSize * (((double)archivers[i][1]/100)/archivers[i][0]));
                time = Math.min( currTime1+currTime2+currTime3, time);
            }
            System.out.println((int)Math.ceil(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
