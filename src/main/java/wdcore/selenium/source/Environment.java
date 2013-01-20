package wdcore.selenium.source;

import java.io.File;
import java.io.IOException;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Environment {
	// private static final Logger log =
	// Logger.getLogger(Environment.class);//Logger.getLogger(Environment.class);

	protected static Logger log = LoggerFactory.getLogger(Environment.class);

	public static boolean local = getLocal();
	public static String appRoot = getAppRoot();
	public static String testDir = getLocalAndTestDirectory();
	public static String seleniumServerUrl = System
			.getenv("SELENIUM_SERVER_URL");

	// private static final Logger log =
	// Logger.getLogger(Environment.class);//Logger.getLogger(Environment.class);

	// public static String seleniumServerUrl =
	// "http://172.16.0.14:4444/wd/hub"; //diveboard-pc Win7

	public static String getSeleniumServerUrl() {

		if (seleniumServerUrl == null || seleniumServerUrl.equals("")) {
			seleniumServerUrl = "http://localhost";
		}

		log.debug("running tests on env: " + seleniumServerUrl);
		return seleniumServerUrl;
	}

	public static String getAppRoot() {
		if (local)
			return System.getProperty("user.dir");

		else

			try {
				return (new File(".")).getCanonicalPath();
			} catch (IOException e) {
				log.warn("WARNING! could not get application root, set appRoot = ''");
			}
		return "";

	}

	// returnes true if runs on local and false if runs on tc.diveboard.com
	public static boolean getLocal() {

		if (seleniumServerUrl == null || seleniumServerUrl.contains("local")) {

			log.debug("Local = true");
			return true;
		} else {
			// appRoot =
			// java.lang.System.getProperties().getProperty("appRoot");

			log.debug("Local = false");
			return false;
		}
	}

	private static String getLocalAndTestDirectory() {
		try {

			String testDir = new java.io.File(".").getCanonicalPath()
					+ File.separator + "test_data" + File.separator;
			log.debug("Test dir: " + testDir);
			return (testDir);

		} catch (Exception e) {
			log.warn("Warning! Could not specify test_directory!");
			return null;
		}

		// return test_directory;
	}
}
