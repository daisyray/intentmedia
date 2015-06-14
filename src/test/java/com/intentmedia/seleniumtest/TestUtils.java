package com.intentmedia.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class TestUtils {
    public static final String PIZZA_BASE_URL = "http://intent-pizza.internal.intentmedia.net:8080";
    public static final long FIVE_SECS = 5 * 1000;
    public static final int DEFAULT_WAIT = 10;
    public static final String TEST_EMAIL = "g5223758@trbvm.com";
    public static final String TEST_PASSWORD = "test1234";

    public void hasCorrectTitle(WebDriver driver) {
        driver.get(PIZZA_BASE_URL);
        String title = driver.getTitle();
        assertNotNull(title);
        assertEquals(title, "Intent Pizza");
    }

    public void isLogoPresent(WebDriver driver) {
        driver.get(PIZZA_BASE_URL);
        WebElement logo = this.waitForElement(driver, By.xpath("//img[1]"));
        assertNotNull(logo);
        String logoUrl = logo.getAttribute("src");
        assertNotNull(logoUrl);
        assertEquals(logoUrl, PIZZA_BASE_URL + "/images/intent_pizza_logo.jpg");
    }

    protected WebElement waitForElement(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(by);
    }

    public void login(WebDriver driver) {
        this.login(driver, TEST_EMAIL, TEST_PASSWORD);
    }

    protected void login(WebDriver driver, String testEmail, String passwordString) {
        driver.get(PIZZA_BASE_URL);
        WebElement email = driver.findElement(By.id("user_session_email"));
        WebElement password = this.waitForElement(driver, By.id("user_session_password"));
        email.sendKeys(testEmail);
        password.sendKeys(passwordString);
        WebElement loginBtn = driver.findElement(By.name("commit"));
        assertNotNull(loginBtn);
        loginBtn.click();
    }

    public void sleep() {
        try {
            Thread.sleep(FIVE_SECS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
