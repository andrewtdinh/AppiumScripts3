import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import util.AppTest;
import static util.Helpers.*;
import page.CamFirstLaunch;
import page.DepthCamPreview;
import page.SingleCamPreview;


public class OneTest extends AppTest {
	static String locationMsg = "text(\"Remember photo location?\")";
	
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