package DriverManger;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverManger extends DriverManger {

	/**
	 * extenation for Chrome Driver
	 */
	@Override
	protected void createWebDriver() {
		//Customizes options
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("headless");
		options.addArguments("--window-size=1400,1200");
		//Using external lib to handle the driver download
		WebDriverManager.chromedriver().setup();
		this.driver = new ChromeDriver(options);

	}

}
