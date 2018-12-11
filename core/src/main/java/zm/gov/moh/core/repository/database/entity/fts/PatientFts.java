package zm.gov.moh.core.repository.database.entity.fts;

import androidx.room.*;
@Entity
 //@Fts4
 public class PatientFts {
   @PrimaryKey
   @ColumnInfo(name = "rowid")
   private final int rowId;
   private final String subject;
   private final String body;

   public PatientFts(int rowId, String subject, String body) {
       this.rowId = rowId;
       this.subject = subject;
       this.body = body;
   }

   public int getRowId() {
       return rowId;
   }
   public String getSubject() {
       return subject;
   }
   public String getBody() {
       return body;
   }
 }