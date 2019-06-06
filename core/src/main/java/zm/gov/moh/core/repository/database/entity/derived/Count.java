package zm.gov.moh.core.repository.database.entity.derived;

import androidx.room.ColumnInfo;

import com.squareup.moshi.Json;

public class Count {
    @ColumnInfo(name = "count")
    @Json(name = "count")
    public long count;
}
