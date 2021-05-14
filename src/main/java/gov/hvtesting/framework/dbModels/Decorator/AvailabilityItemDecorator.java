package gov.hvtesting.framework.dbModels.Decorator;

import gov.hvtesting.framework.dbModels.AvailabilityItem;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class AvailabilityItemDecorator extends AvailabilityItem {

    /**
     * Setting out of date availability here ensures the ATF has label Not Known on the UI
     */
    public AvailabilityItem decorate(AvailabilityItem item) {
        item.setAvailable(true);
        item.setLastUpdated("2021-02-22T00:00:00.000Z");
        item.setEndDate("2021-03-22T00:00:00.000Z");
        item.setStartDate("2021-02-22T00:00:00.000Z");
        return item;
    }

    public AvailabilityItem decorate(AvailabilityItem item, Boolean isAvailable) {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime startDate = getStartDate(currentDate);

        item.setAvailable(isAvailable);
        item.setStartDate(formatDate(startDate));
        item.setEndDate(formatDate(startDate.plusMonths(1)));
        item.setLastUpdated(formatDate(LocalDateTime.now()));

        return item;
    }

    private static String formatDate(LocalDateTime d) {
        return DateTimeFormatter.ISO_INSTANT.format(d.toInstant(ZoneOffset.UTC));
    }

    private static LocalDateTime getStartDate(LocalDate now){
        LocalDate startDate;
        if(now.getDayOfWeek() != DayOfWeek.MONDAY){
            TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
            LocalDate startOfCurrentWeek = now.with(fieldISO, 1);
            startDate = startOfCurrentWeek.plusDays(7);
        }else{
            startDate = now;
        }
        return startDate.atStartOfDay();
    }
}

