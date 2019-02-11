package com.cft.hogan.platform.ppm.api.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.cft.hogan.platform.ppm.api.config.context.ApplicationContext;
import com.cft.hogan.platform.ppm.api.exception.BusinessError;
import com.cft.hogan.platform.ppm.api.exception.ExceptionHanlder;


public class Utils {

	public static java.sql.Date convertStringToSQLDate(String str_date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = (Date) formatter.parse(str_date);
		return  new java.sql.Date(date.getTime());
	}

	public static String convertDateToString(Date date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return  formatter.format(date);
	}

	public static Date getCurrentDate() {
		return new Date(Calendar.getInstance().getTimeInMillis());
	}

	public static boolean isValidDate(String date) {
		try {
			try {
				int day = Integer.parseInt(date.substring(8, 10));
				int month = Integer.parseInt(date.substring(5, 7));
				int year = Integer.parseInt(date.substring(0, 4));
				boolean validDate = true;		
				if(date.length() != 10) {
					validDate = false;
				}else if(month >12 || month <1) {
					validDate = false;
				}else if(day >31 || day < 1) {
					validDate = false;
				}else if(month == 2) {
					if(year%4 == 0 && day >29) {
						validDate = false;
					}else if(year%4 != 0 && day >28) {
						validDate = false;
					}
				}else if((month==4 || month==6 || month==9 || month==11 ) && day >30 ) {
					validDate = false;
				}

				if(!validDate) {
					throw new BusinessError("Invalid date: "+date, true);
				}
			}catch(Exception e) {
				throw new BusinessError("Invalid date: "+date, true);
			}
		}catch(Exception e) {
			ExceptionHanlder.handleException(e);
			return false;
		}
		return true;
	}

	public static String convertTimeStampToString(String time) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(Constants.YYYY_MM_DD_HHMMSS);
		return formatter.format(new Date(Long.parseLong(time)));
	}


	public static String leftPad(final String str, final int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (StringUtils.isEmpty(padStr)) {
			padStr = Constants.SPACE;
		}
		final int padLen = padStr.length();
		final int strLen = str.length();
		final int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= Constants.PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			final char[] padding = new char[pads];
			final char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	public static String leftPad(final String str, final int size, final char padChar) {
		if (str == null) {
			return null;
		}
		final int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > Constants.PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return repeat(padChar, pads).concat(str);
	}

	private static String repeat(final char ch, final int repeat) {
		if (repeat <= 0) {
			return Constants.EMPTY;
		}
		final char[] buf = new char[repeat];
		for (int i = repeat - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}

	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static String getLogMsg() {
		StringBuffer msg = new StringBuffer()
				.append("SessionID:").append(ApplicationContext.getSessionID())
				.append(" -User:").append(ApplicationContext.getLoggedInUser())
				.append(" -Region:").append(ApplicationContext.getRegion());
		return msg.toString();
	}
}