package com.control;

import java.util.*;
import java.text.*;

public class NextTime {
	public static String getNextMonth(String now){
		Date t;
		String nmonth=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");

		try{
			t=sdf.parse(now);
			Date nextmonth=new Date((t.getYear()+(t.getMonth()+1)/12),(t.getMonth()+1)%12,t.getDate());
			nmonth = sdf.format(nextmonth.getTime());

		}catch(Exception e ){

			e.printStackTrace();
		}
		return nmonth;
	}

	public static String getNextWeek(String now){
		Date nowdate;
		String nweek=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

		try{
			nowdate=sdf.parse(now);
			
			long oneday=1000*3600*24;
			nowdate.getDay();
			Date nextweek=new Date(nowdate.getTime()+oneday*(8-nowdate.getDay()));
			
			nweek = sdf.format(nextweek.getTime());

		}catch(Exception e ){

			e.printStackTrace();
		}
		return nweek;
	}
	public static String getFirstWeek(String now){
		Date nowdate;
		String nweek=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

		try{
			nowdate=sdf.parse(now);
			Date nextweek;
			long oneday=1000*3600*24;
			if(nowdate.getDay()==0)
				{nextweek=new Date(nowdate.getTime()-oneday*6);}
			else
			 nextweek=new Date(nowdate.getTime()-oneday*(nowdate.getDay()-1));
			
			nweek = sdf.format(nextweek.getTime());

		}catch(Exception e ){

			e.printStackTrace();
		}
		return nweek;
	}
	public static String getNextDay(String now){
		Date nowdate;
		String nday=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try{
			nowdate=sdf.parse(now);
			long oneday=1000*3600*24;
			Date nextday=new Date(nowdate.getTime()+oneday);
			nday = sdf.format(nextday.getTime());
			System.out.println(nday);

		}catch(Exception e ){

			e.printStackTrace();
		}
		return nday;
	}
	public static String getNextHour(String now){
		Date nowdate;
		String nhour=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HH:MM:SS");

		try{
			nowdate=sdf.parse(now);
			long onehour=1000*3600;
			Date nextday=new Date(nowdate.getTime()+onehour);
			nhour= sdf.format(nextday.getTime());

		}catch(Exception e ){

			e.printStackTrace();
		}
		return nhour;
	}
	
	public static String getNextHours(String now){
		Date nowdate;
		String nhour=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");

		try{
			nowdate=sdf.parse(now);
			long onehour=1000*3600;
			Date nextday=new Date(nowdate.getTime()+onehour);
			nhour= sdf.format(nextday.getTime());

		}catch(Exception e ){

			e.printStackTrace();
		}
		return nhour;
	}
	static final long ONE_MINUTE_IN_MILLIS=60000;
	public static String getAddTime(String now,int slot){
		Date nowdate;
		String nhour=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try{
			nowdate=sdf.parse(now);
			long onehour=slot*ONE_MINUTE_IN_MILLIS;
			Date nextday=new Date(nowdate.getTime());
			Calendar cal= Calendar.getInstance();
			cal.setTime(nextday);
			long t=cal.getTimeInMillis();
			Date afterAddingTenMins=new Date(t+onehour);
			nhour=sdf.format(afterAddingTenMins.getTime());
			//System.out.println("date:"+sdf.format(afterAddingTenMins.getTime()));  
		}catch(Exception e ){

			e.printStackTrace();
		}
		return nhour;
	}
	public static long getMinuTime(String end,String begin){
		Date nowdate;
		String nhour=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long total_minute=0;
		 try {  
		        Date begin_date = sdf.parse(begin);  
		        Date end_date = sdf.parse(end);  
		  
		        total_minute = (end_date.getTime() - begin_date.getTime())/(1000*60);  
		 
		  
		    } catch (ParseException e) {  
		        System.out.println("传入的时间格式不符合规定");  
		    }  
		 return total_minute;
	}
	
	public static void main(String args[]){
		
		
		
		String d=getNextMonth("201212");
		System.out.println(d);
		d=getFirstWeek("20141221");
		System.out.println(d);
		d=getNextDay("20140228");
		System.out.println(d);
		d=getAddTime("2014-12-09 01:30:20",30);
		System.out.println(d);
	}
}
