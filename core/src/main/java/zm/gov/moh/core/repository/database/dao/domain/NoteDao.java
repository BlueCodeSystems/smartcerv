package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Note;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    Note getNote();

    @Insert
    void insert(Note note);
}
