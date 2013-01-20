package wdcore.selenium.elementLocatorFactory.containers;

import org.openqa.selenium.WebElement;

import wdcore.selenium.elementLocatorFactory.elements.Element;


public interface Container extends Element {
    void init(WebElement wrappedElement);
}
