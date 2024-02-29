package SeleniumFrameworkPractice.Tests;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SeleniumFrameworkPractice.pageobjects.OrdersPage;
import SeleniumFrameworkPractice.TestComponents.BaseTest;
import SeleniumFrameworkPractice.pageobjects.CartPage;
import SeleniumFrameworkPractice.pageobjects.CheckoutPage;
import SeleniumFrameworkPractice.pageobjects.ConfirmationPage;
import SeleniumFrameworkPractice.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{

		String productName = "ZARA COAT 3";
		@Test(dataProvider = "getData", groups= {"Purchase"})
		public void submitOrder(HashMap<String,String> input) throws InterruptedException, IOException
		{


			//LandingPage landingPage = launchApp();
			//ProductCatalogue productCatalogue = landingPage.loginApp("anshika@gmail.com", "Iamking@000");
			ProductCatalogue productCatalogue = landingPage.loginApp(input.get("email"), input.get("password"));

			List<WebElement> products = productCatalogue.getProductList();

			productCatalogue.addProductToCart(input.get("product"));

			CartPage cartPage = productCatalogue.goToCart();

			Boolean match = cartPage.verifyProductDisplay(input.get("product"));
			Assert.assertTrue(match); //No assertion should be written to the page object file
			CheckoutPage checkoutPage = cartPage.goToCheckout();

			checkoutPage.selectCountry("India");
			ConfirmationPage confirmationPage = checkoutPage.submitOrder();
			String confirmationMessage = confirmationPage.getConfirmationMessage();
			Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		}

		@Test(dependsOnMethods = {"submitOrder"})
		public void OrderHistoryTest()
		{
			ProductCatalogue productCatalogue = landingPage.loginApp("anshika@gmail.com", "Iamking@000");
			OrdersPage ordersPage = productCatalogue.goToOrdersPage();
			Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
		}

		public String getScreenshot(String testCaseName) throws IOException {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			File file = new File(System.getProperty("user-dir") + "//reports//" + testCaseName + ".png");
			FileUtils.copyFile(src, file);
			return System.getProperty("user-dir") + "//reports//" + testCaseName + ".png";

			//return System.getProperty("user-dir") + "//reports//" + testCaseName + ".png";
		}

		@DataProvider
		public Object[][] getData() throws IOException {
			List<HashMap<String, String>> data =  getJsonDataToMap(System.getProperty("user.dir")+"/src/test/java/SeleniumFrameworkPractice/data/PurchaseOrder.json");
			return new Object[][] {{data.get(0)}, {data.get(1)}};

			/*HashMap<String, String> map = new HashMap<String, String>();
			map.put("email", "anshika@gmail.com");
			map.put("password", "Iamking@000");
			map.put("product", "ZARA COAT 3");

			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("email", "shetty@gmail.com");
			map1.put("password", "Iamking@000");
			map1.put("product", "ADIDAS ORIGINAL");

			return new Object[][] {{map}, {map1}};*/
	}
		/*@DataProvider
		public Object[][] getData()
		{
		return new Object[][] {{"anshika@gmail.com", "Iamking@000", "ZARA COAT 3" },{"shetty@gmail.com", "Iamking@000", "ADIDAS ORIGINAL"}}; //int, String all the data types comes under Object
		}*/
	
}
