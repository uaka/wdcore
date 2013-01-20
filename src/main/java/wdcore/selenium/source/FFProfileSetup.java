package wdcore.selenium.source;

import java.io.File;

//import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wdcore.selenium.source.map.BrowsersMap;

public class FFProfileSetup {
	// private static final Logger log = Logger.getLogger(Application.class);
	protected static Logger log = LoggerFactory.getLogger(FFProfileSetup.class);

	// static int ff_version = 10;
	public static FirefoxProfile getFFProfile() {
		FirefoxProfile profile = new FirefoxProfile();
		// profile.setEnableNativeEvents(false);
		// profile.setPreference("browser.startup.page", 0);
		// profile.setPreference("startup.homepage_welcome_url", "about:");
		// profile.setPreference("startup.homepage_override_url", "about:");
		// profile.setPreference("browser.startup.homepage_override.mstone",
		// "rv:"
		// + ff_version);

		// Automatic save folder
		File extension_asf = new File(BrowsersMap.FLD_FF_EXTENSIONS
				+ "automatic_save_folder-1.0.4.xpi");
		try {
			log.debug("Adding extension " +extension_asf.getAbsolutePath());
			profile.addExtension(extension_asf);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("extensions.asf.savetype", 1);
			profile.setPreference("extensions.asf.defaultfolder",
					Environment.testDir);
			profile.setPreference("extensions.asf.dialogForceRadio", true);
			profile.setPreference("extensions.asf.dialogaccept", true);
			profile.setPreference("extensions.asf.version", "1.0.4");

		} catch (Exception e) {
			log.warn("WARNING! Could not add extension file "
					+ extension_asf.getAbsolutePath());
		}

		if (Environment.local) {
			//
			// Firebug + firefinder_for_firebug
			//

			File extension_firebug = new File(BrowsersMap.FLD_FF_EXTENSIONS
					+ "firebug-1.9.2-fx.xpi");

			try {
				log.debug("Adding extension " +extension_firebug.getAbsolutePath());
				profile.addExtension(extension_firebug);
				profile.setPreference("extensions.firebug.currentVersion",
						"1.9.2");
				File extension_firefinder = new File(
						BrowsersMap.FLD_FF_EXTENSIONS
								+ "firefinder_for_firebug-1.2.2-fx.xpi");
				log.trace("Adding extension " +extension_firefinder);
				profile.addExtension(extension_firefinder);

				// DesiredCapabilities capability =
				// DesiredCapabilities.firefox();
				// driver = new FirefoxDriver(profile);

			} catch (Exception e) {
				log.warn("WARNING! Could not add extension file "
						+ extension_firebug.getAbsolutePath());
			}
		}

		// else {
		// DesiredCapabilities capabilities = new DesiredCapabilities()
		// .firefox();
		//
		// capabilities.setCapability("FireFox", true); // setCapability(true);
		// capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		//
		// }
		return profile;

	}

}
