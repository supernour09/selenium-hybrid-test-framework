package Tests;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import DataProviders.ExcelDataProvider;
import Pages.LoginPage;

public class LoginTest extends TestBase {
	
	
	@BeforeTest
	public void before() {
		this.logger = LogManager.getLogger(LoginTest.class);
		dataProvider = new ExcelDataProvider("C:\\Users\\Supernour\\eclipse-workspace\\NourTask\\excel\\Book1.xlsx",
				"Sheet2");
		
	}
	
	
	
	@DataProvider(name = "LoginData")
	public Object[][] getData() {
		return dataProvider.getData();
	}
	
	@Test(dataProvider = "LoginData")
	public void loginTest(String TestCaseName,String Email, String Password, Boolean Result) {
		this.test = this.extent.createTest(TestCaseName);
		this.test.assignCategory("Login Tests");
		logger.debug("Deleting cookies");
		driver.manage().deleteAllCookies();
		logger.debug("Creating Login object");
		LoginPage page = new LoginPage(driver);
		logger.debug("Login " + TestCaseName);
		page.login(Email, Password);
		this.waitForPageLoaded();
		logger.debug("Before wait");
		Boolean PageCheck = page.checkAlertMessage();
		logger.debug("after wait");
		logger.debug("Result Value : " + Result.toString());
		logger.debug(PageCheck);
		Assert.assertEquals(PageCheck, Result,"Failed test " + TestCaseName);

	}

	
}
