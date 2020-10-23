package com.kc.common.util;


import java.util.*;

public class ChartsUtil {

    public static int[] getMonthDays() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDay = a.get(Calendar.DATE);
        int[] days = new int[maxDay];
        for(int i = 1; i <= maxDay; i++){
            days[i-1] = i;
        }
        return days;
    }


    public static List<String> getMonthDaysStr(){
        Date nowDate = new Date();
        int[] daysInMonth = getMonthDays();
        List<String> days = new ArrayList<String>();
        int currMonth = DateTools.getMonth(nowDate);
        int currDay = DateTools.getDay(nowDate);

        for(int value: daysInMonth){
            if(value <= currDay){
                days.add(currMonth+"/"+value);
            }
        }
        return days;
    }
    public static void main(String[] args) throws Exception {
            System.out.print(getMonthDaysStr().toString());
    }


}
