import util.AppiumTest;

import static util.Helpers.back;

public class PageObjectPatternTest extends AppiumTest {

    @org.junit.Test
    public void pageObject() throws Exception {
        home.accessibilityClick();
        back();

        home.animationClick();
        back();
    }
}