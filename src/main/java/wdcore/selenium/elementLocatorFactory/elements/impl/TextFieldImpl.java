package wdcore.selenium.elementLocatorFactory.elements.impl;



import org.openqa.selenium.WebElement;

import wdcore.selenium.elementLocatorFactory.elements.TextField;


class TextFieldImpl extends AbstractElement implements TextField {
    protected TextFieldImpl(final WebElement wrappedElement) {
        super(wrappedElement);
    }

//    @Override
    public void type(final String text) {
        wrappedElement.sendKeys(text);
    }

//    @Override
    public void clear() {
        wrappedElement.clear();
    }

//    @Override
    public void clearAndType(final String text) {
        clear();
        type(text);
    }
}
