package wdcore.selenium.base;

import java.io.File;


public class VerificationErrors {

	public VerificationErrors() {
		super();
//		this.methodName = methodName;
//		this.wd= wd;
	}

//	String methodName;
//	WebdriverMethods wd;
	
	int errorNum = 0;
	
	private StringBuilder verificationErrors  = new StringBuilder();;

	public StringBuilder getError() {
		return verificationErrors;
	}
	
	

	public void addError(String errorText) {
		++errorNum;
//		System.out.println("_________________________Exception_"
//				+ ++errorNum + "____________________________");
//		System.out.println(errorText);
//
//		
//		wd.makeScreenshot(Environment.appRoot + File.separator + "screenshot"
//				+ File.separator + methodName + "_"
//				+ errorNum + ".png");
//		
//		System.out.println("Current page is "				+ wd.getLocation());
//		System.out
//				.println("_______________________________________________________________");
	
		this.verificationErrors = verificationErrors.append(errorText);
		
	}

	public int getErrorNum() {
		return errorNum;
	}

	
	
	
}
