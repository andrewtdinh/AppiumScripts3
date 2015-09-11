package page;

import static util.Helpers.findByID;
import static util.Helpers.find;
import static util.Helpers.setWait;
import static util.Helpers.tags;
import static util.Helpers.switchToWebView;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

/** Page object for the first page of Intel RealSense Tip on the first camera launch **/
public abstract class RealSenseTip_FirstPage {	
	static String nextBtn = "com.intel.media.DepthTutor:id/button01";
	//static String nextBtn = "button01";
    /** Verify the first page of the RealSense Tip has loaded.
     *  Click the right arrow ('Next' button) to go to the next page
     *  Verify the Intel RealSense Tip 2nd page has loaded. **/
    public static void nextBtnClick() {
    	loaded();
    	findByID(nextBtn).click();
    	RealSenseTip_SecondPage.loaded();
    }
    
	/** Verify the first RealSense Tip page has loaded by checking for 
	 *  presence of the nextBtn **/
    public static void loaded() {
    	switchToWebView();
    	find(nextBtn);
    }
	
}