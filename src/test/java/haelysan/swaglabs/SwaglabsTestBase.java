package haelysan.swaglabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import haelysan.Config;
import haelysan.Base;
import haelysan.TestExecutionListener;
import io.qameta.allure.Step;

@Listeners(TestExecutionListener.class)
public abstract class SwaglabsTestBase extends Base {

    @Step
    protected void login(WebDriver driver, WebDriverWait wait, String usernameStr, String passwordStr) {
	try {
	    String pageTitle = driver.getTitle();
	    System.out.println("The title of this page is ===> " + pageTitle);

	    driver.get(props.getProperty(Config.PROP_LOGIN_PAGE));
	    verifyLoginPageExists(driver, wait);

	    enterValue(driver, wait, props.getProperty(Config.XPATH_LOGIN_USERNAME_INPUT), usernameStr);
	    enterValue(driver, wait, props.getProperty(Config.XPATH_LOGIN_PASSWORD_INPUT), passwordStr);

	    clickOnElement(driver, wait, props.getProperty(Config.XPATH_LOGIN_BTN));

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void verifyLoginPageExists(WebDriver driver, WebDriverWait wait) {
	try {
	    clickOnElement(driver, wait, props.getProperty(Config.PROS_LOGIN_LOGO));
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(props.getProperty(Config.XPATH_LOGIN_BTN))));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Step
    protected boolean isLoginSuccessful(WebDriver driver, WebDriverWait wait) {
	try {
	    String strUrl = driver.getCurrentUrl();
	    if (!strUrl.equals(props.getProperty(Config.URL_PRODUCTS_PAGE))) {
		String errorMessageElemDisplayed = driver.findElement(By.xpath(props.getProperty(Config.XPATH_LOGIN_ERROR_MSG))).getText().trim();
		System.out.println("Error Message Displayed is: " + errorMessageElemDisplayed);
		return false;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return true;
    }

    // TODO: improve logic to compare if all imgs are the same
    @Step
    protected void verifyProductPage(WebDriver driver, WebDriverWait wait, String srcImgXpath, String srcImgValue) {
	try {
	    String getImgAttribute = getAttributeValue(driver, wait, props.getProperty(srcImgXpath), "src");
	    Assert.assertNotNull(getImgAttribute);
	    Assert.assertEquals(getImgAttribute, props.getProperty(srcImgValue));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Step
    protected void logout(WebDriver driver, WebDriverWait wait) {
	clickOnElement(driver, wait, props.getProperty(Config.XPATH_PRODUCTS_PAGE_OPEN_MENU_BTN));
	wait.until(ExpectedConditions
		.elementToBeClickable(By.xpath(props.getProperty(Config.XPATH_PRODUCTS_PAGE_MENU_LOGOUT_LINK))));
	clickOnElement(driver, wait, props.getProperty(Config.XPATH_PRODUCTS_PAGE_MENU_LOGOUT_LINK));
    }
}