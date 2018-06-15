package com.xdong.webapp;

import java.util.Calendar;
import java.util.Date;

public class Test {

	public static Date addOneSecond(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	public static void main(String[] args) {
		Date now = new Date();
		System.out.println(now.getTime());
		System.out.println(addOneSecond(now, 30).getTime());
	}

}
