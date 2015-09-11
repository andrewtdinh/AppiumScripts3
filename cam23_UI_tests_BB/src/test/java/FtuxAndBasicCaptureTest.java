import util.AppTest;
import page.CamFirstLaunch;
import page.DepthCamPreview;
import page.SingleCamPreview;
import static util.Helpers.*;

public class FtuxAndBasicCaptureTest extends AppTest {
	static String locationMsg = "text(\"Remember photo location?\")";
    
	@org.junit.Test
    public void testFirstTimeUX() throws Exception {
    	String locationMsg = "text(\"Remember photo location?\")";
        if (found(locationMsg)){
        	CamFirstLaunch.locationLoaded();
        	CamFirstLaunch.locationYesClick();	
        	CamFirstLaunch.tipSkipClick();
        }
        else {
        	System.out.println("\n\tFirst Time UX does not apply!");
        }
    }
	
	@org.junit.Test
    public void testCaptureDepthXTimes() throws Exception {
		int xTimes = 10;
		int timeWaitBetweenCaptures = 800;
        if (found(locationMsg)){
        	CamFirstLaunch.locationLoaded();
        	CamFirstLaunch.locationYesClick();	
        	CamFirstLaunch.tipSkipClick();
        }
        else {
        	System.out.println("\n\tFirst Time UX does not apply!");
        	DepthCamPreview.loaded();
        }
        setWait(8);
        DepthCamPreview.capture(xTimes, timeWaitBetweenCaptures);
    }
	
	@org.junit.Test
    public void testCaptureSingleShotXTimes() throws Exception {
		int xTimes = 10;
		int timeWaitBetweenCaptures = 800;
    	
        if (found(locationMsg)){
        	CamFirstLaunch.locationLoaded();
        	CamFirstLaunch.locationYesClick();	
        	CamFirstLaunch.tipSkipClick();
        }
        else {
        	System.out.println("\n\tFirst Time UX does not apply!");
        	DepthCamPreview.loaded();
        }
        setWait(5);
        DepthCamPreview.switchToSingleMode("back");
        SingleCamPreview.capture(xTimes, timeWaitBetweenCaptures);
    }
}