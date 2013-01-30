package wdcore.selenium.source.map;

public class RemoteConnectOptions {

	public RemoteConnectOptions(String hub, String os, String browser,
			String browserVersion) {
		super();
		this.hub = hub;
		this.os = os;
		this.browser = browser;
		this.browserVersion = browserVersion;
	}

	String hub;
	String os;
	String browser;
	String browserVersion;

	public  String[] getConnectOptions() {
		String[] items = { hub,  os, browser, browserVersion};
		return items;
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
}
