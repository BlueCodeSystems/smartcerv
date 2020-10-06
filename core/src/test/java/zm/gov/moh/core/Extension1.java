package zm.gov.moh.core;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class Extension1 implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        beforeEachCallback(this);
    }

    private void beforeEachCallback(Extension1 extension1) {
    }

    @Override
    public void afterEach(ExtensionContext context) {
        afterEachCallback(this);
    }

    private void afterEachCallback(Extension1 extension1) {
    }
}