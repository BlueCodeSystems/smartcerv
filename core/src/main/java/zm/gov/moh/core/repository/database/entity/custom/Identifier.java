package zm.gov.moh.core.repository.database.entity.custom;


import com.squareup.moshi.Json;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Identifier {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "identifier_id")
    @Json(name = "identifier_id")
    private long identifierId;
    private String identifier;
    private short assigned;

    public long getIdentifierId() {
        return identifierId;
    }

    public Identifier(String identifier){

        this.identifier = identifier;
        this.assigned = 0;
    }

    public void setIdentifierId(long identifierId) {
        this.identifierId = identifierId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public short getAssigned() {
        return assigned;
    }

    public void setAssigned(short assigned) {
        this.assigned = assigned;
    }

    public void markAsAssigned(){
        this.assigned = 1;
    }
}