package zm.gov.moh.core;


import android.app.Application;
import android.content.Context;

import com.google.common.base.Predicate;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import androidx.room.Room;
import zm.gov.moh.core.mocks.DatabaseMock;
import zm.gov.moh.core.model.UserMock;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.UserDao;
import zm.gov.moh.core.repository.database.dao.domain.UserDaoMock;
import zm.gov.moh.core.repository.database.dao.domain.VisitDaoMock;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntityMock;


@RunWith(JUnit4.class)
public class EntityDBTest extends Application {
    private UserDaoMock userDaoMock;
    private UserDao userDao;
    private DatabaseMock db;
    private Database database;
    public Context ApplicationProvider;
    VisitDaoMock visitDaoMock;
    VisitEntityMock visitEntityMock;

    @Before
    public void createDb() {
        database = Room.inMemoryDatabaseBuilder(this, Database.class).build();
        visitDaoMock=database.visitDaoMock();
        AndroidThreeTen.init(this);
        visitEntityMock = new VisitEntityMock(100L,2L,120L,50L,3L);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {

        VisitEntityMock[] entities = {visitEntityMock};
       database.visitDaoMock().insert(entities);
        List<Long> ids =visitDaoMock.getIds();


    }


    private Object equalTo(UserMock userMock) {
        return null;
    }

    private void assertThat(UserMock userMock, Predicate<UserMock> equalTo) {
    }
}

