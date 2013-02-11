package wdcore.selenium.base.map;

import java.io.File;

import wdcore.selenium.base.Environment;

public class BrowsersMap {

	public static final String FF = "firefox";
	public static String IE = "ie";
	
	static String fldIncludes =  getIncludesFld();
//	static String fldIncludes1 = Environment.appRoot + "/includes"
//	 + File.separator;
	public static String IEDRIVER_PATH = fldIncludes + "IEDriverServer.exe";
	public static String GOOGLE_CHROME = "googlechrome";
	public static String CHROME_DRIVER_PATH = fldIncludes + "chromedriver.exe";
	public static String OPERA = "opera";

	public static String FIREBUG = "firebug";
	public static String FLD_FF_EXTENSIONS = fldIncludes + "firefox-extensions"
			+ File.separator;
//	public static String FLD_FF_EXTENSIONS1 = fldIncludes + "firefox-extensions"
//			+ File.separator;
	
	private static String getIncludesFld(){
		String mvn_includes_fld = "src/test/resources/includes" + File.separator;
		String eclipse_includes_fld = Environment.appRoot + "/includes" + File.separator;
		
		File fld_includes_mvn = new File(mvn_includes_fld);
		
		if(fld_includes_mvn.isDirectory()){
			System.out.println("Includes folder = " + mvn_includes_fld);
			return  mvn_includes_fld; // for maven proj
		}
		else 
		{ System.out.println("Includes folder = " + eclipse_includes_fld);
			return eclipse_includes_fld; //for eclipse proj
		}
	}

}
