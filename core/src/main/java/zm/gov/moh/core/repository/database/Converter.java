package zm.gov.moh.core.repository.database;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;


public class Converter {

    @TypeConverter
    public ZonedDateTime fromTimestamp(String datetime) {
        
        return datetime == null ? null : ZonedDateTime.parse(datetime);
    }

    @TypeConverter
    public String dateToTimestamp(ZonedDateTime datetime) {
        if (datetime == null) {
            return null;
        } else {

          DateTimeFormatter formatter =  DateTimeFormatter.ISO_DATE_TIME;

          return formatter.format(datetime);
        }
    }
}

/*
* TimeZone tz = TimeZone.getTimeZone("UTC");
DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
df.setTimeZone(tz);
String nowAsISO = df.format(new Date());
* */