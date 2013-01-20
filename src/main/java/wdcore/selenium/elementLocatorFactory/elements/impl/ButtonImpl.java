package wdcore.selenium.elementLocatorFactory.elements.impl;

import org.openqa.selenium.WebElement;

import wdcore.selenium.elementLocatorFactory.elements.Button;


class ButtonImpl extends AbstractElement implements Button {
    protected ButtonImpl(final WebElement wrappedElement) {
        super(wrappedElement);
    }

//    @Override
    public void click() {
        wrappedElement.click();
    }
}
