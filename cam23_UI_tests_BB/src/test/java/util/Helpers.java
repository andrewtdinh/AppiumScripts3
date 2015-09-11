package util;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.*;


public abstract class Helpers {

	public static AndroidDriver driver;
	public static URL serverAddress;
	private static WebDriverWait driverWait;

	/**
	 * Initialize the webdriver. Must be called before using any helper methods. *
	 */
	public static void init(AndroidDriver webDriver, URL driverServerAddress) {
		driver = webDriver;
		serverAddress = driverServerAddress;
		int timeoutInSeconds = 30;
		// must wait at least 60 seconds for running on Sauce.
		// waiting for 30 seconds works locally however it fails on Sauce.
		driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
	}

	/**
	 * Set implicit wait in seconds *
	 */
	public static void setWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	/**
	 * Return an element by locator *
	 */
	public static WebElement element(By locator) {
		return driver.findElement(locator);
	}
  
	/**
	 * Return an element by locator *
	 */
	public static WebElement elementByID(String locator) {
		return driver.findElement(By.id(locator));
	}
	/**
	 * Return a list of elements by locator *
	 */
	public static List<WebElement> elements(By locator) {
		return driver.findElements(locator);
	}  
  
	/** The three methods below seem to not be working.  
	 * Method to switch to WebView
	 */
	public static void switchToWebView(){
		Set contexts = driver.getContextHandles();
		if (contexts.contains("WEBVIEW_com.intel.media.DepthTutor")){
			System.out.println(contexts);
			driver.context("WEBVIEW_com.intel.media.DepthTutor").switchTo();
		}
	}
  
	/**
	 * Method to switch to Native app
	 */
	public static void switchToNative(){
		Set contexts = driver.getContextHandles();
		for (Object s:contexts){
			if (s.toString() == "NATIVE_APP"){
				System.out.println("Switching to " +s.toString());
				driver.context(s.toString());
			}
		}
	}
  
	/**
	 * Method to find the current context and return it as an object
	 */
	public static Object getContext(){
		Set contexts = driver.getContextHandles();
		Iterator iter = contexts.iterator();
		Object context = iter.next();
		return context;
	}

	/**
	 * Press the back button *
	 */
	public static void back() {
		driver.navigate().back();
	}
  
