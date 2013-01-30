package wdcore.selenium.source;

import java.util.Properties;

//import org.apache.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Application {
	
//	private static final Logger log = Logger.getLogger(Application.class);
	protected static Logger log = LoggerFactory.getLogger(Application.class);


	private static Application singleton;

	private Properties props;

//	private AutoItHelper autoItHelper;
	private WebDriverHelper webDriverHelper;
//	private TabHelper tabHelper;
//	private NavigationHelper navigationHelper;
/*
	public static Application getInstance() {
		if (singleton == null) {
			singleton = new Application();
			log.info("ApplicationManager created");
		}
		return singleton;
	}
	
	private Application() {
	}
	*/

	public void stop() {
		log.info("Application stop");
		if (webDriverHelper != null) {
			
			webDriverHelper.dismissDriver();
		}
	
	}
	
	public void setProperties(Properties props) {
		this.props = props;
	}
	
	public String getProperty(String key) {
		return props.getProperty(key);
	}
	
	public int getTimeout() {
		return Integer.parseInt(getProperty("timeout", "30"));
	}
	
	public int getImplicitWait() {
		return Integer.parseInt(getProperty("implicitWait", "3"));
	}

	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	

	public WebDriverHelper getWebDriverHelper() {
		if (webDriverHelper == null) {
			webDriverHelper = new WebDriverHelper(this);
			log.info("WebDriverHelper created");
		}
		return webDriverHelper;
	}

//	public AutoItHelper getAutoItHelper() {
//		if (autoItHelper == null) {
//			autoItHelper = new AutoItHelper(this); 
//			log.trace("AutoItHelper created");
//		}
//		return autoItHelper;
//	}
//
//  public TabHelper topLevel() {
//    return getTabHelper();
//  }
//
//	public TabHelper getTabHelper() {
//		if (tabHelper == null) {
//		  tabHelper = new TabHelper(this);
//			log.trace("TabHelper created");
//		}
//		return tabHelper;
//	}
//
//	public NavigationHelper navigateTo() {
//		if (navigationHelper == null) {
//			navigationHelper = new NavigationHelper(this);
//			log.trace("NavigationHelper created");
//		}
//		return navigationHelper;
//	}
//	
//	// aliases
//
//	public ListOf<Tab> getTabs() {
//	  return getTabHelper().getTabs();
//	}
//
//  public Tab createTab() {
//    return getTabHelper().createTab();
//  }
}
