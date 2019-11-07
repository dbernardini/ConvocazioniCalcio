package com.bernardini.danilo.convocazioniriofreddo;

import java.util.Comparator;

public class PlayersComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {
        int num1 = 0;
        int num2 = 0;
        if (str1.contains(" ")){
            String number1 = str1.substring(0,str1.indexOf(' '));
            if (number1.matches("\\d+")) {
                num1 = Integer.parseInt(number1);
            }
        }
        if (str2.contains(" ")) {
            String number2 = str2.substring(0,str2.indexOf(' '));
            if (number2.matches("\\d+")) {
                num2 = Integer.parseInt(number2);
            }
        }
        return num1-num2;
    }
}
