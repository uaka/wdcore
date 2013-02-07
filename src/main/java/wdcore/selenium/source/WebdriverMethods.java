package wdcore.selenium.source;

import java.io.*;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.HasInputDevices;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Mouse;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;

import org.openqa.selenium.WebDriverException;
//import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.*;

public class WebdriverMethods extends SeleneseTestBase {

	protected static Logger log = LoggerFactory
			.getLogger(WebdriverMethods.class);

	public WebdriverMethods(int timeout, WebDriver driver,
			VerificationErrors ver) {
		super();
		this.timeout = timeout;
		this.driver = driver;
		this.ver = ver;
	}

	public int timeout;

	private WebDriver driver;

	VerificationErrors ver;

	// public boolean local;

	protected void throwableFail(String text) throws Exception {
		throw new Exception(text);

	}

	public void addVerificationError(String errorText) {
		ver.addError(errorText);

		log.error("_________________________Exception_" + ver.getErrorNum()
				+ "____________________________");
		// log.warn("_________________________Exception_"
		// + ver.verificationErrorNumber + "____________________________");
		log.error(errorText);
		// verificationErrors.append(verificationErrorNumber + " " + errorText
		// + "\nOn page " + wd.getLocation() + "\n");
		// wd.makeScreenshotOnFailure(); //TODO
		makeScreenshot(Environment.appRoot + File.separator + "screenshot"
				+ File.separator + System.currentTimeMillis() + "_"
				+ ver.getErrorNum() + ".png");
		log.error("Current page is " + getLocation());
		System.out
				.println("_______________________________________________________________");
	}

	public void onTestFailure(String errorText, Object failedMethodName) {
		log.error("_________________________Breaking Exception_____________________________");
		log.error(errorText);
		ver.addError(errorText);

		// wd.makeScreenshotOnFailure();
		makeScreenshot(Environment.appRoot + File.separator + "screenshot"
				+ File.separator + "_failure_screenshot_" + failedMethodName
				+ ".png");

		log.error("Current page is " + getLocation());
		log.error("_______________________________________________________________");
	}

