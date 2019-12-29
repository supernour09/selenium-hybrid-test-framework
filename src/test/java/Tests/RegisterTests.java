package Tests;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import DataProviders.ExcelDataProvider;
import Pages.RegisterPage;


public class RegisterTests extends TestBase {
	
	@BeforeTest
	public void before() {
		this.logger = LogManager.getLogger(LoginTest.class);
		dataProvider = new ExcelDataProvider("C:\\Users\\Supernour\\eclipse-workspace\\NourTask\\excel\\Book1.xlsx",
				"Sheet1");
	}
	
	@DataProvider(name = "RegisterData")
	public Object[][] getData() {

		return dataProvider.getData();

	}
	
	
	@Test(dataProvider = "RegisterData")
	public void RegisterTest(String TestCaseName,String FirstName, String LastName, String Email, String Phone, String Password,
			String ConfirmPassword, Boolean Result) {
		this.test = this.extent.createTest(TestCaseName);
		this.test.assignCategory("Register Tests");
		logger.debug("Deleting cookies");
		driver.manage().deleteAllCookies();
		logger.debug("Creating Register object");
		RegisterPage page = new RegisterPage(driver);
		page.logout();
		logger.debug("Registe " + TestCaseName);
		this.waitForPageLoaded();
		page.registe(FirstName, LastName, Phone, Email, Password, ConfirmPassword);
		this.waitForPageLoaded();
		logger.debug("Before Wait for Alert Message");
		Boolean PageCheck = page.checkAlertMessage();
		logger.debug("After Wait for Alert Message");
		logger.debug("Excpected Value : " + Result.toString());
		logger.debug("Returned Value :" + PageCheck);
		Assert.assertEquals(PageCheck, Result,"Failed test " + TestCaseName);
	}


	

}
