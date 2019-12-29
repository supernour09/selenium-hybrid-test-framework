package DriverManger;

import org.openqa.selenium.WebDriver;

/**
 * 
 * 
 * Abstract class for all the drivers to be extended
 *
 */
public abstract class DriverManger {
	protected WebDriver driver;
	protected abstract void createWebDriver();
	public void quitWebDriver() {
		if(null != driver) {
			driver.quit();
			driver = null;
		}
	}
	
	public WebDriver getWebDriver() {
		if(null == driver) {
			createWebDriver();
		}
		return driver;
	}
}
