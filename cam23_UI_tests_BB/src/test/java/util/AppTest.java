//Some items in this files are artifacts created by copying the Appium Tutorial Files.
package util;

import page.CamFirstLaunch;
import page.DepthCamPreview;
import page.HomePage;
import page.RealSenseTip_FirstPage;
import page.RealSenseTip_SecondPage;
import page.RealSenseTip_ThirdPage;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;

import io.appium.java_client.android.AndroidDriver;

import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static util.Helpers.driver;

public class AppTest implements SauceOnDemandSessionIdProvider {

    static {
        // Disable annoying cookie warnings.
        // WARNING: Invalid cookie header
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }

    /** Page object references. Allows using simpler names, like 'home' instead of 'HomePage' **/
    protected HomePage home;
    protected CamFirstLaunch ftux;
    protected DepthCamPreview depth;

    /** wait wraps Helpers.wait **/
    public static WebElement wait(By locator) {
        return Helpers.wait(locator);
    }

    private boolean runOnSauce = System.getProperty("sauce") != null;

    /** Authenticate to Sauce with environment variables SAUCE_USER_NAME and SAUCE_API_KEY **/
    private SauceOnDemandAuthentication auth = new SauceOnDemandAuthentication();

    /** Report pass/fail to Sauce Labs **/
    // false to silence Sauce connect messages.
    public @Rule
    SauceOnDemandTestWatcher reportToSauce = new SauceOnDemandTestWatcher(this, auth, false);

    @Rule
    public TestRule printTests = new TestWatcher() {
        protected void starting(Description description) {
            System.out.print("  test: " + description.getMethodName());
        }

        protected void finished(Description description) {
            final String session = getSessionId();

            if (session != null) {
                System.out.println(" " + "https://saucelabs.com/tests/" + session);
            } else {
                System.out.println();
            }
        }
    };

    private String sessionId;

    /** Keep the same date prefix to identify job sets. **/
    private static Date date = new Date();

    /** Run before each test **/
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.1.0");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "BlackBurn");
        capabilities.setCapability("platformVersion", "5.0");
		capabilities.setCapability("appPackage", "com.intel.camera22");
		capabilities.setCapability("appActivity", ".Camera");
		capabilities.setCapability("deviceOrientation", "landscape");	//new

        URL serverAddress;
		//The four lines below need to be enabled with the apk needs to be installed on the device.
        //String userDir = System.getProperty("user.dir");
        //String localApp = "api.apk";
		//String appPath = Paths.get(userDir, localApp).toAbsolutePath().toString();
		//capabilities.setCapability("app", appPath);
		serverAddress = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver(serverAddress, capabilities);

        sessionId = driver.getSessionId().toString();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Helpers.init(driver, serverAddress);
    }

    /** Run after each test **/
    @After
    public void tearDown() throws Exception {
        if (driver != null) {
			driver.closeApp();
			driver.quit();
		}
    }

    /** If we're not on Sauce then return null otherwise SauceOnDemandTestWatcher will error. **/
    public String getSessionId() {
        return runOnSauce ? sessionId : null;
    }
}