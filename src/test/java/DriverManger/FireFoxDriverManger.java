package DriverManger;

import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FireFoxDriverManger extends DriverManger {

	/**
	 * extenation for Firefox Driver
	 */
	@Override
	protected void createWebDriver() {
		//Using external lib to handle the driver download
		WebDriverManager.firefoxdriver().setup();
		this.driver = new FirefoxDriver();

	}

}
