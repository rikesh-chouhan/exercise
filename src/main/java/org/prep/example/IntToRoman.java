package org.prep.example;

import java.util.List;
import java.util.Map;

public class IntToRoman {

    public static final Map<Integer, String> ONE_CHART = Map.of(1, "I", 10, "X", 100, "C", 1000, "M");
    public static final Map<Integer, String> FIVE_CHART = Map.of(1, "V", 10, "L", 100, "D");

    public static void main(String[] args) {
        IntToRoman itr = new IntToRoman();
        List<Integer> numbers = List.of(3749, 10, 20, 40, 900);
        for (Integer number: numbers) {
            String num = itr.intToRoman(number);
            System.out.println(num);
        }
    }

    public String intToRoman(int num) {
        if (num > 3999) {
            throw new IllegalArgumentException(num + " greater than 3999");
        }
        StringBuilder buffer = new StringBuilder();
        int powerTen = 10;
        String decNum = "" + num;
        int counter = decNum.length();
        decNum = buffer.append(decNum).reverse().toString();
        buffer.delete(0, buffer.length());
        while (counter > 0) {
            powerTen = Double.valueOf(Math.pow(powerTen, counter-1)).intValue();
            int digit = Integer.parseInt(decNum.substring(counter - 1, counter));
            if (digit != 0) {
                buffer.append(getNumeral(powerTen, digit));
            }
            powerTen = 10;
            counter--;
        }

        return buffer.toString();
    }

    public String getNumeral(int tenPower, int number) {
        String numeral = "";
        switch (tenPower) {
            case 1:
                if (number == 4) {
                    numeral = "IV";
                } else if (number == 9) {
                    numeral = "IX";
                } else {
                    numeral = getForNon4And9(tenPower, number);
                }
                break;
            case 10:
                if (number == 4) {
                    numeral = "XL";
                } else if (number == 9) {
                    numeral = "XC";
                } else {
                    numeral = getForNon4And9(tenPower, number);
                }
                break;
            case 100:
                if (number == 4) {
                    numeral = "CD";
                } else if (number == 9) {
                    numeral = "CM";
                } else {
                    numeral = getForNon4And9(tenPower, number);
                }
                break;
            case 1000:
                numeral = getForNon4And9(tenPower, number);
                break;
            default:
                numeral = "";
                break;
        }
        //System.out.println("number-" + number + "-power-" + tenPower + "-numeral-" + numeral);
        return numeral;
    }

    String getForNon4And9(int tenPower, int number) {
        if (number % 5 == 0) {
            return FIVE_CHART.get(tenPower);
        } else {
            StringBuffer builder = new StringBuffer();
            int remainder = number % 5;
            for (int i = 0; i < remainder; i++) {
                builder.append(ONE_CHART.get(tenPower));
            }
            if (number > 5) {
                builder.insert(0, FIVE_CHART.get(tenPower));
            }
            return builder.toString();
        }
    }
}