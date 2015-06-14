package com.intentmedia.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class TestBase {
    public static final String PIZZA_BASE_URL = "http://intent-pizza.internal.intentmedia.net:8080";
    private static final long FIVE_SECS = 5 * 1000;
    protected WebDriver driver;
    public static final int DEFAULT_WAIT = 10;
    public static final String TEST_EMAIL = "g5223758@trbvm.com";
    public static final String TEST_PASSWORD = "test1234";

    public void hasCorrectTitle() {
        this.driver.get(PIZZA_BASE_URL);
        String title = this.driver.getTitle();
        assertNotNull(title);
        assertEquals(title, "Intent Pizza");
    }

    public void isLogoPresent() {
        this.driver.get(PIZZA_BASE_URL);
        WebElement logo = this.wait(By.xpath("//img[1]"));
        assertNotNull(logo);
        String logoUrl = logo.getAttribute("src");
        assertNotNull(logoUrl);
        assertEquals(logoUrl, "/images/intent_pizza_logo.jpg");
    }

    protected WebElement wait(By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return this.driver.findElement(by);
    }

    protected void login() {
        this.login(TEST_EMAIL, TEST_PASSWORD);
    }

    protected void login(String testEmail, String passwordString) {
        this.driver.get(PIZZA_BASE_URL);
        WebElement email = this.driver.findElement(By.id("user_session_email"));
        WebElement password = this.wait(By.id("user_session_password"));
        email.sendKeys(testEmail);
        password.sendKeys(passwordString);
        WebElement loginBtn = this.driver.findElement(By.name("commit"));
        assertNotNull(loginBtn);
        loginBtn.click();
    }

    public void sleep() {
        try {
            Thread.sleep(FIVE_SECS);
            this.driver.get(PIZZA_BASE_URL+"/pizzas/new");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