	/**
	 *  Method to swipe the screen given the starting and ending coordinates in percentage of the screen
	 */
	public static void swipe(Double startX, Double startY, Double endX, Double endY) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", startX);
		swipeObject.put("startY", startY);
		swipeObject.put("endX", endX);
		swipeObject.put("endY", endY);
		swipeObject.put("duration", 0.4);
		js.executeScript("mobile: swipe", swipeObject);
	}

	/**
	 * Method to tap on a coordinate in pixels
	 */
	public static void tap(Integer x_coordinate, Integer y_coordinate) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Integer> tapObject = new HashMap<String, Integer>();
		tapObject.put("x", x_coordinate); // in pixels from left
		tapObject.put("y", y_coordinate); // in pixels from top
		js.executeScript("mobile: tap", tapObject);
	}
	/**
	 *  Method to tap on a coordinate in percentage
	 */
	public static void tap(Double x_percent, Double y_percent) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> tapObject = new HashMap<String, Double>();
		tapObject.put("x", x_percent); // in percentage of the screen width from the left
		tapObject.put("y", y_percent); // in percentage of the screen height from the top
		js.executeScript("mobile: tap", tapObject);
	}  
  
	/**
	 * Return a list of elements by tag name *
	 */
	public static List<WebElement> tags(String tagName) {
		return elements(for_tags(tagName));
	}

	/**
	 * Return a tag name locator *
	 */
	public static By for_tags(String tagName) {
		return By.className(tagName);
	}

	/**
	 * Return a static text element by xpath index *
	 */
	public static WebElement s_text(int xpathIndex) {
		return element(for_text(xpathIndex));
	}

	/**
	 * Return a static text locator by xpath index *
	 */
	public static By for_text(int xpathIndex) {
		return By.xpath("//android.widget.TextView[" + xpathIndex + "]");
	}

	/**
	 * Return a static text element that contains text *
	 */
	public static WebElement text(String text) {
		return element(for_text(text));
	}

	/**
	 * Return a static text locator that contains text *
	 */
	public static By for_text(String text) {
		return By.xpath("//android.widget.TextView[contains(@text, '" + text + "')]");
	}

	/**
	 * Return a static text element by exact text *
	 */
	public static WebElement text_exact(String text) {
		return element(for_text_exact(text));
	}

	/**
	 * Return a static text locator by exact text *
	 */
	public static By for_text_exact(String text) {
		return By.xpath("//android.widget.TextView[@text='" + text + "']");
	}

	public static By for_find(String value) {
		return By.xpath("//*[@content-desc=\"" + value + "\" or @resource-id=\"" + value +
				"\" or @text=\"" + value + "\"] | //*[contains(translate(@content-desc,\"" + value +
				"\",\"" + value + "\"), \"" + value + "\") or contains(translate(@text,\"" + value +
				"\",\"" + value + "\"), \"" + value + "\") or @resource-id=\"" + value + "\"]");
	}

	public static WebElement find(String value) {
		return element(for_find(value));
	}
  
	public static boolean found(String locator) {
		setWait(10);
		return (!driver.findElementsByAndroidUIAutomator(locator).isEmpty()) ? true : false;
	}
  
	/**Find by UI Automator.  The string is of the forms:
	 * String switchMode = "description(\"Show switch camera mode list\")"; 
	 * String hdrON = "text(\"HDR\nON\")";
	 */
	public static WebElement findByAU(String locator) {
		setWait(10);
		return driver.findElementByAndroidUIAutomator(locator);
	}
  
	/**Find by ID.  The string is of the forms:
	 * String modePano = "com.intel.camera22:id/mode_wave_panorama";  
	 */
	public static WebElement findByID(String locator) {
		setWait(10);
		//return driver.findElement(By.id(locator));
		return driver.findElementByAccessibilityId(locator);
	}

	/**
	 * Method to put the driver to sleep for a number of mili-seconds
	 */
	public static void waitInMs(Integer milisec) {
		try {
			Thread.sleep(milisec);
		} catch(InterruptedException e) {}
	}  
	
	/**
	 * Wait until find the element with locator specified.
	 */
	public static void waitUntil(String locator) {
		driverWait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AndroidUIAutomator(locator)));
	}
  
	/**
	 * Wait 30 seconds for locator to find an element *
	 */
	public static WebElement wait(By locator) {
		return driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * Wait for locator to find an element using resource-id
	 */
	public static WebElement waitForID(String locator) {
		//return driverWait.until(ExpectedConditions.presenceOfElementLocated(By.id(locator))); 
		return driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
	}
  
	/**
	 * Wait 60 seconds for locator to find all elements *
	 */
	public static List<WebElement> waitAll(By locator) {
		return driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * Wait 60 seconds for locator to not find a visible element *
	 */
	public static boolean waitInvisible(By locator) {
		return driverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	/**
	 * Wait until an id is invisible
	 */
	public static boolean waitIDGone(String locator){
		return driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(locator)));
	}

	/**
	 * Return an element that contains name or text *
	 */
	public static WebElement scroll_to(String value) {
		return driver.scrollTo(value);
	}

	/**
	 * Return an element that exactly matches name or text *
	 */
	public static WebElement scroll_to_exact(String value) {
		return driver.scrollToExact(value);
	}

	/**
	 *  Method to find the number of files from a particular folder on the device.
	 */
	public static int findNumberOfFiles(String dFolderPath) throws InterruptedException {
		int numOfFiles = 0;
		Thread.sleep(500);
		try {
			ProcessBuilder process = new ProcessBuilder("adb", "shell", "ls", dFolderPath, "|", "wc", "-l");
			Process p = process.start();
			//Thread.sleep(5000);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			p.waitFor();
			while ((line = br.readLine()) != null) {
				if (!line.equals(""))
					numOfFiles = Integer.parseInt(line); 
			}
		} catch (Exception e){
			System.out.println(e);
		}
		return numOfFiles;
	}
	
	/**
	 *  Method to retrieve a certain value from the camera preference xml files
	 */
	public static String getValueFromXML(String fileName, String filterTerm, String searchPattern, Integer searchGroupIndex) throws InterruptedException {
		String xmlPath = "/data/data/com.intel.camera22/shared_prefs/";
		String xmlLine = "";
		Thread.sleep(1000);
		try {
			ProcessBuilder process = new ProcessBuilder("adb", "shell", "cat", xmlPath + fileName, "|", "grep", filterTerm);
			Process p = process.start();
			Thread.sleep(5000);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			p.waitFor();
			while ((line = br.readLine()) != null) {
				if (!line.equals(""))
					xmlLine = line.toString(); 
			}
		} catch (Exception e){
			System.out.println(e);
		}
		Pattern compiledPattern = Pattern.compile(searchPattern);
		Matcher m = compiledPattern.matcher(xmlLine);
		String xmlValue = "";
		if (m.find()) {
            xmlValue = m.group(searchGroupIndex).toString();
            //System.out.printf("The value for the '%s' filter is '%s'.\n", filterTerm, xmlValue);	//Enable for debugging.
        }
        else {
            System.out.println("Could not retrieve a value from the camera preference xml files!");
            assertTrue(false);
        }
		return xmlValue;
	}
	
	/**
	 *  Method to get the current camera mode */
	public static String getCameraMode() throws InterruptedException{
		String modeBtnLocator = "description(\"Show switch camera mode list\")";
		String hdrON = "text(\"HDR\nON\")";
		String smileON = "text(\"Smile\nON\")";
		String currentMode = "";
		Thread.sleep(500);
		String valueString = getValueFromXML("mode_selected.xml", "Mode", "value=.(\\d+).", 1);
		Integer valueInt = Integer.parseInt(valueString); 
		switch(valueInt){
			case 1:
				findByAU(modeBtnLocator).click();
				Thread.sleep(400);
				if (found(hdrON)) {
					System.out.println("\tCurrent mode is HDR.");
					currentMode = "hdr";
					driver.tap(1, 1300, 800, 500);;
					break;
				}
				else if (found(smileON)) {
					System.out.println("\tCurrent mode is Smile.");
					currentMode = "smile";
					driver.tap(1, 1300, 800, 500);
					break;
				}
				else {
					System.out.println("\tCurrent mode is Single Shot.");
					currentMode = "single";
					driver.tap(1, 1300, 800, 500);
					break;
				}
			case 5:
				System.out.println("\tCurrent mode is Burst.");
				currentMode = "burst";
				break;
			case 9:
				System.out.println("\tCurrent mode is Video.");
				currentMode = "video";
				break;	
			case 11:
				System.out.println("\tCurrent mode is Panorama.");
				currentMode = "pano";
				break;
			case 12:
				System.out.println("\tCurrent mode is Depth.");
				currentMode = "depth";
				break;
			default:
				System.out.println("\tCurrent camera mode is not recognized.");
				assertTrue(false);
		}
		return currentMode;
	}
	
	/**
	 * Method to get the camera's facing direction.*/
	public static String getCameraDirection() throws InterruptedException{
		String currentDirection = "";
		Thread.sleep(500);
		String valueString = getValueFromXML("com.intel.camera22_preferences_0.xml ", "id_key", ">(\\d+)<", 1);
		Integer valueInt = Integer.parseInt(valueString);
		switch(valueInt){
			case 0:
				System.out.println("Camera is back-facing.");
				currentDirection = "back";
				break;
			case 1:
				System.out.println("Camera is front-facing.");
				currentDirection = "front";
				break;
			default:
				System.out.println("Current camera mode is not recognized.");
				assertTrue(false);
		}
		return currentDirection;
	}	
}