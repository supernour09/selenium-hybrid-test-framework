package Pages;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/**
 * 
 * Verification for the email using fakenamegenrator email address
 * @author 
 *
 */
public class EmailPage extends PageBase {
	
	
	By EmailText = By.linkText("PHPTRAVELS");
	String EmailFrame = "emailFrame";
    Integer waitTimeForEmail;
	
	public EmailPage(WebDriver driver , String Email) {
		super();
		this.driver = driver;
		waitTimeForEmail = Integer.parseInt( prop.getProperties("waitTimeForEmail").toString());
		Object[] arrOfStr = Email.split("@", 2); 
		String address = "http://www.fakemailgenerator.com/#/" + arrOfStr[1] + "/" +arrOfStr[0]  + "/";
		System.out.println(address);
		driver.get(address);
	}

	
	/**
	 * Wait until the Email message appears if it appears it return True;
	 * if it doesn't return False
	 * 
	 * @return Boolean
	 */
	public Boolean checkEmailMessage() {
		
		WebElement email = null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(waitTimeForEmail))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
		try {
			
			email = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					driver.switchTo().frame(EmailFrame);
					WebElement element = driver.findElement(EmailText);
					return element;
				}
			});
		} catch (Exception e) {
			System.out.println(e);
		}
		return (email != null);
	}
	
	public void Verification() {
		driver.switchTo().frame(EmailFrame);
		WebElement element = driver.findElement(EmailText);
		element.click();
	}
	
	
	
}
