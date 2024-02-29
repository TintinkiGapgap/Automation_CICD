package SeleniumFrameworkPractice.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import SeleniumFrameworkPractice.pageobjects.LandingPage;

import static java.nio.file.Files.readAllBytes;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws IOException
	{
		
		//properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\aarti\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\main\\java\\SeleniumFrameworkDesign\\resources\\GlobalData.properties");
		//FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\SeleniumFrameworkDesign\\resources\\GlobalData.properties");
		prop.load(fis);

		//when browser command is sent from mvn command using command line, then use java ternary operator to select the statement
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
				//prop.getProperty("browser");

		if(browserName.contains("chrome"))
		{
			ChromeOptions options = new ChromeOptions();
			if(browserName.contains("headless"))
			{
				options.addArguments("headless");
			}
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarti\\OneDrive\\Documents\\Sync with Google\\Personal\\Learning\\WebDriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
		//WebDriverManager.chromedriver().setup();
		driver =new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));//full screen
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
		System.setProperty("webdriver.gecho.driver", "C:\\Users\\aarti\\OneDrive\\Documents\\Sync with Google\\Personal\\Learning\\WebDriver\\geckodriver-v0.34.0-win-aarch64\\gechodriver.exe");
		//WebDriverManager.chromedriver().setup();
		driver =new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
		System.setProperty("webdriver.edge.driver", "C:\\Users\\aarti\\OneDrive\\Documents\\Sync with Google\\Personal\\Learning\\WebDriver\\edgedriver_win64\\msedgedriver.exe");
		//WebDriverManager.chromedriver().setup();
		driver =new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		return driver;
		
	}
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
	//Json to String
//	String jsonContent = FileUtils.readFileToString(new File(filePath), "UTF-8");
	String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8); // UTF_8 - Encoding Format

	//String to HashMap - Jackson Databind Jar
	ObjectMapper mapper = new ObjectMapper();
	List<HashMap<String, String>> data;
	data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
        return data;
    }

	public List<HashMap<String, String>> getJsonDataToMapUsingGson(String filePath) throws IOException {
		Gson gson = new Gson();
		String json = new String(Files.readAllBytes(Paths.get(filePath)));
		Type type = new TypeToken<List<HashMap<String, String>>>(){}.getType();
		List<HashMap<String, String>> map;
        map = gson.fromJson(json, type);
        return map;

	}

	@BeforeMethod(alwaysRun=true)
		public LandingPage launchApp() throws IOException
		{
			driver = initializeDriver();
			landingPage = new LandingPage(driver);
			landingPage.goTo();
			return landingPage;
		}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user-dir") +"screenshots//reports//" + testCaseName + ".png");
		FileUtils.copyFile(src, file);
		//return file;
		return System.getProperty("user-dir") + "//reports//" + testCaseName + ".png";
	}
}
