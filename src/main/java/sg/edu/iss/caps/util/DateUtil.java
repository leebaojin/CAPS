package sg.edu.iss.caps.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss (Z) yyyy");
	private static DateTimeFormatter dtf_local = DateTimeFormatter.ofPattern("d/M/yyyy hh:mm:ss a");
	
	
	public static String GetCurrentZoneTime() {
		ZonedDateTime now = ZonedDateTime.now();
		return now.format(dtf).toString();
	}
	
	public static String ConvertFromLocalDateTime(LocalDateTime date) {
		return date.format(dtf_local).toString();
	}
	
	

}
