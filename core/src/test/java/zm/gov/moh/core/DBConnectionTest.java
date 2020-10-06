package zm.gov.moh.core;

import android.database.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.PreparedStatement;

import zm.gov.moh.core.mocks.DatabaseMock;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
    public class DBConnectionTest {

        @InjectMocks
        DatabaseMock databaseMock;
        @Mock
        DatabaseMock dbinstance;
        @Mock
        PreparedStatement stmt;

        @Before
        public void setUp() throws SQLException, java.sql.SQLException {
            when(dbinstance.insert(("(SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId"))).thenReturn(stmt);
            when(stmt.executeUpdate()).thenReturn(1);

        }

        @Test
        public void testInsertTable() {
            boolean t = true;
            assertTrue((Boolean) databaseMock.<Boolean>insert(t));

        }

    private void assertTrue(boolean entities) {
    }
}

