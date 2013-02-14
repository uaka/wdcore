package wdcore.selenium.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wdcore.selenium.base.map.BrowsersMap;
import wdcore.selenium.base.map.RemoteConnectOptions;
//import wdcore.selenium.base.map.RemoteConnectOptions;

import com.opera.core.systems.OperaDriver;
import org.openqa.selenium.Platform;

public class WebDriverHelper extends HelperBase {

    protected static Logger log = LoggerFactory.getLogger(WebDriverHelper.class);

    public static void addChromeDriverPath() {
        File file = new File(BrowsersMap.CHROME_DRIVER_PATH);
        System.setProperty("webdriver.chrome.driver",
                file.getAbsolutePath());
    }
    private WebDriver driver;
    // private String browserMode = BrowsersMap.FF;
    private String defaultHub = Environment.seleniumServerUrl;// "http://localhost:4444/wd/hub"; //
    // change to
    // "http://myserver:4444/wd/hub"
    // to use remote webdriver by
    // default
    // Factory
    private static int restartFrequency = Integer.MAX_VALUE;
    private static String key = null;
    private static int count = 0;

    // public void setDefaultHub(String newDefaultHub) {
    // defaultHub = newDefaultHub;
    // }
    //
    // public void setBrowser(String browser) {
    // browserMode = browser;
    // }
    private WebDriver newWebDriver(RemoteConnectOptions options) {
        String hub = options.getHub();
        String browserMode = options.getBrowser();
        driver = (hub == null) ? createLocalDriver(browserMode)
                : createRemoteDriver(options);
        setImplicitWaitsOn();
        key = browserMode + ":" + hub;
        count = 0;
        return driver;
    }

    public WebDriverHelper(Application manager) {
        super(manager);
        String hub = manager.getProperty("serverHost", defaultHub);
        String os = manager.getProperty("os");
        String browserMode = manager.getProperty("browser", BrowsersMap.FF);
        String browserVersion = manager.getProperty("browserVersion");

        RemoteConnectOptions options = new RemoteConnectOptions(hub, os,
                browserMode, browserVersion);

        // setDefaultHub(manager.getProperty("serverHost"));
        log.debug("Going to start " + browserMode);

        if (browserVersion != null) {
            log.debug("browserVersion = " + browserVersion);
        }
        if (os != null) {
            log.debug(" on os: " + os);
        }
        if (hub != null) {
            log.debug(" on hub: " + hub);
        }

        driver = getDriver(options);

        // driver = new TracingWebDriver(driver); TODO
        log.debug("Browser started");

    }

    private static WebDriver createLocalDriver(String browserMode) {
        log.debug("Starting local driver (no greed)");
        if (browserMode.equals(BrowsersMap.FF)) {

            try {
                FirefoxProfile profile = FFProfileSetup.getFFProfile(manager.getProperty("serverTestDir"), (manager.getProperty(
                        "debugMode", "false")).equalsIgnoreCase("true"));

                return new FirefoxDriver(profile);
            } catch (Exception e) {
                log.warn("WARNING! Could not add firebug and firefinder extensions "
                        + e.getMessage());

                return new FirefoxDriver();
            }

        }

        if (browserMode.startsWith(BrowsersMap.IE)) {
            addIEDriverPath();
            return new InternetExplorerDriver();
        }
        if (browserMode.equals(BrowsersMap.GOOGLE_CHROME)) {
            addChromeDriverPath();
            return new ChromeDriver();
        }
        if (browserMode.equals(BrowsersMap.OPERA)) {
            return new OperaDriver();
        }

        log.debug("Unknown browser, falling back to firefox");
        return new FirefoxDriver();
    }

