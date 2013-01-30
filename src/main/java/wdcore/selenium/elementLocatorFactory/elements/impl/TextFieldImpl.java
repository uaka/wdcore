package wdcore.selenium.elementLocatorFactory.elements.impl;

import org.openqa.selenium.WebElement;

import wdcore.selenium.elementLocatorFactory.elements.TextField;

class TextFieldImpl extends AbstractElement implements TextField {
	protected TextFieldImpl(final WebElement wrappedElement) {
		super(wrappedElement);
	}

	// @Override
	public void type(final String text) {
		LOG.debug("Type '" + text + "' in element" + wrappedElement);
		wrappedElement.sendKeys(text);
	}

	// @Override
	public void clear() {
		wrappedElement.clear();
	}

	// @Override
	public void clearAndType(final String text) {
		LOG.debug("Clear and Type '" + text + "' in element" + wrappedElement);
		clear();
		type(text);
	}

	public String getText() {
		return wrappedElement.getText();
	}
}
