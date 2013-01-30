package wdcore.selenium.pageFactory;


import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wdcore.selenium.elementLocatorFactory.ExtendedFieldDecorator;
import wdcore.selenium.source.Application;
import wdcore.selenium.source.WebdriverMethods;
//import wdcore.selenium.source.WebdriverMethods;


public abstract class Page {
	protected static Logger LOG = LoggerFactory.getLogger(Page.class);

//	protected Application app;
	protected WebdriverMethods wd;

	public void init(Application app, WebdriverMethods wd) {
		
		this.wd = wd;
	      PageFactory.initElements( new ExtendedFieldDecorator(app.getWebDriverHelper().getDriver()), this);
	      
	}

}
