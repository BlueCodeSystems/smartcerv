/*package zm.gov.moh.core;

import android.provider.Telephony;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import androidx.test.InstrumentationRegistry;
import zm.gov.moh.core.mocks.DatabaseMock;
import zm.gov.moh.core.model.Visit;

import static junit.framework.TestCase.assertNotNull;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
*//*
@RunWith(JUnit4.class)
@LargeTest
public class SQLiteDBTest {

	private DatabaseMock mDatabase;
	private DatabaseMock visitDaoMock;

	@Before
	public void setUp(){
		mDatabase = new DatabaseMock(InstrumentationRegistry.getContext());
		mDatabase.isOpen();
	}

	@After
	public void finish() {
		mDatabase.close();
	}

	@Test
	public void testPreConditions() {
		assertNotNull(mDatabase);
	}

	@Test
	public void testShouldAddExpenseType() throws Exception {
		mDatabase.createVisit("VISIT1", 1.2);
		List<Visit> visits = mDatabase.getAllVisits();

		assertThat(visits.size(), is(1));
		assertTrue(visits.get(0).toString().equals("VISIT1"));
		assertTrue(visits.get(0).getValue().equals(1.2));
	}

	@Test
	public void testDeleteAll() {
		mDatabase.deleteAll();
		List<Visit> visits = mDatabase.getAllVisits();

		assertThat(visits.size(), is(0));
	}

	@Test
	public void testDeleteOnlyOne() {
		mDatabase.createVisit("VISIT", 1.2);
		List<Visit> visits = mDatabase.getAllVisit();

		assertThat(visits.size(), is(1));

		mDatabase.deleteVisit(visits.get(0));
		visits = mDataSource.getAllVisit();

		assertThat(visits.size(), is(0));
	}

	@Test
	public void testAddAndDelete() {
		mDatabase.deleteAll();
		mDatabase.createRate("VISIT1", 1.2);
		mDatabase.createRate("VISIT2", 1.993);
		mDatabase.createRate("VISIT3", 1.66);

		List<Visit> visits = mDatabase.getAllVisits();
		assertThat(visits.size(), is(3));

		mDatabase.deleteVisit(visits.get(0));
		mDatabase.deleteVisit(visits.get(1));

		visits = mDatabase.getAllVisits();
		assertThat(visits.size(), is(1));
	}
}*/