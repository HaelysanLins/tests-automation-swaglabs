package haelysan.swaglabs;

import org.testng.Assert;
import org.testng.annotations.Test;

import haelysan.Config;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;

@Feature("Login Feature")
public class SwaglabsLoginTests extends SwaglabsTestBase {

    @Test(description = "Verify if a user will be able to login with a valid username and valid password (usermane: standard_user)", groups = "login", priority = 1, enabled = true)
    public void login_standardUser_success() {
	login(driver, wait, props.getProperty(Config.PROP_STANDARD_USERNAME), props.getProperty(Config.PROP_PASSWORD));
	Assert.assertTrue(isLoginSuccessful(driver, wait));
	logout(driver, wait);
    }

    @Test(description = "Verify if a user will be able to login with a blocked user and can't access (usermane: locked_out_user)", groups = "login", priority = 1, enabled = true)
    public void login_lockedOutUser_unsuccessful() {
	login(driver, wait, props.getProperty(Config.PROP_LOCKED_OUT_USERNAME),
		props.getProperty(Config.PROP_PASSWORD));
	Assert.assertFalse(isLoginSuccessful(driver, wait));
    }

    @Test(description = "Verify if a user will be able to login with a problem user. (usermane: problem_user)", groups = "login", priority = 1, enabled = true)
    public void login_problemUser_success() {
	login(driver, wait, props.getProperty(Config.PROP_PROBLEM_USERNAME), props.getProperty(Config.PROP_PASSWORD));
	Assert.assertTrue(isLoginSuccessful(driver, wait));
	logout(driver, wait);
    }
    
    @Flaky
    @Test(description = "Login Performance Glitch User", groups = "login", priority = 1, enabled = true)
    public void login_performanceGlitchUser_unsuccessful() {
	login(driver, wait, props.getProperty(Config.PROP_PERFORMANCE_USERNAME),
		props.getProperty(Config.PROP_PASSWORD));
	Assert.assertTrue(isLoginSuccessful(driver, wait));
	logout(driver, wait);
    }

    @Test(description = "Verify if a user cannot login with a valid username and an invalid password and Verify the messages for invalid login.", groups = "login", priority = 1, enabled = true)
    public void login_validUserInvalidPassword_unsuccessful() {
	login(driver, wait, props.getProperty(Config.PROP_STANDARD_USERNAME),
		props.getProperty(Config.PROP_INVALID_PASSWORD));
	Assert.assertFalse(isLoginSuccessful(driver, wait));
    }

    @Test(description = "Verify if a user cannot login with a invalid username and an invalid password and Verify the messages for invalid login.", groups = "login", priority = 1, enabled = true)
    public void login_invalidUserInvalidPassword_unsuccessful() {
	login(driver, wait, props.getProperty(Config.PROP_INVALID_USERNAME),
		props.getProperty(Config.PROP_INVALID_PASSWORD));
	Assert.assertFalse(isLoginSuccessful(driver, wait));
    }

    @Test(description = "Login Problem User", groups = "login", priority = 1, enabled = true)
    public void login_invalidUserValidPassword_unsuccessful() {
	login(driver, wait, props.getProperty(Config.PROP_INVALID_USERNAME), props.getProperty(Config.PROP_PASSWORD));
	Assert.assertFalse(isLoginSuccessful(driver, wait));
    }

    @Test(description = "Verify the login page for both, when the username and password fields are blank and Login button is clicked and Verify the messages for invalid login.", groups = "login", priority = 1, enabled = true)
    public void login_blankUserBlankPassword_unsucessful() {
	login(driver, wait, " ", " ");
	Assert.assertFalse(isLoginSuccessful(driver, wait));
    }

    @Test(description = "Verify the login page for both, when the username field is blank and Login button is clicked and Verify the messages for invalid login.", groups = "login", priority = 1, enabled = true)
    public void login_blankUserValidPassword_unsuccessful() {
	login(driver, wait, " ", props.getProperty(Config.PROP_PASSWORD));
	Assert.assertFalse(isLoginSuccessful(driver, wait));
    }

    @Test(description = "Verify the login page for both, when the password field is blank and Login button is clicked and Verify the messages for invalid login.", groups = "login", priority = 1, enabled = true)
    public void login_validUserBlankPassword_unsuccessful() {
	login(driver, wait, props.getProperty(Config.PROP_STANDARD_USERNAME), "ghjkll;k/j,hgfndghjk");
	Assert.assertFalse(isLoginSuccessful(driver, wait));
    }

}