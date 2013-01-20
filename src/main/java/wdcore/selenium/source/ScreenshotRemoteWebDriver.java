package wdcore.selenium.source;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ScreenshotRemoteWebDriver extends RemoteWebDriver implements TakesScreenshot  {

 public ScreenshotRemoteWebDriver(URL url, DesiredCapabilities dc) {
  super(url, dc);
 } 
 
 public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
  if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) {
   return target.convertFromBase64Png(execute(DriverCommand.SCREENSHOT).getValue().toString());
  }
  return null;
 } 
 public WebElement findElementByLocator(String locator){
	  if(locator.startsWith("//"))
	   return super.findElementByXPath(locator);
	  else if (locator.startsWith("css=")) {
	   return super.findElementByCssSelector(locator.replace("css=", ""));
	  } else {
	   try{
	    return super.findElementById(locator);
	   } catch (NoSuchElementException e){
	    return super.findElementByName(locator);
	   }
	  }
	 }
	 
	 public List<WebElement> findElementsByLocator(String locator){
	  if(locator.startsWith("//"))
	   return super.findElementsByXPath(locator);
	  else {
	   List<WebElement> elements = super.findElementsById(locator);
	   if(elements.size() == 0)
	     elements = super.findElementsByName(locator);
	   return elements;
	  }
	 }
	}