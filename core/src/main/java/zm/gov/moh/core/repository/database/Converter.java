package zm.gov.moh.core.repository.database;

import androidx.room.TypeConverter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;


public class Converter {

    @TypeConverter
    public LocalDateTime fromTimestamp(String datetime) {

        try {
            return LocalDateTime.parse(datetime, DateTimeFormatter.ISO_DATE_TIME);
        }
        catch (Exception e){
            return null;
        }
    }

    @TypeConverter
    public String dateToTimestamp(LocalDateTime datetime) {
        if (datetime == null) {
            return null;
        } else
            return datetime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    //Date time
    @TypeConverter
    public LocalDate fromDate(String date) {

        return date == null ? null : LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @TypeConverter
    public String toDate(LocalDate date) {

        return date == null ? null : date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @TypeConverter
    public String toTimeStampISO(LocalTime zonedDateTime){

        if(zonedDateTime != null)
            return zonedDateTime.toString();
        return null;
    }

    /*@TypeConverter
    public LocalDateTime fromTimeStampISO(String localDatetime){

        return (localDatetime != null)? LocalDateTime.parse(localDatetime) : null;
    }*/

    @TypeConverter
    public String charToString(char character){

        return String.valueOf(character);
    }

    @TypeConverter
    public char StringToChar(String character){

        return character.charAt(0);
    }
}