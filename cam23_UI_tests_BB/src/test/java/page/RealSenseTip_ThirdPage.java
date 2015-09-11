package page;

import static util.Helpers.find;
import static util.Helpers.findByID;

/** Page object for the second page of Intel RealSense Tip on the first camera launch **/
public abstract class RealSenseTip_ThirdPage {	
	static String nextBtn = "com.intel.media.DepthTutor:id/button01";

    /** Verify the second page of the RealSense Tip has loaded.
     *  Click the right arrow ('Next' button) to go to the next page
     *  Verify the Intel RealSense Tip 3nd page has loaded. **/
    public static void gotItClick() {
    	loaded();
    	findByID(nextBtn).click();
    }
    
    /** Verify the third RealSense Tip page has loaded by checking for 
	 *  presence of the nextBtn **/
    public static void loaded() {
        find("Got it");
    }	
}