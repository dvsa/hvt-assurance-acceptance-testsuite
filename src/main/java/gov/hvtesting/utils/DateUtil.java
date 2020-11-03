package gov.hvtesting.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    public Date extractDate(String input, String regex, String outputFormat) throws ParseException {
        Date foundDate = null;
        Matcher m = Pattern.compile(regex).matcher(input);
        if (m.find()) {
            foundDate = new SimpleDateFormat(outputFormat).parse(m.group(1));
        }
        return foundDate;
    }

    public LocalDate getNextMonday() {
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        return nextMonday;
    }

    public Date getUtcNow() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date expectedLastUpdatedOn = localDateFormat.parse(simpleDateFormat.format(new Date()));
        return expectedLastUpdatedOn;
    }
}
