package haelysan.swaglabs;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import haelysan.Config;
import io.qameta.allure.Feature;

@Feature("Products Feature")
public class SwaglabsProductPageTests extends SwaglabsTestBase {

    
    @Test(description = "Verify if Product page loads correctly", priority = 1, enabled = true, dependsOnGroups = "login")
    public void loadProductPage_successful() {
	
	login(driver, wait, Config.PROP_PROBLEM_USERNAME, Config.PROP_PASSWORD);
	Assert.assertTrue(isLoginSuccessful(driver, wait));
	verifyProductPage(driver, wait, Config.XPATH_PRODUCTS_PAGE_MENU_ITEM_IMAGE,
		Config.SRC_PRODUCTS_PAGE_MENU_ITEM_IMAGE);
	logout(driver, wait);
    }

}
