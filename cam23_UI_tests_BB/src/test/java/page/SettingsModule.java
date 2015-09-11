package page;

import static util.Helpers.*;

/** Page object for the Settings module.  This is a basic one so other page object classes can extend it **/
public abstract class SettingsModule {	

	/** Scrolling to the Switch Camera section after the Settings menu is open **/
    public static void scrollTo(String settingNameString) {
        scroll_to(settingNameString);
        find(settingNameString);
    }
    
	/** Tap on the Back Camera toggle icon **/
    public static void tapBackCamIcon() {
        scroll_to("Switch Camera");
        find("Switch Camera");		//Not sure how to locate the Back Camera Switch icon
    }

	/** Tap on the Front Camera toggle icon **/
    public static void tapFrontCamIcon() {
        scroll_to("Switch Camera");
        find("Switch Camera");		//Not sure how to locate the Front Camera Switch icon
    }
	
    /** Verify the Settings is open by checking for 
	 *  presence of the settings_list_view **/
    public static void settingsLoaded() {
        findByID("com.intel.camera22:id/setting_listview");
    }	
}