package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

@Entity(tableName = "location_tag_map")
public class LocationTagMap {

    @PrimaryKey
    public long location_id;
    public long location_tag_id;
}