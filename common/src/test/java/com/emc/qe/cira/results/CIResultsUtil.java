package com.emc.qe.cira.results;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CIResultsUtil {

	public static Date parseDate(String dateAsString) throws IllegalArgumentException {
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss yyyy", Locale.ENGLISH);
		Date result = null;
		try {
			result = df.parse(dateAsString);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Unable to parse the date " + dateAsString);
		}
		return result;
	}

	public static Map<TimeUnit, Long> parseDate(long diffInMillies) {
		List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
		Collections.reverse(units);
		Map<TimeUnit, Long> result = new LinkedHashMap<TimeUnit, Long>();
		long milliesRest = diffInMillies;
		for (TimeUnit unit : units) {
			long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
			long diffInMilliesForUnit = unit.toMillis(diff);
			milliesRest = milliesRest - diffInMilliesForUnit;
			result.put(unit, diff);
		}
		return result;
	}
}
