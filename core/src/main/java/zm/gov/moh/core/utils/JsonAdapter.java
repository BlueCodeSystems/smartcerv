package zm.gov.moh.core.utils;

import androidx.room.TypeConverter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.ToJson;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class JsonAdapter {

    @ToJson
    public String toTimeStampISO(ZonedDateTime zonedDateTime){

        return zonedDateTime.toString();
    }

    @FromJson
    public ZonedDateTime fromTimeStampISO(String zonedDateTime){

        return ZonedDateTime.parse(zonedDateTime);
    }

    @ToJson
    public String StringTolong(long value){

        return String.valueOf(value);
    }

    @FromJson
    public Long longToString(String value){

        long v = (!value.equals(JsonReader.Token.NULL.toString()))? Long.valueOf(value):0l;

        return v;
    }

    //Date time
    @FromJson
    public LocalDate fromDate(String date) {

        return date == null ? null : LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @ToJson
    public String toDate(LocalDate date) {

        return date == null ? null : date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }



    @ToJson
    public String charToString(char character){

        return String.valueOf(character);
    }

    @FromJson
    public char StringToChar(String character){

        return character.charAt(0);
    }
}
