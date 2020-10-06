package zm.gov.moh.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * Abstract base class for tests that use the database.
 */
abstract class AbstractDatabaseTests {

    @BeforeAll
    static void createDatabase() {
        beforeAllMethod(AbstractDatabaseTests.class.getSimpleName() + ".createDatabase()");
    }

    static void beforeAllMethod(String s) {
    }

    @BeforeEach
    void connectToDatabase() {
        beforeEachMethod(AbstractDatabaseTests.class.getSimpleName() + ".connectToDatabase()");
    }

    protected abstract void beforeEachMethod(String s);

    @AfterEach
    void disconnectFromDatabase() {
        afterEachMethod(AbstractDatabaseTests.class.getSimpleName() + ".disconnectFromDatabase()");
    }

    protected abstract void afterEachMethod(String s);

    @AfterAll
    static void destroyDatabase() {
        afterAllMethod(AbstractDatabaseTests.class.getSimpleName() + ".destroyDatabase()");
    }

    private static void afterAllMethod(String s) {
    }

}