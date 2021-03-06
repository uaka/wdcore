package wdcore.selenium.base;

import java.io.File;
import java.io.IOException;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Environment {
	// private static final Logger log =
	// Logger.getLogger(Environment.class);//Logger.getLogger(Environment.class);

	protected static Logger log = LoggerFactory.getLogger(Environment.class);

	// public static boolean local = getLocal();
	public static String appRoot = getAppRoot();
	// public static String testDir = getLocalAndTestDirectory();
	public static String seleniumServerUrl = System
			.getenv("SELENIUM_SERVER_URL");

	// private static final Logger log =
	// Logger.getLogger(Environment.class);//Logger.getLogger(Environment.class);

	// public static String seleniumServerUrl =
	// "http://172.16.0.14:4444/wd/hub"; //diveboard-pc Win7

	/*
	 * public static String getSeleniumServerUrl() {
	 * 
	 * if (seleniumServerUrl == null || seleniumServerUrl.equals("")) {
	 * seleniumServerUrl = "http://localhost:4444/wd/hub"; }
	 * 
	 * log.debug("running tests on env: " + seleniumServerUrl); return
	 * seleniumServerUrl; }
	 */
	public static String getAppRoot() {
		// if (local)
		// return System.getProperty("user.dir");
		//
		// else

		try {
			// String appRoot = (new File(".")).getCanonicalPath();
			appRoot = java.lang.System.getProperties().getProperty("appRoot");
			if (appRoot == null)
				appRoot = System.getProperty("user.dir");
			log.debug("Application root is: " + appRoot);
			return appRoot;

		} catch (Exception e) {
			log.warn("WARNING! could not get application root, set appRoot = ''");
		}
		return "";

	}

	// returnes true if runs on local and false if runs on tc.diveboard.com
	public static boolean getLocal() {

		if (seleniumServerUrl == null) {

			log.debug("Local = true");
			return true;
		} else {
			// appRoot =
			// java.lang.System.getProperties().getProperty("appRoot");

			log.debug("Local = false");
			return false;
		}
	}

	/*
	 * private static String getLocalAndTestDirectory() { try {
	 * 
	 * // String testDir = new java.io.File(".").getCanonicalPath() // +
	 * File.separator + "test_data" + File.separator; String testDir =
	 * getTestDir(); log.debug("Test dir: " + testDir); return (testDir);
	 * 
	 * } catch (Exception e) {
	 * log.warn("Warning! Could not specify test_directory!"); return null; }
	 * 
	 * // return test_directory; }
	 */

	public static String getTestDir() {
		if (isWindows())
			return "C:\\test_data\\";
		if (isUnix())
			return "~/test_data/";
		return File.separator + "test_data" + File.separator;

	}

	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}

	public static boolean isUnix() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}
}
