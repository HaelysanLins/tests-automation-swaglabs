package haelysan;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class Base {

    static String driversFolderPath;
    protected Properties props;
    public static WebDriver driver;
    protected int waitTimeout = 0;
    protected WebDriverWait wait;

    @BeforeClass
    public void init() {
	props = loadProperties(ClassLoader.getSystemResource("properties/config.properties").getPath());
	waitTimeout = Integer.parseInt(props.getProperty(Config.PROP_WEBDRIVER_WAIT_TIMEOUT));
	driversFolderPath = ClassLoader.getSystemResource(props.getProperty(Config.PROP_DRIVERS)).getPath();
	driver = getDriver(props.getProperty(Config.PROP_TARGET_BROSWER), props.getProperty(Config.PROP_TARGET_DEVICE));
	wait = new WebDriverWait(driver, waitTimeout);
    }

    @AfterClass
    public void tearDown() {
	driver.quit();
    }
    
    private Properties loadProperties(String path) {
	Properties props = new Properties();
	try (InputStream input = new FileInputStream(path)) {
	    props.load(input);
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	return props;
    }

    private WebDriver getDriver(String targetBrowser, String device) throws IllegalArgumentException {
	WebDriver driver = null;
	if (targetBrowser.equalsIgnoreCase(props.getProperty(Config.PROP_CHROME))) {
	    System.setProperty(props.getProperty(Config.PROP_WEBDRIVER_CHROME),
		    driversFolderPath + props.getProperty(Config.PROP_CHROME_DRIVE));
	    driver = new ChromeDriver();
	} else if (targetBrowser.equalsIgnoreCase(props.getProperty(Config.PROP_FIREFOX))) {
	    System.setProperty(props.getProperty(Config.PROP_WEBDRIVER_FIREFOX),
		    driversFolderPath + props.getProperty(Config.PROP_FIREFOX_DRIVE));
	    driver = new FirefoxDriver();
	} else {
	    throw new IllegalArgumentException(props.getProperty(Config.MSG_INVALID_TARGET_BROSWER));
	}
	setWindowSize(driver, device);
	return driver;
    }

    private void scrollToElement(WebDriver driver, WebElement element) {
	((JavascriptExecutor) driver).executeScript(props.getProperty(Config.PROP_ARGUMENTS_SCROLLINTOWIEW), element);
    }

    private void clickOnHiddenElement(WebDriver driver, WebDriverWait wait, String xpath) {
	WebElement element = driver.findElement(By.xpath(xpath));
	((JavascriptExecutor) driver).executeScript(props.getProperty(Config.PROP_ARGUMENTS_CLICK), element);
    }

    public void clickOnElement(WebDriver driver, WebDriverWait wait, String xpath) {
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	WebElement element = driver.findElement(By.xpath(xpath));
	element.click();
    }

    public void enterValue(WebDriver driver, WebDriverWait wait, String xpath, String value) {
	WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	element.clear();
	element.sendKeys(value);
    }

    public String getAttributeValue(WebDriver driver, WebDriverWait wait, String xpath, String attribute) {
	String attributeValue = null;
	try {
	    WebElement element = driver.findElement(By.xpath(xpath));
	    attributeValue = element.getAttribute(attribute);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return attributeValue;
    }

    // TODO: implement DeviceType enum
    private void setWindowSize(WebDriver driver, String device) {
	int width, height = 0;
	if (device.equalsIgnoreCase(props.getProperty(Config.PROP_IPAD))) {
	    width = 1024;
	    height = 768;
	} else if (device.equalsIgnoreCase(props.getProperty(Config.PROP_IPAD_PRO))) {
	    width = 1024;
	    height = 1366;
	} else {
	    driver.manage().window().maximize();
	    return;
	}
	driver.manage().window().setSize(new Dimension(width, height));
    }
}