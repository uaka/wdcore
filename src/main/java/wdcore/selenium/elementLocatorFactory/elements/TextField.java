package wdcore.selenium.elementLocatorFactory.elements;

public interface TextField extends Element {
    void type(String text);

    void clear();

    void clearAndType(String text);
    
    String getText();
    String getValue();
}
