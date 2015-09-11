package page;

import static org.junit.Assert.*;
import static util.Helpers.*;
import util.Helpers;

/** Page object for Depth Camera Preview page object **/
public abstract class DepthCamPreview extends ModeMenu {
	static String clickableShutterBtn = "description(\"Shutter button\").clickable(true).enabled(true)";
	static String cam0Preview = "resourceId(\"com.intel.camera22:id/vice_camera_image_view0\")";
	static String cam1Preview = "resourceId(\"com.intel.camera22:id/vice_camera_image_view1\")";
	static String mostRecentThumbnail = "com.intel.camera22:id/thumbnail";
	static String modeBtn = "com.intel.camera22:id/mode_button";
	
	/** 
	 *  Tap on the Shutter button once.  Look until the Shutter button
	 *  becomes available again.  Also verify the most recent photo thumbnail is there.**/
	public static void shutterTap() {
		waitUntil(clickableShutterBtn);
		findByAU(clickableShutterBtn).click();
		waitForID(mostRecentThumbnail);
	}
	
	/** Wait for the Mode Button to become available.
	 *  Tap on the Mode Button once.  Look until the Mode Menu is loaded.**/
	public static void modeBtnTap() {
		//waitUntil(modeBtn);
		waitForID(modeBtn);
		elementByID(modeBtn).click();
		modeMenuLoaded();
	}
	
	/** 
	 *  Capture the xTimes amount of depth images**/
	public static void capture(int xTimes, int mSecBetweenCaptures) throws InterruptedException{
		String pathTo100ANDRO = "/sdcard/DCIM/100ANDRO";
		
		int filesPreCapture = findNumberOfFiles(pathTo100ANDRO);
		System.out.println("\tCapturing image(s):");
		System.out.print("\t");
		for (int n = 0; n < xTimes; n++) {
			shutterTap();
			waitInMs(mSecBetweenCaptures);
			System.out.printf("..%d", n+1);
        }
		waitInMs(2000);
		int filesPostCapture = findNumberOfFiles(pathTo100ANDRO);
		int filesCaptured = filesPostCapture - filesPreCapture;
		if (filesCaptured == xTimes)
			System.out.printf("\n\t'%d' Depth images were captured out of the specified number of '%d'!\n", filesCaptured, xTimes);
		else {
			System.out.printf("\n\t'%d' Depth images were captured out of the specified number of '%d'.  Capture failed!!\n", filesCaptured, xTimes);
			assertTrue(false);
		}
	}
    
	/** 
	 *  Tap on the Settings icon
	 *  Verify the presence of . **/
	public static void settingsTap() {
		find("camera_settings").click();
		SettingsModule.settingsLoaded();
	}
    
    /**Method to toggle camera direction.
     * This method's completion is pending the correct implementation of the tapBackCamIcon
     * and the tapFrontCamIcon from SettingsModule.    
     * @throws InterruptedException 
     */
    public static void toggleCamDirection(String camDirection) throws InterruptedException {
    	settingsTap();
    	SettingsModule.scrollTo("Switch Camera");	
    	if (camDirection == "back") {
    		SettingsModule.tapBackCamIcon();
    		assertTrue(Helpers.getCameraDirection() == camDirection);
    	}
    	else if (camDirection == "front") {
    		SettingsModule.tapFrontCamIcon();
    		assertTrue(Helpers.getCameraDirection() == camDirection);
    	}
    }
    
    /**Method to switch to back-facing Single Shot shooting mode
     * First the camera is switched to Depth mode to make sure camera shooting direction is back-facing.
     * Then camera is switched to Single Shot shooting mode.  If the direction is to be front-facing, 
     * the camera direction is switched.  Then the shooting mode and the camera-facing
     * direction are checked to make sure they are correct as verification steps.  Switching to
     * front camera is not functional because of the toggleCamDirection's dependency. 
     * @throws InterruptedException 
     */
    public static void switchToSingleMode(String camDirection) throws InterruptedException{
    	modeBtnTap();
    	singleCamIconTap();
    	SingleCamPreview.loaded();
    	if (camDirection == "front"){
    		System.out.print("\n\tThe switch to front camera is not yet implemented!!");
    	}
    }
    
    /** Verify the Depth Camera Preview page loaded by checking for 
	 *  presence of the Shutter button 
     * @throws InterruptedException **/
    public static void loaded() throws InterruptedException {
    	findByAU(clickableShutterBtn);
    	findByAU(cam0Preview);
    	findByAU(cam1Preview);
    }	
}