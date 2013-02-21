package wdcore.selenium.elementLocatorFactory.elements.impl;



import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wdcore.selenium.elementLocatorFactory.elements.Element;
import wdcore.selenium.pageFactory.Page;


abstract class AbstractElement implements Element {
	protected static Logger LOG = LoggerFactory.getLogger(AbstractElement.class);
    protected final WebElement wrappedElement;

    protected AbstractElement(final WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

//    @Override
    public boolean isDisplayed() {
    	try {
			boolean result = wrappedElement.isDisplayed();
			return result;
		} catch (Exception e) {
					return false;
		}

    }
    
    public String getText() {
        return  wrappedElement.getText();
      }
    
    
     public String getAttribute(String attr) {
		return wrappedElement.getAttribute(attr);
	}
   
}
