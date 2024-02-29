package SeleniumFrameworkPractice.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

public class ProductCatalogue extends AbstractComponents{
	
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		//initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);// this - current class driver
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	//List<WebElement> products = driver.findElements(By.cssSelector(".col-lg-4"));
	//pageFactory
	@FindBy(css=".col-lg-4")
	List<WebElement> products;
	
	By productBy = By.cssSelector(".col-lg-4"); //format for By element
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	By spinner = By.cssSelector(".ng-animating");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productBy);
		return products;
	}
	
	public WebElement getProductByName(String product) throws InterruptedException {
		WebElement prod = getProductList().stream().filter(s -> s.findElement(By.tagName("b")).getText()
				.equals(product)).findFirst().orElse(null);
		return prod;
	}

	public void addProductToCart(String productName) throws InterruptedException
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
	}
}
