package wdcore.selenium.elementLocatorFactory.containers;

import org.openqa.selenium.WebElement;

public interface ContainerFactory {
    <C extends Container> C create(Class<C> containerClass, WebElement wrappedElement);
}
