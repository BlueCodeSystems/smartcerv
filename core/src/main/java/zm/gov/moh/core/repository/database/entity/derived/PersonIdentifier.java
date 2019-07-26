package zm.gov.moh.core.repository.database.entity.derived;

import com.squareup.moshi.Json;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import zm.gov.moh.core.repository.database.entity.domain.Person;

@Entity(tableName = "person_identifier")
public class PersonIdentifier {

    @PrimaryKey
    @ColumnInfo(name = "person_id")
    @Json(name = "person_id")
    private long personId;
    private String uuid;

    public long getPersonId() {
        return personId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public PersonIdentifier(Person person){

        this.personId = person.getPersonId();
        this.uuid = person.getUuid();
    }

    public PersonIdentifier(){

    }
}
