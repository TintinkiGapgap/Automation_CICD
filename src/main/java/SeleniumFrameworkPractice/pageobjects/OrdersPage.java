package SeleniumFrameworkPractice.pageobjects;

import java.util.List;
import java.util.stream.Stream;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrdersPage extends AbstractComponents {

	WebDriver driver;
	
	public OrdersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="tr td:nth-child(3)")
	 List<WebElement> orderNames;
	
	public boolean verifyOrderDisplay(String orderName)
	{
		Boolean match = orderNames.stream().anyMatch(product->product.getText().equalsIgnoreCase(orderName)); //anyMatch for Boolean
		return match;
	}
}
