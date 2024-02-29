package SeleniumFrameworkPractice.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents{
WebDriver driver;
	
	public CheckoutPage(WebDriver driver)
	{
		//initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);// this - current class driver
	}
	
	@FindBy(xpath="//input[@placeholder = 'Select Country']")
	WebElement country;
	
	@FindBy(xpath="//button[contains(@class, 'ta-item')][2]")
	WebElement selectCountry;
	
	@FindBy(xpath="//a[text()='Place Order ']")
	WebElement submit;
	
	By results = By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName)
	{
		Actions a = new Actions(driver);
		a.sendKeys(country,countryName ).build().perform();
		waitForElementToAppear(results);
		selectCountry.click();
	}
	
	public ConfirmationPage submitOrder()
	{
		submit.click();
		return new ConfirmationPage(driver);
	}

}
