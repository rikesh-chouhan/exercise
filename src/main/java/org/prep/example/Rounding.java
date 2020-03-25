package org.prep.example;

public class Rounding {

    public static void main(String[] args) {
        for(String one: args) {
            int completed = 0;
            String prev = one.substring(completed, completed+1);
            for(int i=0; i<one.length(); i++) {
                String curr = one.substring(i, i+1);
                if (!curr.equals(prev)) {
                    completed = i;
                    break;
                }
            }
            System.out.println((int)Math.floor(((float)completed/one.length()) * 100));
        }
    }
}
