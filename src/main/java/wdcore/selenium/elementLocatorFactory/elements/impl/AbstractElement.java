package wdcore.selenium.elementLocatorFactory.elements.impl;



import org.openqa.selenium.WebElement;

import wdcore.selenium.elementLocatorFactory.elements.Element;


abstract class AbstractElement implements Element {
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
    
}
