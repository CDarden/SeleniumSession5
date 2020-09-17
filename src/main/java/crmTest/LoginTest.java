package crmTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {

	WebDriver driver;
	String browser = null;

	@BeforeTest
	public void readConfig() {
		// below classes help us read any files.
		// InputStream
		// BufferedReader
		// FileReader
		// Scanner

		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("./src/main/java/config/config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			// Setting up the property
			System.setProperty("webdriver.chrome.driver", ".\\chromedriver_win32\\chromedriver.exe");

			// Creating an object for Chrome driver
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", "./driver/geckodriver.exe");
			driver = new FirefoxDriver();

		}

		// maximizing browser
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		// Get to the website
		driver.get("http://www.techfios.com/ibilling/?ng=admin/");

		// Use Implicitly wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void loginTest1() throws InterruptedException {

		// Element Library - how to store webElement using By Locator and WebElement
		By USERNAME_FIELD_Locator = By.id("username");
		By PASSWORD_FIELD_Locator = By.id("password");
		By SIGNIN_BUTTON_Locator = By.name("login");
		By DASHBOARD_PAGE_Locator = By.xpath(" //h2[contains(text(),'Dashboard ' )]");

		// Data we need to pass
		String loginId = ("demo@techfios.com");
		String passWord = ("abc123");

		driver.findElement(USERNAME_FIELD_Locator).sendKeys(loginId);
		driver.findElement(PASSWORD_FIELD_Locator).sendKeys(passWord);
		driver.findElement(SIGNIN_BUTTON_Locator).click();

		waitforElement(driver, 3, DASHBOARD_PAGE_Locator);

		String dashboardValidationText = driver.findElement(DASHBOARD_PAGE_Locator).getText();
		Assert.assertEquals("Dashboard", dashboardValidationText, "Wrong Page!!!");
	}
	@Test
	public void addCustomerTest() throws InterruptedException {

	// Element Library
	By USER_NAME_FIELD = By.id("username");
	By PASSWORD_FIELD = By.id("password");
	By SIGNIN_BUTTON = By.name("login");
	By DASHBOARD_PAGE_Locator = By.xpath(" //h2[contains(text(),'Dashboard ' )]");
	By CUSTOMERS_BUTTON = By.xpath("//span[contains(text(), 'Customers')]");
	By ADD_CUSTOMER_BUTTON = By.xpath("//a[contains(text(), 'Add Customer')]");
	By ADD_CONTACT_LOCATOR = By.xpath("//h5[contains(text(),'Add Contact')]");
	By FULL_NAME_FIELD = By.xpath("//input[@id='account']");
	By COMPANY_NAME_FIELD = By.xpath("//input[@id='company']");
	By EMAIL_FIELD = By.xpath("//input[@id='email']");
	By PHONE_FIELD = By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@id='address']");
	By CITY_FIELD = By.xpath("//input[@id='city']");
	By STATE_REGION_FIELD = By.xpath("//input[@id='state']");
	By ZIP_FIELD = By.xpath("//input[@id='zip']");
	By SUBMIT_BUTTON = By.xpath("//button[@class='btn btn-primary']");
	By LIST_CONTACTS_BUTTON = By.xpath("//a[contains(text(),'List Contacts')]");

	// Login Data
	String loginId = "demo@techfios.com";
	String password = "abc123";

	// Test Data
	String fullName = "Malaika Malaika";
	String companyName = "Techfios";
	String emailAddress = "Malaika@gmail.com";
	String phoneNumber = "2105667878";

	// Perform Login In
	driver.findElement(USER_NAME_FIELD).sendKeys(loginId);
	driver.findElement(PASSWORD_FIELD).sendKeys(password);
	driver.findElement(SIGNIN_BUTTON).click();

			// Validate Dashboard Page
			waitforElement(driver,3,DASHBOARD_PAGE_Locator);
			String dashboardValidationText = driver.findElement(DASHBOARD_PAGE_Locator).getText();
			Assert.assertEquals("Dashboard", dashboardValidationText, "Wrong Page!!!");
			
			driver.findElement(CUSTOMERS_BUTTON).click();
			driver.findElement(ADD_CUSTOMER_BUTTON).click();
			waitforElement(driver, 3, ADD_CONTACT_LOCATOR);
			
			// Generate random number
			Random rmd = new Random();
			int randomNum = rmd.nextInt(999);
			
			//filling out add customer form
			driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + randomNum );
			driver.findElement(EMAIL_FIELD).sendKeys(randomNum + emailAddress);
			
			
	}
	@AfterMethod
	public void tearDown() {

		driver.close();
		driver.quit();
	}

	public void waitforElement(WebDriver driver, int timeInSeconds, By locator) {
		// creating an explicit wait in a method
		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
}