package wdcore.selenium.base;

import java.io.File;

//import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wdcore.selenium.base.map.BrowsersMap;

public class FFProfileSetup {
	// private static final Logger log = Logger.getLogger(Application.class);
	protected static Logger log = LoggerFactory.getLogger(FFProfileSetup.class);

	// static int ff_version = 10;
	public static FirefoxProfile getFFProfile(String testDir, boolean debugMode) {
		FirefoxProfile profile = new FirefoxProfile();
		// profile.setEnableNativeEvents(false);
		 profile.setPreference("browser.startup.page", 0);
		 profile.setPreference("startup.homepage_welcome_url", "about:");
		 profile.setPreference("startup.homepage_override_url", "about:");
		 profile.setPreference("browser.startup.homepage_override.mstone",
		 "rv:"
		 + "99.9.9");
		
		 /*
		  * он будет ждать ('complete' == readyState || 'interactive' == readyState)
[13:51:54] Alexei Barantsev: где readyState = window.document.readyState
[13:52:43] Alexei Barantsev: обычная стратегия ждёть complete
[13:53:00] Alexei Barantsev: а эта ждёт либо complete, либо interactive
[13:53:39] Alexei Barantsev: interactive наступает обычно раньше, это состояние, когда пользователю уже показана страница и он может с ней взаимодействовать, хотя загрузка ещё продолжается
 */
//		 profile.setPreference("webdriver.load.strategy", "unstable");

		 if(testDir!=null){
		// Automatic save folder
		File extension_asf = new File(BrowsersMap.FLD_FF_EXTENSIONS
				+ "automatic_save_folder-1.0.4.xpi");
		
//		if(!extension_asf.exists())
//			 extension_asf = new File(BrowsersMap.FLD_FF_EXTENSIONS1
//					+ "automatic_save_folder-1.0.4.xpi");
		
		try {
			log.debug("Adding extension " +extension_asf.getAbsolutePath());
			profile.addExtension(extension_asf);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("extensions.asf.savetype", 1);
			profile.setPreference("extensions.asf.defaultfolder",
					testDir);
			log.debug("Browser will download to  " + testDir);
			profile.setPreference("extensions.asf.dialogForceRadio", true);
			profile.setPreference("extensions.asf.dialogaccept", true);
			profile.setPreference("extensions.asf.version", "1.0.4");

		} catch (Exception e) {
			log.warn("WARNING! Could not add extension file "
					+ extension_asf.getAbsolutePath() + e.getMessage());
		}
		 }
		if (debugMode) {
			//
			// Firebug + firefinder_for_firebug
			//

			File extension_firebug = new File(BrowsersMap.FLD_FF_EXTENSIONS
					+ "firebug-1.11.2-fx.xpi");
			File extension_firefinder = new File(
					BrowsersMap.FLD_FF_EXTENSIONS
							+ "firefinder_for_firebug-1.2.5-fx.xpi");
			
//			if(!extension_firebug.exists()){
//				extension_firebug = new File(BrowsersMap.FLD_FF_EXTENSIONS1
//						+ "firebug-1.9.2-fx.xpi");
//				extension_firefinder = new File(
//						BrowsersMap.FLD_FF_EXTENSIONS1
//								+ "firefinder_for_firebug-1.2.2-fx.xpi");
//			}

			try {
				log.debug("Adding extension " +extension_firebug.getAbsolutePath());
				profile.addExtension(extension_firebug);
				profile.setPreference("extensions.firebug.currentVersion",
						"1.11.2");
				
				log.trace("Adding extension " +extension_firefinder);
				profile.addExtension(extension_firefinder);

				

			} catch (Exception e) {
				log.warn("WARNING! Could not add extension file "
						+ extension_firebug.getAbsolutePath());
			}
		}

	
		return profile;

	}

}
