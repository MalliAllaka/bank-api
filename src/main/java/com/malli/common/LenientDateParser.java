package com.malli.common;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LenientDateParser extends PropertyEditorSupport {

	private static final List<String> formats = new ArrayList<String>();

	private static final Log log = LogFactory.getLog(LenientDateParser.class);

	private String outputFormat;

	static {
		formats.add("dd/MM/yyyy hh:mm a");
		formats.add("dd/MM/yyyy hh:mm:ss a");
		formats.add("dd-MM-yyyy-hh-mm-ss");
		formats.add("hh:mm a");
		formats.add("HH:mm");
		formats.add("dd/MM/yyyy");
		formats.add("dd-MM-yyyy H:mm a");
		formats.add("dd-MM-yyyy");
		formats.add("hh:mm a");
		formats.add("dd MMM yyyy");
		formats.add("MMM-yyyy HH:ss");
		formats.add("MM/yyyy");
		formats.add("MMM-yyyy");
		formats.add("MMM yyyy");
		formats.add("yyyy-MM-dd HH:mm:ss");
		
	}

	public LenientDateParser() {
	}

	public Date parse(String dateValue) {
		for (String candidateFormat : formats) {
			Date date = lenientParse(dateValue, candidateFormat);
			// if (date != null) {
			return date;
			// }
		}
		throw new RuntimeException("tried so many formats, non matched");
	}

	private Date lenientParse(String dateCandidate, String dateFormat) {
		try {
			return new SimpleDateFormat(dateFormat).parse(dateCandidate);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
			return;
		}
			
		DateTime dt = null;
		for (String format : formats) {

			try {

				dt = DateTime.parse(text.trim(), DateTimeFormat.forPattern(format));

				break;

			} catch (Exception e) {
				if (log.isDebugEnabled())
					log.debug(e, e);
			}
		}
//		if (dt != null)
		
			setValue(dt.toDate());
	}

	@Override
	public String getAsText() {
		Date date = (Date) getValue();

		if (date == null)
			return "";

		DateTimeFormatter f = DateTimeFormat.forPattern(outputFormat);

		return f.print(date.getTime());

	}

}
