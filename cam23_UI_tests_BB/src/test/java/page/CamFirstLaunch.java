package page;

import static util.Helpers.find;
import static util.Helpers.swipe;
import static util.Helpers.tap;

/** Page object for the camera preview on the first camera launch **/
public abstract class CamFirstLaunch {

    /** Verify the location message has loaded.
     *  Click the 'Yes' button.
     *  Verify the Intel RealSense Tip has loaded. **/
    public static void locationYesClick() {
    	locationLoaded();
        find("Yes").click();
        realSenseMsgLoaded();
    }
    
    /** Verify the Intel RealSense Tip has loaded.
     *  Then click on 'Yes'. 
     **/
    public static void tipYesClick() {
    	realSenseMsgLoaded();
        find("Yes").click();
    }
    
    /** Verify the Intel RealSense Tip message has loaded.
     *  Click the 'Skip' button.
     *  Verify the Intel RealSense Tip has loaded. 
     * @throws InterruptedException **/
    public static void tipSkipClick() throws InterruptedException {
    	realSenseMsgLoaded();
        find("Skip").click();
        DepthCamPreview.loaded();
    }

    /** Verify the location message has loaded,
	 *  using a check for the text 'location' on the 'Remember photo
	 *  location?' message.**/
    public static void locationLoaded() {
        find("location");
    }
	
	/** Verify the RealSense message has loaded by checking for 
	 *  presence of the word RealSense**/
    public static void realSenseMsgLoaded() {
        find("RealSense");
    }
	
}