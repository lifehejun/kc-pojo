package com.kc.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @Author		 Bruce
 * @Date   		 2018-5-25 下午2:34:46 
 * @Description  考勤工具类
 */
public class TimecardUtil {

	
	private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final String TIME1 = " 00:00:00";
	public static final String TIME2 = " 10:00:00";
	public static final String TIME3 = " 20:00:00";
	public static final String TIME4 = " 23:59:59";
	
	/**
	 * 获取今天日期
	 * @return
	 */
	public static String yyyyMMdd(){
		return ymd.format(new Date());
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String yyyyMMddHHmmss(){
		return ymdhms.format(new Date());
	}
	
	/**
	 * 是否迟到 上午打卡结果 1=正常2=迟到5分钟以内3==迟到30分钟以内4=旷工
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static Integer late() throws NumberFormatException, ParseException{
		Long currStamps = getTimestamps();
		Long normalStamps = getTimestamps(TIME2);
		if(currStamps > normalStamps+30*60)	//矿工
			return 4;
		if(currStamps > normalStamps+5*60 && normalStamps <= currStamps+30*60) //迟到5分钟以内
			return 3;
		if(currStamps > normalStamps  && currStamps <= currStamps+5*60) //迟到30分钟以内
			return 2;
		return 1;
	}
	
	/**
	 * 是否早退 

	 * @return 1=正常2=迟到5分钟以内3==迟到30分钟以内4=旷工
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static Integer leaveEarly() throws NumberFormatException, ParseException {
		Long currStamps = getTimestamps();
		Long normalStamps = getTimestamps(TIME3);
		if(currStamps  >= normalStamps-5*60 && currStamps < normalStamps){	//早退5分钟
			return 2;
		}
		if(currStamps < normalStamps-5*60 &&currStamps  >= normalStamps-30*60 ){ //早退30分钟
			return 3;
		}
		if(currStamps < normalStamps - 30*60 ){ //旷工
			return 4;
		}
		return 1;
	}
	
	/**
	 * 获取现在的时间戳
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static Long getTimestamps() throws NumberFormatException, ParseException{
		return Long.parseLong(dateToStamp(yyyyMMddHHmmss()).substring(0,10));
	}
	/**
	 * 获取今天指定时间的时间戳
	 * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public static Long getTimestamps(String time) throws NumberFormatException, ParseException{
		return Long.parseLong(dateToStamp(yyyyMMdd()+time).substring(0,10));
	}
	
	/**
	 * 时间戳转日期
	 * @param s
	 * @return
	 */
	public static String stampToDate(Long s){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        return simpleDateFormat.format(date);
    }
	
   public static Date transForDate(Long ms){  
        if(ms==null){  
            ms=0L;  
        }  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date temp=null;  
        if(ms!=null){  
            try {  
                String str=sdf.format(ms);  
                temp=sdf.parse(str);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        return temp;  
    } 
	
	/**
	 * 日期戳转时间
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static String dateToStamp(String s) throws ParseException {
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = simpleDateFormat.parse(s);
	        long ts = date.getTime();
	        res = String.valueOf(ts);
	        return res;
	}
	
	/**
	 * 得到日期天数
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }
	
	/**
	 * 是否是周日
	 * @param date
	 * @return
	 */
	public static boolean isSunday(Date date){
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date); 
        if( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        	return true;
        return false;
	}
	
	/**
	 * 得到日期是月份中的哪一天
	 * @param date
	 * @return
	 */
	public static int getDay(Date date){
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date); 
        return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	
	public static void main(String[] args) throws NumberFormatException, ParseException {
		
	}
	
}

