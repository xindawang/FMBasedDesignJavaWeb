package com.example.fnweb.tools;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeTool {
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	public static java.util.Date stringToUtilDate(String stringDate) throws ParseException {
		try {
			return DATE_FORMATTER.parse(stringDate);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw e;
		}
	}

	public static Date stringToSqlDate(String stringDate) throws ParseException {
		java.util.Date utilDate;
		try {
			utilDate = stringToUtilDate(stringDate);
			return new Date(utilDate.getTime());
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw e;
		}
	}
	
	public static String utilDateToString(java.util.Date date){
		return DATE_FORMATTER.format(date);
	}
}
