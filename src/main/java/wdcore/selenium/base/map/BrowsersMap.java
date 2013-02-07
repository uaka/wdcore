package wdcore.selenium.base.map;

import java.io.File;

import wdcore.selenium.base.Environment;

public class BrowsersMap {

	public static String FF = "firefox";
	public static String IE = "ie";
	static String fldIncludes = "src/test/resources/includes" + File.separator; // for
																				// maven
																				// proj
	static String fldIncludes1 = Environment.appRoot + "/includes"
	 + File.separator;
	public static String IEDRIVER_PATH = fldIncludes + "IEDriverServer.exe";
	public static String GOOGLE_CHROME = "googlechrome";
	public static String CHROME_DRIVER_PATH = fldIncludes + "chromedriver.exe";
	public static String OPERA = "opera";

	public static String FIREBUG = "firebug";
	public static String FLD_FF_EXTENSIONS = fldIncludes + "firefox-extensions"
			+ File.separator;
	public static String FLD_FF_EXTENSIONS1 = fldIncludes1 + "firefox-extensions"
			+ File.separator;

}