package com.loki.server.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTest {
	public static void main(String[] args) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
//		
//		System.out.println(sdf.format(new Date()));
		Pattern p=Pattern.compile("^\\\\d{4}\\\\D+\\\\d{2}\\\\D+\\\\d{2}$");
		String tsStr = "2008-12-12 12:22:33"; 
		Matcher m=p.matcher(tsStr);
		while(m.find()) {
			System.out.println(m.group());
		}
		
	}
}
