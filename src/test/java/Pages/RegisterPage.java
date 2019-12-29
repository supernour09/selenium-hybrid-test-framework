package Pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class RegisterPage extends PageBase {

	// Selectors from page
	By TextBox_FirstName = By.name("firstname");
	By TextBox_LastName = By.name("lastname");
	By TextBox_Phone = By.name("phone");
	By TextBox_Email = By.name("email");
	By TextBox_Password = By.name("password");
	By TextBox_ConfirmPassword = By.name("confirmpassword");
	By Button_SignUp = By.cssSelector(".signupbtn");
	By Alert_Box = By.xpath("//*[@id=\"headersignupform\"]/div[2]/div");
	By Button_Login = By.cssSelector(".loginbtn");

	String registerLink = "https://www.phptravels.net/register";
	String logoutLink = "https://www.phptravels.net/account/logout/";
	
	Integer waitTimeForAlert ;
	
	public RegisterPage(WebDriver driver) {
		super();
		logger = LogManager.getLogger(RegisterPage.class);
		waitTimeForAlert = Integer.parseInt( prop.getProperties("waitTimeForAlert").toString());
		this.driver = driver;
		
	}

	public void setFirstName(String text) {
		driver.findElement(TextBox_FirstName).sendKeys(text);
	}

	public void setLastName(String text) {
		driver.findElement(TextBox_LastName).sendKeys(text);
	}

	public void setPhone(String text) {
		driver.findElement(TextBox_Phone).sendKeys(text);
	}

	public void setEmail(String text) {
		driver.findElement(TextBox_Email).sendKeys(text);
	}

	public void setPassword(String text) {
		driver.findElement(TextBox_Password).sendKeys(text);
	}

	public void setConfirmPassword(String text) {
		driver.findElement(TextBox_ConfirmPassword).sendKeys(text);
	}

	public void clickSignUp() {
		driver.findElement(Button_SignUp).click();
	}
	
	
	/**
	 * Logout function used to recover from the successfully register test and able to continue testing
	 * also it delete the cookies
	 */
	public void logout() {
		driver.manage().deleteAllCookies();
		logger.debug("Logout");
		driver.get(logoutLink);
	}
	
	
	/**
	 * Wait until the Error message appears if it appears it return False;
	 * if it doesn't return True
	 * 
	 * it is used to check if the Login process is succsufly or not
	 * @return Boolean
	 */
	public Boolean checkAlertMessage() {
		WebElement alert= null;
		try {
			alert = driver.findElement(Alert_Box);
		}catch(Exception e) {
			//Registration successfully
			return true;
		}
		return false;
	}

	/**
	 * This method used to make Register in PhpTravel website
	 * @param FirstName
	 * @param LastName
	 * @param Phone
	 * @param Email
	 * @param Password
	 * @param ConfirmPassword
	 */
	public void registe(String FirstName, String LastName, String Phone, String Email, String Password,
			String ConfirmPassword) {
		driver.get(registerLink);
		this.setFirstName(FirstName);
		this.setLastName(LastName);
		this.setEmail(Email);
		this.setPhone(Phone);
		this.setPassword(Password);
		this.setConfirmPassword(ConfirmPassword);
		this.clickSignUp();
	}

}
