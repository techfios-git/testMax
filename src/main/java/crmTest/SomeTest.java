package crmTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class SomeTest {
	WebDriver driver;
	String browser = null;

	@BeforeTest
	public void readPropertiesFile() {
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("..\\Test\\src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void launchBrowser() {

		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", ".\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();

		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// get url
		driver.get("http://techfios.com/test/billing/?ng=admin/");

	}

	//@Test(priority = 1)
	public void loginTest() {

		// Element Library
		By USERNAME_FIELD = By.xpath("(//input[@class='form-control'])[1]");
		By PASSWORD_FIELD = By.xpath("//input[contains(@type, 'pass')]");
		By SIGN_BUTTON = By.xpath("//*[contains(@class, 'success block') and @type='submit']");
		By DASHBOARD_PAGE_TITLE = By.xpath("//span[contains(text(), 'Dashboard')]");

		String loginId = "techfiosdemo@gmail.com";
		String password = "abc123";

		// Login in
		driver.findElement(USERNAME_FIELD).sendKeys(loginId);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGN_BUTTON).click();

		// Validate dashboard page
		WebElement dashboardPageTitle = driver.findElement(DASHBOARD_PAGE_TITLE);
		Assert.assertEquals("Wrong page!!", "Dashboard", dashboardPageTitle.getText());
	}

	@Test(priority = 2)
	public void addAndVerifyDepositTest() throws InterruptedException {

		// Element Library
		By USERNAME_FIELD = By.xpath("(//input[@class='form-control'])[1]");
		By PASSWORD_FIELD = By.xpath("//input[contains(@type, 'pass')]");
		By SIGN_BUTTON = By.xpath("//*[contains(@class, 'success block') and @type='submit']");
		By DASHBOARD_PAGE_TITLE = By.xpath("//span[contains(text(), 'Dashboard')]");
		By CRM_BUTTON = By.xpath("//span[contains(text(), 'CRM')]");
		By ADD_CONTACT_BUTTON = By.xpath("//a[contains(text(), 'Add Contact')]");
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
		
		String loginId = "techfiosdemo@gmail.com";
		String password = "abc123";

		// Test Data
		String fullName = "Tom Cruise";
		String companyName = "Techfios";
		String emailId = "techfios@hotmail.com";
		String phoneNumber =  "12312142";
		String address = "213 beltline road";
		String city = "Carrolton";
		String state = "TX";
		String zip = "75030";

		// Login in
		driver.findElement(USERNAME_FIELD).sendKeys(loginId);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGN_BUTTON).click();

		// Validate dashboard page
		WebElement dashboardPageTitle = driver.findElement(DASHBOARD_PAGE_TITLE);
		Assert.assertEquals("Wrong page!!", "Dashboard", dashboardPageTitle.getText());
		waitForElement(driver, 2, CRM_BUTTON);

		// Go to Add Contact
		driver.findElement(CRM_BUTTON).click();
		waitForElement(driver, 2, ADD_CONTACT_BUTTON);
		driver.findElement(ADD_CONTACT_BUTTON).click();

		// Fill out add contact form
		Random rnd = new Random();
		int randomNumber = rnd.nextInt(999);
		
		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + randomNumber);
		driver.findElement(COMPANY_NAME_FIELD).sendKeys(companyName + randomNumber);
		driver.findElement(EMAIL_FIELD).sendKeys(emailId);
		driver.findElement(PHONE_FIELD).sendKeys(phoneNumber);
		driver.findElement(ADDRESS_FIELD).sendKeys(address);
		driver.findElement(CITY_FIELD).sendKeys(city);
		driver.findElement(STATE_REGION_FIELD).sendKeys(state);
		driver.findElement(ZIP_FIELD).sendKeys(zip);
		driver.findElement(SUBMIT_BUTTON).click();
		
		//Validate added contact on list contact
		Thread.sleep(5000);
		driver.findElement(LIST_CONTACTS_BUTTON).click();

	}

	//@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

	public void waitForElement(WebDriver driver, int timeInSeconds, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}
