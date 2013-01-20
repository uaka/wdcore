package wdcore.selenium.elementLocatorFactory;


import org.openqa.selenium.WebElement;

import wdcore.selenium.elementLocatorFactory.elements.Element;




public interface ElementFactory {
    <E extends Element> E create(Class<E> elementClass, WebElement wrappedElement);
}
