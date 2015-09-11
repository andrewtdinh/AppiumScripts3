package page;

import static util.Helpers.find;

/** Page object for the accessibility page **/
public abstract class AccessibilityPage {

    /** Verify the accessibility page has loaded **/
    public static void loaded() {
        find("Accessibility Node Provider");
    }
}