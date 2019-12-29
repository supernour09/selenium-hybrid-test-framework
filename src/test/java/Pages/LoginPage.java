package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends PageBase {

	
	/**
	 * Selectors from the page
	 */
	By TextBox_Email = By.name("username");
	By TextBox_Password = By.name("password");
	By Button_Login = By.cssSelector(".loginbtn");
	By Alert_Box = By.xpath("//*[@id=\"toggleStyle04-collapseOne\"]/div/div/div[1]/div");
	
	String Link = "https://www.phptravels.net/login";
	

	/*
	 * Constructor which request the page
	 */
	
	public LoginPage(WebDriver driver) {
		super();
		this.driver = driver;
		driver.get(Link);
	}

	public void setEmail(String text) {
		driver.findElement(TextBox_Email).sendKeys(text);
	}

	public void setPassword(String text) {
		driver.findElement(TextBox_Password).sendKeys(text);
	}

	public void clickLogin() {
		driver.findElement(Button_Login).click();
	}
	
	
	/**
	 * Wait until the Error message appears if it appears it return False;
	 * if it doesn't return True
	 * 
	 * it is used to check if the Login proccess is succssuflly or not
	 * @return Boolean
	 */
	public Boolean checkAlertMessage() {
		return !driver.findElement(Alert_Box).isDisplayed();
	}

	
	/**
	 * This method is used to login into PhpTravel website
	 * @param Email
	 * @param Password
	 */
	public void login(String Email, String Password) {
		//TODO write logger here
		this.setEmail(Email);
		this.setPassword(Password);
		this.clickLogin();
	}

}
