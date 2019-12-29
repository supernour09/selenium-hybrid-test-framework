package Tests;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import DataProviders.ExcelDataProvider;
import Pages.EmailPage;
import Pages.LoginPage;
import Pages.RegisterPage;

public class EmailValid extends TestBase {

	// Data Provider to get data from excel
	ExcelDataProvider dataProvider;
	
	
	
	@BeforeTest
	public void before() {
		this.logger = LogManager.getLogger(LoginTest.class);
		dataProvider = new ExcelDataProvider("C:\\Users\\Supernour\\eclipse-workspace\\NourTask\\excel\\Book1.xlsx",
				"Sheet4");
	}
	
	@DataProvider(name = "EmailData")
	public Object[][] getData() {
		return dataProvider.getData();
	}
	
	@Test(dataProvider = "EmailData")
	public void EmailTest(String TestCaseNo,String FirstName, String LastName, String Email
			,String Phone , String Password) {		
		this.test = this.extent.createTest(TestCaseNo);
		this.test.assignCategory("Email Tests");
		logger.debug("Deleting cookies");
		driver.manage().deleteAllCookies();
		logger.debug("Creating Register object");
		RegisterPage page = new RegisterPage(driver);
		logger.debug("Registe");
		page.registe(FirstName, LastName, Phone, Email, Password, Password);
		logger.debug("Waiting object");
		this.waitForPageLoaded();
		if (page.checkAlertMessage()) {
			logger.debug("Test Succes Registe  " + TestCaseNo + " : ");
			EmailPage emailPage = new EmailPage(driver, Email);
			this.waitForPageLoaded();
			if(emailPage.checkEmailMessage()) {
				emailPage.Verification();
				logger.debug("Test Succes Email Verfication  " + TestCaseNo + " : ");
				LoginPage loginPage = new LoginPage(driver);
				loginPage.login(Email, Password);
				this.waitForPageLoaded();
				if(!loginPage.checkAlertMessage()) {
					logger.debug("Test Succes Login  " + TestCaseNo + " : ");
					Assert.assertTrue(true, "Whole proccess is succs");
				}else {
					logger.debug("Test Failed  " + TestCaseNo + " : ");
					Assert.assertTrue(false, "Failed Test in Login Check");
				}
			}else {
				logger.debug("Test Failed  " + TestCaseNo + " : ");
				Assert.assertTrue(false, "Failed Test in Email Address Check");
			}
		} else {
			logger.debug("Test Failed  " + TestCaseNo + " : ");
			Assert.assertTrue(false, "Failed Test in Register");
		}

	}


}
