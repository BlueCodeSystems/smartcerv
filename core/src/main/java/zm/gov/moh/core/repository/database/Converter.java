package zm.gov.moh.core.repository.database;

import androidx.room.TypeConverter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;


public class Converter {

    @TypeConverter
    public LocalDateTime fromTimestamp(String datetime) {
        
        return datetime == null ? null : LocalDateTime.parse(datetime);
    }

    @TypeConverter
    public String dateToTimestamp(LocalDateTime datetime) {
        if (datetime == null) {
            return null;
        } else
            return datetime.toString();
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
    public String toTimeStampISO(ZonedDateTime zonedDateTime){

        if(zonedDateTime != null)
            return zonedDateTime.toString();
        return null;
    }

    @TypeConverter
    public ZonedDateTime fromTimeStampISO(String zonedDateTime){

        if(zonedDateTime != null)
            return ZonedDateTime.parse(zonedDateTime);
        return null;
    }

    @TypeConverter
    public String charToString(char character){

        return String.valueOf(character);
    }

    @TypeConverter
    public char StringToChar(String character){

        return character.charAt(0);
    }
}