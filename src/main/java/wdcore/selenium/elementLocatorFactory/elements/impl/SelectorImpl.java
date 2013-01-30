package wdcore.selenium.elementLocatorFactory.elements.impl;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import wdcore.selenium.elementLocatorFactory.elements.Selector;


class SelectorImpl extends AbstractElement implements Selector {
    protected SelectorImpl(final WebElement wrappedElement) {
        super(wrappedElement);
    }

//    @Override
    public void select(String value) {
    	LOG.debug("Select '" + value + "' in " + wrappedElement);

    	
			Select select = new Select(wrappedElement);
			if (value.startsWith("value="))
				select.selectByValue(value.replace("value=", ""));
			else if (value.startsWith("label="))
				select.selectByVisibleText(value.replace("label=", ""));
			else if (value.startsWith("index=")) {
				int index = Integer.parseInt(value.replace("index=", ""));
				select.selectByIndex(index);
			} else
				select.selectByVisibleText(value);

    }
}
