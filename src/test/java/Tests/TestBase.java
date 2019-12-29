package Tests;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import DataProviders.ExcelDataProvider;
import DriverManger.DriverManger;
import DriverManger.DriverMangerFactory;
import DriverManger.DriverMangerFactory.DriverType;
import config.PropertiesFile;

/**
 * base class for all tests
 * 
 *
 */
public class TestBase {

	public DriverManger driverManger;
	public WebDriver driver;
	public ExcelDataProvider dataProvider;
	public Logger logger;
	public static Object[][] data;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public PropertiesFile prop ;
	public Integer waitTimeForPageLoad;

	
	/*
	 * make sure the page is fully loaded
	 */
	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			logger.error("page not loading");
		}
	}

	@BeforeSuite
	public void setUp() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/MyOwnReport.html");
		htmlReporter.setAppendExisting(true);
		htmlReporter.config().setDocumentTitle("PhpTravel Report");
		htmlReporter.config().setReportName("PhpTravel Report");
		htmlReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		driverManger = DriverMangerFactory.getDriverManger(DriverType.CHROME);
		driver = driverManger.getWebDriver();
		prop = new PropertiesFile();
		waitTimeForPageLoad = Integer.parseInt( prop.getProperties("waitTimeForPageLoad").toString());
		driver.manage().timeouts().implicitlyWait(waitTimeForPageLoad, TimeUnit.SECONDS);
	}

	
	
	
	/**
	 * to convert the object array into one string
	 * 
	 * @param parameters
	 * @return
	 */
	public String parametersString(Object[] parameters) {
		String[] stringArray = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++)
			stringArray[i] = String.valueOf(parameters[i]);

		return Arrays.toString(stringArray);
	}
	
	/**
	 * reopen the driver to recover from failed asserts
	 */
	public void reopenDriver() {
		driver.close();
		driver.quit();
		driverManger = DriverMangerFactory.getDriverManger(DriverType.CHROME);
		driver = driverManger.getWebDriver();
	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		// get the parameters used in the test
		Object[] parameters = result.getParameters();
		// convert it to string to display it in the report
		String parameterStringValue = parametersString(parameters);
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));
			test.log(Status.FAIL, MarkupHelper.createLabel(parameterStringValue, ExtentColor.RED));
			//get name of the test
			String screenshotPath = TestBase.getScreenhot(driver, parameters[0].toString());
			test.addScreenCaptureFromPath(screenshotPath);
			test.fail(result.getThrowable());
			reopenDriver();
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
			test.log(Status.PASS, MarkupHelper.createLabel(parameterStringValue, ExtentColor.GREEN));
		} else {
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
	
	}

	@AfterTest
	public void end() {
		System.out.println("in the end");
		extent.flush();
	}
	
	@AfterSuite
	public void tearDown() {
		this.driver.quit();
	}

	/**
	 * take the screenshot and return the address
	 * @param driver
	 * @param screenshotName
	 * @return
	 * @throws Exception
	 */
	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir") + "/test-output/screenShots/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}
