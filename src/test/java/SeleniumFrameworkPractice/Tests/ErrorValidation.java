package SeleniumFrameworkPractice.Tests;
import SeleniumFrameworkPractice.TestComponents.Retry;
import SeleniumFrameworkPractice.pageobjects.CartPage;
import SeleniumFrameworkPractice.pageobjects.ProductCatalogue;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SeleniumFrameworkPractice.TestComponents.BaseTest;

import java.util.List;

public class ErrorValidation extends BaseTest{

	@Test(groups= {"ErrorHandling"}, retryAnalyzer= Retry.class)
	public void InvalidLoginErrValidation()
	{
		landingPage.loginApp("anshika123@gmail.com", "Iamking@0000");
		//Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMessage());
	}

	@Test
	public void ProductErrorValidation() throws InterruptedException {
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApp("rahulshetty@gmail.com", "Iamking@000");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCart();
		boolean match = cartPage.verifyProductDisplay("ZARA COAT 1923");
		org.testng.Assert.assertTrue(match);
	}

}
