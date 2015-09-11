package page;

import static util.Helpers.elementByID;
import static util.Helpers.find;
import static util.Helpers.waitForID;
import static util.Helpers.waitIDGone; 

/** Page object for the Mode Menu module. **/
public abstract class ModeMenu {	
	static String singleIcon = "com.intel.camera22:id/vertical_mode_image_t2i";
	static String singleCamSubMode = "com.intel.camera22:id/sub_mode_text";

	/** Tap on the Single Camera icon.**/
	public static void singleCamIconTap() {
		find("Single").click();
		waitIDGone(singleIcon);
		waitIDGone(singleCamSubMode);
	}
	
	/** Tap on the Depth Camera icon.**/
	public static void depthCamIconTap() {
		find("Depth Snapshot").click();
		waitIDGone(singleIcon);
	}
	
    /** Verify the Mode Menu is open by checking for 
	 *  presence of the Depth Snapshot **/
    public static void modeMenuLoaded() {
        find("Depth Snapshot");
    }	
}