
package SeleniumFrameworkPractice.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

public class LandingPage extends AbstractComponents{
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		//initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);// this - current class driver
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	//pageFactory
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(xpath="//input[@type='password']")
	WebElement passwordEle;
	
	@FindBy(name="login")
	WebElement submit;
	
	@FindBy(css="[class*= 'flyInOut']")  // Class - .ng-tns-c4-14.ng-star-inserted.ng-trigger.ng-trigger-.ngx-toastr.toast-error
	WebElement invalidLoginErrorMessage;
	
	public void goTo()	
	{
		driver.get("http://www.rahulshettyacademy.com/client"); //URL in the browse
	}
	
	public ProductCatalogue loginApp(String email, String password)
	{
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	public String getErrorMessage()
	{
		waitForWebElementToAppear(invalidLoginErrorMessage);
		String errMessage = invalidLoginErrorMessage.getText();
		return errMessage;
	}
	


	
}
