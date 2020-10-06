package zm.gov.moh.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Extension of {@link AbstractDatabaseTests} that inserts test data
 * into the database (after the database connection has been opened)
 * and deletes test data (before the database connection is closed).
 */
@ExtendWith({ Extension1.class, Extension2.class })
public class DatabaseTestsDemo extends AbstractDatabaseTests {

    @BeforeAll
    static void beforeAll() {
        beforeAllMethod(DatabaseTestsDemo.class.getSimpleName() + ".beforeAll()");
    }

    @BeforeEach
    void insertTestDataIntoDatabase() {
        beforeEachMethod(getClass().getSimpleName() + ".insertTestDataIntoDatabase()");
    }

    @Test
    void testDatabaseFunctionality() {
        testMethod(getClass().getSimpleName() + ".testDatabaseFunctionality()");
    }

    private void testMethod(String s) {
    }

    @AfterEach
    void deleteTestDataFromDatabase() {
        afterEachMethod(getClass().getSimpleName() + ".deleteTestDataFromDatabase()");
    }

    @AfterAll
    static void afterAll() {
        beforeAllMethod(DatabaseTestsDemo.class.getSimpleName() + ".afterAll()");
    }

    @Override
    protected void beforeEachMethod(String s) {
        
    }

    @Override
    protected void afterEachMethod(String s) {

    }
}