package zm.gov.moh.core.repository.database.entity.fts;

import androidx.room.*;

@Entity(tableName = "client_name_fts")
 @Fts4
public class ClientNameFts {

       @PrimaryKey(autoGenerate = true)
       @ColumnInfo(name = "rowid")
       private long rowId;
       private String name;
       private long id;

       public ClientNameFts(long rowId, String name, long id) {
           this.name = name;
           this.id = id;
       }

       public String getName() {
           return name;
       }

    public long getRowId() {
        return rowId;
    }

    public long getId() {
        return id;
    }
}