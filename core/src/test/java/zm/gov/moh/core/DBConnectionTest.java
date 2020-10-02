package zm.gov.moh.core;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DBConnectionTest {
	
	@InjectMocks private DBConnection dbConnection;
	@Mock private Connection mockConnection;
	@Mock private Statement mockStatement;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testMockDBConnection() throws Exception {	
		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
		int value = dbConnection.executeQuery("");
		Assert.assertEquals(value, 1);
		Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
		System.out.println("Database Connection Successful");
	}

}
