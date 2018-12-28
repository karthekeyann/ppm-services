package com.cft.hogan.platform.ppm.services.massmaintenance.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cft.hogan.platform.ppm.services.config.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.BadRequestException;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.BusinessException;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.ItemNotFoundException;
import com.cft.hogan.platform.ppm.services.massmaintenance.exception.SystemException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
				if(date.length() != 10) {
					throw new BusinessException("Invalid date: "+date, true);
				}else if(Integer.parseInt(date.substring(5, 7)) >12 || Integer.parseInt(date.substring(5, 7)) <1) {
					throw new BusinessException("Invalid date: "+date, true);
				}else if(Integer.parseInt(date.substring(8, 10)) >31 || Integer.parseInt(date.substring(8, 10)) <1) {
					throw new BusinessException("Invalid date: "+date, true);
				}
			}catch(Exception e) {
				throw new BusinessException("Invalid date: "+date, true);
			}
		}
		catch(Exception e) {
			Utils.handleException(e);
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
				.append("SessionID:").append(getSessionID())
				.append(" -User:").append(getLoggedInUser())
				.append(" -Environment:").append(SystemContext.getEnvironment())
				.append(" -Region:").append(Utils.getRegion());
		return msg.toString();
	}

	public static String getRegion() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String region = request.getHeader("X-region");
		if(region==null || StringUtils.isEmpty(region) || 
				!(region.equalsIgnoreCase(Constants.REGION_COR) || region.equalsIgnoreCase(Constants.REGION_TDA) || 
						region.equalsIgnoreCase(Constants.REGION_PASCOR) || region.equalsIgnoreCase(Constants.REGION_PASTDA))) {
			throw new SystemException("Invalid request header - X-region :"+region);
		}
		return region;
	}

	public static String getUserIdInRequestHeader() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String user = request.getHeader("X-user");
		if(user==null || StringUtils.isEmpty(user)) {
			throw new SystemException("Invalid request header - X-user missing");
		}
		return user;
	}

	public static String getSessionID() {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
		if(session!=null) {
			return session.getId();
		}
		return Constants.EMPTY;
	}

	public static String getLoggedInUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public static void handleException(Exception e) {
		try {
			log.error(getLogMsg());
			log.error("Service Endpoint:"+SystemContext.getEndPoint());
			log.error(e.getMessage(),e );
			if(e instanceof BusinessException) {
				if(((BusinessException) e).processFurther) {
					log.error("Business error occured. Error information logged and continue processing.");
				}else{
					throw new BusinessException(e.getMessage(), false);
				}
			}else if(e instanceof ItemNotFoundException || e instanceof NoResultException || e instanceof EmptyResultDataAccessException) {
				throw new ItemNotFoundException();
			}else if(e instanceof BadRequestException) {
				throw new BadRequestException(e.getMessage());
			}else if(e instanceof SystemException) {
				throw new SystemException(e.getMessage());
			}else {
				throw new SystemException();
			}
		} catch (IOException ex) {
			handleException(ex);
		}
	}
}