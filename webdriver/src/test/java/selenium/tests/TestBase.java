package selenium.tests;

import org.junit.After;
import org.junit.Before;
import selenium.app.Application;

public class TestBase {

    public Application app;

    @Before
    public void start() {
        app = new Application();
    }

    @After
    public void stop() {
        app.quit();
    }

}