	public void waitForTextPresent(final String text) throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout)
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							try {

								return d.getPageSource().contains(text);

							} catch (WebDriverException e) {
							}

							return null;
						}
					});
		} catch (Exception e) {
			throw new Exception("Text is NOT found: '" + text + "'"
					+ " on page " + driver.getCurrentUrl());
		}

		// WebdriverMethods.driver.manage().timeouts().implicitlyWait(3,
		// TimeUnit.SECONDS);
		WebDriverHelper.setImplicitWaitsOn(driver);

	}

	public void waitForElement(String element) throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout).until(elementPresent(element));

		} catch (Exception e) {
			throw new Exception("FAIL: Element " + element + " was not found");
		}
		WebDriverHelper.setImplicitWaitsOn(driver);

	}

	ExpectedCondition<Boolean> elementPresent(final String element) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return isElementPresent(element);
			}
		};
	}

	public void waitNoElement(String element) throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout)
					.until(elementNotPresent(element));
		} catch (Exception e) {
			throw new Exception("FAIL: Element " + element
					+ " is still present");
		}
		WebDriverHelper.setImplicitWaitsOn(driver);

	}

	ExpectedCondition<Boolean> elementNotPresent(final String element) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return !isElementPresent(element);
			}
		};
	}

	public void waitForVisible(String element) throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout).until(elementVisible(element));
		} catch (Exception e) {
			throw new Exception("FAIL: Element " + element + " is not visible ");
		}
		WebDriverHelper.setImplicitWaitsOn(driver);
	}

	ExpectedCondition<Boolean> elementVisible(final String element) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return isVisible(element);
			}
		};
	}

	public void waitForNotVisible(String element) throws Exception {
		Thread.sleep(300);
		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout)
					.until(elementNotVisible(element));
		} catch (Exception e) {
			throw new Exception("FAIL: Element " + element
					+ " is still visible ");
		}
		WebDriverHelper.setImplicitWaitsOn(driver);
	}

	ExpectedCondition<Boolean> elementNotVisible(final String element) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return !isVisible(element);
			}
		};
	}

	public void waitForTitle(String text) throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout).until(titlePresent(text));
		} catch (Exception e) {
			throw new Exception("Timeout fail, title is NOT found: '" + text
					+ "', real title is " + getTitle());
		}
		WebDriverHelper.setImplicitWaitsOn(driver);

	}

	ExpectedCondition<Boolean> titlePresent(final String text) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return getTitle().equalsIgnoreCase(text);
			}
		};
	}

	public void waitForText(String element, String text) throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout).until(textInElementPresent(
					element, text));
		} catch (Exception e) {
			throw new Exception("Timeout fail, text is NOT found: '" + text
					+ "', real text for element " + element + " is: '"
					+ getText(element) + "'");
		}
		WebDriverHelper.setImplicitWaitsOn(driver);

	}

	ExpectedCondition<Boolean> textInElementPresent(final String element,
			final String text) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return (getText(element).contains(text) || (getText(element)
						.equalsIgnoreCase(text)));
			}
		};
	}

	public void waitForNoText(String element, String text) throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout).until(textInElementNotPresent(
					element, text));
		} catch (Exception e) {
			throw new Exception("Timeout fail, text is NOT found: '" + text
					+ "', real text for element " + element + " is: '"
					+ getText(element) + "'");
		}
		WebDriverHelper.setImplicitWaitsOn(driver);
	}

	ExpectedCondition<Boolean> textInElementNotPresent(final String element,
			final String text) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return !(getText(element).contains(text) || (getText(element)
						.equalsIgnoreCase(text)));
			}
		};
	}

	public boolean waitAndVerifyVisible(String element)
			throws InterruptedException {
		Thread.sleep(500);
		for (int second = 0;; second++) {
			if (second >= timeout / 2) {
				log.warn("!WARNING: Element " + element
						+ " is NOT visible on page " + driver.getCurrentUrl());
				return false;
			}
			try {
				if (isVisible(element))
					return true;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

	}

	public void waitForLink(String link) throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout).until(linkIsOpened(link));
		} catch (Exception e) {
			throw new Exception("current location " + driver.getCurrentUrl()
					+ " does not equal expected: " + link);
		}
		WebDriverHelper.setImplicitWaitsOn(driver);
	}

	ExpectedCondition<Boolean> linkIsOpened(final String link) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return driver.getCurrentUrl().equals(link);
			}
		};
	}

	protected void waitAndVerify(String element) throws InterruptedException {

		for (int second = 0;; second++) {
			if (second >= timeout / 2)
				log.warn("!WARNING: Element " + element
						+ " is NOT found on page " + driver.getCurrentUrl());
			try {
				if (isElementPresent(element))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
	}

	public void waitForElementsCount(String element, int count)
			throws Exception {

		WebDriverHelper.setImplicitWaitsOff(driver);
		try {
			new WebDriverWait(driver, timeout).until(elementsCountEquals(
					element, count));
		} catch (Exception e) {
			throw new Exception("FAIL: Elements " + element
					+ " count is not equals " + count);
		}
		WebDriverHelper.setImplicitWaitsOn(driver);
	}

	ExpectedCondition<Boolean> elementsCountEquals(final String element,
			final int count) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver arg0) {

				return getCount(element) == count;
			}
		};
	}

	public void waitForAlert() throws Exception {

		for (int second = 0;; second++) {
			if (second >= timeout)
				throw new Exception("FAIL: No any alert was found");
			try {
				if (isAlertPresent())
					break;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			Thread.sleep(1000);

		}

	}

	public void waitForChecked(String element) throws Exception {
		for (int second = 0;; second++) {
			if (second >= timeout)
				throw new Exception("Element " + element + " is not checked ");
			try {
				if (isChecked(element))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
	}

	// protected void makeScreenshotOnFailure() {
	// // if (local)
	// // try {
	// //
	// // String path = System.getProperty("user.dir");
	// //
	// // File f = new File(path + File.separator + "screenshot"
	// // + File.separator + Verifications.failedMethodName + "_"
	// // + Verifications.verificationErrorNumber + ".png");
	// //
	// // makeScreenshot(f.getAbsolutePath());
	// //
	// // } catch (Exception e) {
	// // System.out.println("Could not get screenshot dir! "
	// // + e.getMessage());
	// // }
	// //
	// // else // remote WD
	// // {
	// try {
	// makeScreenshot(Environment.appRoot + File.separator
	// + "screenshot" + File.separator
	// + Verifications.failedMethodName + "_"
	// + Verifications.verificationErrorNumber + ".png");
	// } catch (Exception e) {
	// System.out.println("Could not make screenshot! "
	// + e.getMessage());
	// }
	//
	// // }
	//
	// }

	public void makeScreenshot(String pathname) {

		log.info("\n Making screenshot on  " + pathname);
		try {

			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(scrFile, new File(pathname));
		} catch (Exception e) {
			log.error("Could not make screenshot! " + e.getMessage());
		}

	}

	protected void windows() {
		Set<String> winIDs = driver.getWindowHandles();

	}

	public void verifyNoText(String element, String text) {
		if ((getText(element).contains(text) || (getText(element)
				.equalsIgnoreCase(text)))) {
			log.warn("WARNING! text in element " + element + "\n is: "
					+ getText(element));
		}

	}

	// Assert there is no such text in the element
	public void assertNoText(String element, String text) throws IOException {

		String realText = getText(element);

		if ((realText.contains(text) || (realText.contains(text.toUpperCase())) || (realText
				.equalsIgnoreCase(text)))) {
			addVerificationError("WARNING! text in element " + element
					+ " is: " + getText(element));

		}

	}

	public void verifyText(String element, String text) {
		if (!(getText(element).contains(text) || (getText(element)
				.equalsIgnoreCase(text)))) {
			log.warn("WARNING! text is differ for element " + element
					+ "\n real text is: " + getText(element)
					+ "  and expected: " + text);
		}

	}

	public boolean assertText(String element, String text) throws IOException {
		String actual = getText(element).toLowerCase();
		String expected = text.toLowerCase();
		try {
			if (!(actual.contains(expected) || actual
					.equalsIgnoreCase(expected))) {
				addVerificationError("Text is differ for element " + element
						+ " \nReal text is:\n" + getText(element)
						+ "\nAnd expected:\n" + text);
				return false;

			}
		} catch (Exception e) {
			addVerificationError(e.getMessage());
			return false;
		}
		return true;

	}

	public void assertTextPresent(String text) throws IOException {

		// if(!(isTextPresent(text.toUpperCase()) ||
		// isTextPresent(text.toLowerCase())))
		// addVerificationError("No text found " + text);

		// assertText("css=body",text);

		String element = "css=body";
		String realText = getText(element);

		try {
			if (!(realText.contains(text.toUpperCase()) || realText
					.contains(text)))

			{

				addVerificationError("No text found '" + text + "'");

			}
		} catch (Exception e) {
			addVerificationError(e.getMessage());
		}

	}

	public void assertNoText(String text) throws IOException {

		String element = "css=body";
		String realText = getText(element);

		try {
			if ((realText.contains(text.toUpperCase()) || realText
					.contains(text))) {

				addVerificationError("Text found '" + text + "'");

			}
		} catch (Exception e) {
			addVerificationError(e.getMessage());
		}

	}

	public void assertTextInElementPresent(String element) throws IOException {
		if ((getText(element).equals(""))) {
			addVerificationError("No text found in element " + element);
		}

	}

	public void assertValue(String element, String value) throws IOException {
		if (!(getValue(element).contains(value) || (getValue(element)
				.equalsIgnoreCase(value)))) {

			// throwableFail("WARNING! value is differ for element " +element +
			// " /n real value is: "+ getValue(element) + "  and expected: " +
			// value);

			addVerificationError("Value is differ for element " + element
					+ " /n real value is: " + getValue(element)
					+ "  and expected: " + value);

		}

	}

	public void assertElement(String element) throws IOException {
		if (!isElementPresent(element))
			// throwableFail("Element "+element +" not found!");
			addVerificationError("Element " + element + " not found!");
	}

	public void assertNoElement(String element) throws IOException {
		if (isElementPresent(element))

			addVerificationError("Element " + element + " presents!");
	}

	public void assertElementVisible(String element) throws IOException {
		if (!isVisible(element))

			addVerificationError("Element " + element + " not visible!");

	}

	public void assertElementNotVisible(String element) throws IOException {
		if (isVisible(element))

			addVerificationError("Element " + element + " is visible!");
	}

	public void assertElementsCount(String element, int expectedCount) throws IOException {
		int actualCount = getCount(element);
		if (actualCount!= expectedCount)
			
			addVerificationError("Elements count of '" + element + "' = " + actualCount+
					"but expected "+ expectedCount);
	}
	
	public void open(String url) {
		log.debug("opening url " + url);
		driver.get(url);
	}

	public String getLocation() {
		return driver.getCurrentUrl();

	}

	public void click(String element) throws Exception {
		try {
			log.debug("clicking " + element);
			getElement(element).click();
		}

		catch (Exception e)

		{
			throwableFail("Could not Click element: " + element + " Error: "
					+ e.getMessage());

		}
	}

	public void jsClick(String elementID) throws Exception {
		try {
			getEval("return window.jQuery('#" + elementID + "').click()");
		}

		catch (Exception e)

		{
			throwableFail("WARNING! Could not Click element: " + elementID
					+ " Error: " + e.getMessage());

		}
	}

	public void clear(String element) {
		// clears textbox
		getElement(element).clear();

	}

	public String getValue(String element) {

		return getElement(element).getAttribute("value");

	}

	public String[] getValues(String elementLocator) {

		List<WebElement> elements = getElementsList(elementLocator);
		int elementsCount = elements.size();

		String[] values = new String[elementsCount];

		int index = 0;

		for (WebElement element : elements) {

			values[index++] = element.getAttribute("value");

		}
		return values;

	}

	public String getEval(String script) throws Exception {
		try {
			return ((JavascriptExecutor) driver).executeScript(script)
					.toString();
		} catch (Exception e) {
			throwableFail("error JS eval: " + e.getMessage() + " \n " + script);
			return null;
		}

	}

	public void type(String element, String text) throws Exception {
		log.debug("Typing text '" + text + "' in element:" + element);
		try {

			getElement(element).sendKeys(text);

		} catch (Exception e)

		{
			throwableFail("WARNING! Could not Type in element: " + element
					+ " Error: " + e.getMessage());

		}
	}

	public String getText(String element) {
		try {
			return getElement(element).getText();
		} catch (Exception e)

		{
			// log.debug("WARNING! Could not get Text from element: "
			// + element);
			return "Could not get Text from element: " + element;

		}

	}

	public String getTitle() {

		return driver.getTitle();

	}

	public void assertAlert(String text) throws Exception {

		String AlertMessage = getConfirmation();

		if (!(AlertMessage.contains(text) || (AlertMessage
				.equalsIgnoreCase(text)))) {
			// throwableFail("WARNING! Alert text is differ"+
			// " /n real text is: "+ AlertMessage + "  and expected: " + text);

			addVerificationError("WARNING! Alert text is differ"
					+ " /n real text is: " + AlertMessage + "  and expected: "
					+ text);
		}

		acceptAlert();

	}

	public void assertElementActive(String elementID) {
		if (!isElementActive(elementID))
			addVerificationError("Element " + elementID
					+ " is not active.  Actually, active: "
					+ getActiveElementID());

	}

	public Boolean isElementActive(String elementID) {
		WebElement element = driver.switchTo().activeElement();
		if (element.getAttribute("id").equals(elementID))
			return true;
		else
			return false;
	}

	public String getActiveElementID() {
		WebElement element = driver.switchTo().activeElement();
		return element.getAttribute("id");
	}

	// public String setActiveElementID(String id) {
	// WebElement element = driver.findElement(By.id(id));
	// element.
	// return element.getAttribute("id");
	// }

	public String getConfirmation() {

		// Get a handle to the open alert, prompt or confirmation
		Alert alert = driver.switchTo().alert();
		// Get the text of the alert or prompt
		return alert.getText();
		// And acknowledge the alert (equivalent to clicking "OK")

	}

	public boolean isAlertPresent() {
		String text;
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
		} catch (Exception e) {
			text = "false";
		}
		if (!text.equals("false"))
			return true;
		else
			return false;

	}

	public boolean acceptAlert() {
		try {
			// Get a handle to the open alert, prompt or confirmation
			Alert alert = driver.switchTo().alert();

			// And acknowledge the alert (equivalent to clicking "OK")
			alert.accept();
		} catch (Exception e) {
			log.warn("Could not accept alert ");
			// + e.getMessage());
			return false;
		}

		return true;
	}

	public void select(String element, String value) {
		try {
			Select select = new Select(getElement(element));
			if (value.startsWith("value="))
				select.selectByValue(value.replace("value=", ""));
			else if (value.startsWith("label="))
				select.selectByVisibleText(value.replace("label=", ""));
			else if (value.startsWith("index=")) {
				int index = Integer.parseInt(value.replace("index=", ""));
				select.selectByIndex(index);
			} else
				select.selectByVisibleText(value);

		} catch (Exception e)

		{
			addVerificationError("WARNING! Could not select in element: "
					+ element + " Error: " + e.getMessage());

		}
	}

	public List<WebElement> getAllSelectedOptions(String element) {

		Select select = new Select(getElement(element));
		return select.getAllSelectedOptions();

	}

	public void removeAllSelections(String element) {
		Select select = new Select(getElement(element));

		select.deselectAll();

	}

	public String getAttribute(String value) {

		if (value.lastIndexOf("@") > 0)
			return getElement(value.substring(0, value.lastIndexOf("@")))
					.getAttribute(
							value.substring(value.lastIndexOf("@") + 1,
									value.length()));

		else
			log.warn("!WARNING: incorrect value " + value + " for getAttribute");
		return "";

	}

	public String getAttribute(String element, String attr) {

		return getElement(element).getAttribute(attr);

	}

	public String[] getElementsAttribute(String elementLocator, String attr) {

		// return getElementsList(element).getAttribute(attr);

		List<WebElement> elements = getElementsList(elementLocator);
		int elementsCount = elements.size();

		String[] values = new String[elementsCount];

		int index = 0;

		for (WebElement element : elements) {

			values[index++] = element.getAttribute(attr);

		}
		return values;

	}

	public void setAttribute(String locator, String attribute, String value) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].setAttribute('" + attribute + "',arguments[1]);",
				getElement(locator), value);
	}

	public void mouseOver(String element) {

		// build and perform the mouseOver with Advanced User Interactions API
		Actions builder = new Actions(driver);
		builder.moveToElement(getElement(element)).build().perform();
		// builder.moveToElement(getElement(element)).perform();

	}

	public void mouseDown(String element) {

		Locatable hoverItem = (Locatable) getElement(element);

		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());

	}

	// :(
	public void mouseMoveAndClick(String element1, String element2) 
	{
		WebDriverHelper.setImplicitWaitsOn(driver);
		Actions builder = new Actions(driver);

		builder.moveToElement(getElement(element1)).click(getElement(element2))
				.build().perform();
	}

	public void mouseOut(String element) {

		Locatable hoverItem = (Locatable) getElement(element);

		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates(), 300, 300);

	}

	public void mouseUp(String element) {

		Locatable hoverItem = (Locatable) getElement(element);

		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseUp(hoverItem.getCoordinates());

	}

	public void mouseMoveAt(String element, int x, int y) {

		Locatable hoverItem = (Locatable) getElement(element);

		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates(), x, y);

	}

	public void dragAndDrop(String element, int x, int y) {

		// build and perform the dragAndDropBy with Advanced User Interactions
		// API
		Actions builder = new Actions(driver);
		builder.dragAndDropBy(getElement(element), x, y).build().perform();

	}

	public void dragAndDropToObject(String element, String element1) {

		// build and perform the dragAndDropBy with Advanced User Interactions
		// API
		Actions builder = new Actions(driver);
		builder.dragAndDrop(getElement(element), getElement(element1)).build()
				.perform();

	}

	public boolean isChecked(String element) {
		return getElement(element).isSelected();
	}

	public void check(String element) {
		WebElement webelem = getElement(element);

		if (!webelem.isSelected())
			webelem.click();

	}

	public void uncheck(String element) {
		WebElement webelem = getElement(element);
		
		if (webelem.isSelected())
			webelem.click();
	}

	public void closeBrowser() {
		log.debug("Closing current browser");
		driver.quit();
	}

	public boolean isVisible(String element) {
//		WebDriverHelper.setImplicitWaitsOn(driver);
		try {
			boolean result = getElement(element).isDisplayed();
			return result;
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			return false;
		}

	}

	public boolean isTextPresent(String text) {
		try {
			return (driver.getPageSource().contains(text));
		} catch (Exception e) {
			log.warn(e.getMessage());
			return false;
		}

	}

	public String[] getTextFromElements(String elementLocator) {
		List<WebElement> elements = getElementsList(elementLocator);
		String[] text = new String[elements.size()];
		int index = 0;

		for (WebElement element : elements) {

			text[index++] = element.getText();

		}
		return text;
	}

	public void assertTextInElementsPresent(String elementLocator) {
		List<WebElement> elements = getElementsList(elementLocator);

		for (WebElement element : elements) {
			if ((element.getText().equals(""))) {
				addVerificationError("No text found in element "
						+ elementLocator);
			}

		}
	}

	public void clickAllElements(String elementLocator) {
		List<WebElement> elements = getElementsList(elementLocator);
		// int elementsCount = elements.size();
		int y = 0;
		for (int i = 0; i < elements.size(); i++) {

			// scroll to previous element
			if (i > 1) {
				WebElement previous = elements.get(i - 2);
				// Locatable hoverItem = (Locatable) previous;
				y = previous.getLocation().getY();
			}
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"
					+ y + ");");

			// click element
			elements.get(i).click();
		}

	}

	public int clickRNDElements(String elementLocator) {

		List<WebElement> elements = getElementsList(elementLocator);
		int elementsCount = elements.size();
		int clickedCount = 0;
		Random random = new Random(elementsCount);
		int y = 0;

		for (int i = 0; i < elementsCount; i++) {
			if (random.nextBoolean()) {

				clickedCount++;

				// scroll to previous element
				if (i > 1) {
					WebElement previous = elements.get(i - 2);
					// Locatable hoverItem = (Locatable) previous;
					// y =
					// hoverItem.getCoordinates().getLocationOnScreen().getY();
					y = previous.getLocation().getY();
				}
				((JavascriptExecutor) driver)
						.executeScript("window.scrollTo(0," + y + ");");

				// click element
				elements.get(i).click();
			}
		}
		return clickedCount;

	}

	public int getCount(String element) {
		return getElementsList(element).size();

	}

	public void verifyElement(String element) {
		if (!isElementPresent(element))
			log.warn("WARNING! element " + element + " not found on page "
					+ getLocation());

	}

	public boolean isElementPresent(String element) {
		return (getElementsList(element).size() > 0) ? true : false;

	}

	WebElement getElement(String element) {

		if (element.startsWith("//"))
			return driver.findElement(By.xpath(element));
		else if (element.startsWith("xpath=")) {
			element = element.substring(6, element.length());
			return driver.findElement(By.xpath(element));
		} else if (element.startsWith("css=")) {
			element = element.substring(4, element.length());
			return driver.findElement(By.cssSelector(element));
		} else if (element.startsWith("id=")) {
			element = element.substring(3, element.length());
			return driver.findElement(By.id(element));
		}

		else if (element.startsWith("link=")) {
			element = element.substring(5, element.length());
			return driver.findElement(By.linkText(element));
		} else if (element.startsWith("partialLink=")) {
			element = element.substring(12, element.length());
			return driver.findElement(By.partialLinkText(element));
		}

		else if (element.startsWith("name=")) {
			element = element.substring(5, element.length());
			return driver.findElement(By.name(element));
		}

		else if (element.startsWith("class=")) {
			element = element.substring(6, element.length());
			return driver.findElement(By.className(element));
		}

		else
			return driver.findElement(By.id(element));

	}

	public void refresh() {
		log.debug("refreshing page " + getLocation());
		driver.navigate().refresh();

	}

	public void selectFrame(String frameLocator) {
		driver.switchTo().frame(getElement(frameLocator));
	}

	public void selectTopFrame() {

		driver.switchTo().defaultContent();
	}

	public void maximizeBrowserWindow() throws Exception {

		Window window = driver.manage().window();
		Point zero = new Point(0, 0);
		window.setPosition(zero);
		String width = getEval("return window.screen.availWidth;");
		String height = getEval("return window.screen.availHeight;");
		Dimension dim = new Dimension(Integer.parseInt(width),
				Integer.parseInt(height));
		window.setSize(dim);

	}

	public void sendKeysToActiveElement(String text) {
		driver.switchTo().activeElement().sendKeys(text);

	}

	public void sendKeys(String text) {
		Actions builder = new Actions(driver);
		Action reload = builder.sendKeys(text).build();
		reload.perform();

	}

	public void ctrlF5() {
		log.debug("Click CTRL + F5");
		Actions builder = new Actions(driver);
		Action reload = builder.sendKeys(Keys.CONTROL, Keys.F5).build();
		reload.perform();
		log.debug("Page was refreshed");
		Action release = builder.keyUp(Keys.CONTROL).build();
		release.perform();
	}

	public void sendTab() {
		driver.switchTo().activeElement().sendKeys(Keys.TAB);

	}

	public void sendDown() {
		driver.switchTo().activeElement().sendKeys(Keys.DOWN);

	}

	public void sendPageDown() {
		driver.switchTo().activeElement().sendKeys(Keys.PAGE_DOWN);
	}

	public void sendEnd() {
		driver.switchTo().activeElement().sendKeys(Keys.END);
	}

	public void sendEnter() {
		driver.switchTo().activeElement().sendKeys(Keys.ENTER);

	}

	public void sendCtrlV(String element) {
		getElement(element).sendKeys(Keys.CONTROL + "v");

	}

	public void shiftHome(String locator) throws Exception {

		getElement(locator).sendKeys(Keys.SHIFT, Keys.HOME);
	}

	public void cleanCookies() {
		driver.manage().deleteAllCookies();

	}

	public void pinHeaderToTop() {
		//		 Adding a cookie to make sure the header stays on top
				
				log.debug(" Adding a cookie to make sure the header stays on top");
				 Cookie cookie = new Cookie("header_stays_top", "true", "/", null);
				 driver.manage().addCookie(cookie);
				 refresh();
	}

	public void fireEvent(String url, String eventName) {
		log.debug("fire event " + eventName + " for element " + url);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String scriptName = getAttribute(url + "@" + eventName);
		js.executeScript(scriptName);

	}

	public void hideElement(String elementID) throws Exception {
		log.debug("hiding: " + elementID);
		getEval("return $('#" + elementID + "').hide()");
		acceptAlert();
		assertElementNotVisible(elementID);
		acceptAlert();
	}

	public void scrollToElement(String element) {
		try {
			Locatable hoverItem = (Locatable) getElement(element);
			int y = hoverItem.getCoordinates().getLocationOnScreen().getY();
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"
					+ y + ");");
		} catch (Exception e) {
			log.warn("WARNING! Could not scroll to element: " + element);
		}
	}

	public void scrollUp() {

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,"
				+ "document.body.scrollHeight,document.documentElement.clientHeight));");
	}

	public void showElement(String elementID) throws Exception {
		getEval("return $('#" + elementID + "').show()");
		acceptAlert();
	}

	public List<WebElement> getElementsList(String element) {

		try {
			if (element.startsWith("//")) {

				return driver.findElements(By.xpath(element));

			}

			else if (element.startsWith("xpath=")) {
				element = element.substring(6, element.length());
				return driver.findElements(By.xpath(element));
			}

			else if (element.startsWith("css=")) {
				element = element.substring(4, element.length());
				return driver.findElements(By.cssSelector(element));

			} else if (element.startsWith("id=")) {
				element = element.substring(3, element.length());
				return driver.findElements(By.id(element));
			}

			else if (element.startsWith("link=")) {
				element = element.substring(5, element.length());
				return driver.findElements(By.linkText(element));
			}

			else if (element.startsWith("name=")) {
				element = element.substring(5, element.length());
				return driver.findElements(By.name(element));
			} else
				return driver.findElements(By.id(element));

		} catch (Exception e) {
			return null;
		}
	}

	public void assert2Strings(String name, String expected, String actual)
			throws Exception {
		if (!expected.equalsIgnoreCase(actual) )

			addVerificationError(name + " is '" + actual
					+ "' does not match expected result: " + expected);
	}

	public void assert2StringsNotEquals(String name, String expected,
			String actual) throws Exception {
		if (expected.equals(actual))

			addVerificationError(name + " is " + actual + " should not match: "
					+ expected);
	}

	public void assertStringContains(String name, String expected, String actual)
			throws Exception {
		if (!actual.contains(expected))

			addVerificationError(name + " is " + actual
					+ " does not contains expected result: " + expected);
	}

	public void assertisTrue(String name, boolean cond) {
		if (!cond)
			addVerificationError("Error: " + name);

	}

	public void assertEquals(String name, int expected, int actual) {
		if (expected != actual)
			addVerificationError("Error: " + name + " expected " + expected
					+ " actual " + actual);
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