    private static WebDriver createRemoteDriver(RemoteConnectOptions options) {

        String hub = options.getHub();
        String browserMode = options.getBrowser();
        String browserVersion = options.getBrowserVersion();
        String os = options.getOs();

        DesiredCapabilities capabillities = new DesiredCapabilities();

        if (hub.contains("saucelabs")) {
            capabillities.setBrowserName(browserMode);
            if (browserVersion != null) {
                capabillities.setCapability("version", browserVersion);
            }
            // capabillities.setCapability("platform", Platform.valueOf(os));
            if (os != null) {
                capabillities.setCapability("platform", os);
            }
            // capabillities.setCapability("name", method.getName());
        } else {

            if (browserMode.equals(BrowsersMap.FF)) {
                capabillities = DesiredCapabilities.firefox();
                 FirefoxProfile profile = FFProfileSetup.getFFProfile(manager.getProperty("serverTestDir"), (manager.getProperty(
                    "debugMode", "false")).equalsIgnoreCase("true"));
            capabillities.setCapability(FirefoxDriver.PROFILE, profile);
            } else if (browserMode.equals(BrowsersMap.IE)) {
//                addIEDriverPath();
                capabillities = DesiredCapabilities.internetExplorer();
            } else if (browserMode.equals(BrowsersMap.GOOGLE_CHROME)) {
//                addChromeDriverPath();
                capabillities = DesiredCapabilities.chrome();
            } else if (browserMode.equals(BrowsersMap.OPERA)) {
                capabillities = DesiredCapabilities.opera();
            } else capabillities = DesiredCapabilities.htmlUnit();

//			capabillities = browserMode.equals(BrowsersMap.FF) ? DesiredCapabilities
//					.firefox()
//					: browserMode.equals(BrowsersMap.IE) ?  DesiredCapabilities
//							.internetExplorer()
//							: browserMode.equals(BrowsersMap.GOOGLE_CHROME) ? DesiredCapabilities
//									.chrome()
//									: browserMode.equals(BrowsersMap.OPERA) ? DesiredCapabilities
//											.opera() : DesiredCapabilities
//											.htmlUnit();

        }
       
        log.debug("Starting remote driver");

        // desiredCapabilities.setJavascriptEnabled(true);
        try {
            return new ScreenshotRemoteWebDriver(new URL(hub), 
                    capabillities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Could not connect to remote WebDriver hub ", e);
        }
    }

    public void dismissDriver() {
        log.debug("Going to stop browser");
        if (driver != null) {
            driver.quit();
            driver = null;
            key = null;
            log.debug("Browser stopped");
        }

    }

    //
    // public void stop() {
    // log.debug("Going to stop browser");
    // driver.quit();
    // log.debug("Browser stopped");
    // }
    public WebDriver getDriver() {

        // String browserMode = manager.getProperty("browser", BrowsersMap.FF);
        // return getDriver(defaultHub, browserMode);
        return driver;
    }

    public WebDriver getDriver(RemoteConnectOptions options) {
        count++;

        // 1. WebDriver instance is not created yet
        if (driver == null) {
            return newWebDriver(options);
        }
        // 2. Different flavour of WebDriver is required
        String newKey = options.getBrowser() + ":" + options.getHub();

        if (!newKey.equals(key)) {
            dismissDriver();
            key = newKey;
            return newWebDriver(options);
        }
        // 3. Browser is dead
        try {
            driver.getCurrentUrl();
        } catch (Throwable t) {
            t.printStackTrace();
            return newWebDriver(options);
        }
        // 4. It's time to restart
        if (count > restartFrequency) {
            dismissDriver();
            return newWebDriver(options);
        }
        // 5. Just use existing WebDriver instance
        return driver;
    }

    public void setImplicitWaitsOff() {
        log.trace("Set implicit waits off");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public void setImplicitWaitsOn() {
        log.trace("Set implicit waits on");
        driver.manage().timeouts().implicitlyWait(manager.getTimeout(), TimeUnit.SECONDS);
    }

    public static void setImplicitWaitsOff(WebDriver driver) {
        log.trace("Set implicit waits off");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public static void setImplicitWaitsOn(WebDriver driver) {
        log.trace("Set implicit waits on");
        driver.manage().timeouts().implicitlyWait(manager.getImplicitWait(), TimeUnit.SECONDS);
    }

    public static void addIEDriverPath() {
        File file = new File(BrowsersMap.IEDRIVER_PATH);
        log.debug("Setting IE driver from " + file);
        if(file.exists()){
             System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
//              log.debug("File exists");
        
        } else   log.debug("File doesnt exist!!!!!!!!");
    }
}
