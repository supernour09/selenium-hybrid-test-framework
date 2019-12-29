package Pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import config.PropertiesFile;

public class PageBase {
	
	WebDriver driver = null;
	PropertiesFile prop ;
	protected Logger logger;
	
	public PageBase() {
		prop = new PropertiesFile();
	}

}
