package com.intentmedia.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HiringTest {
    private WebDriver driver;
    public static final int DEFAULT_WAIT = 10;

    @BeforeClass
    public void beforeTest() {
        this.driver = new FirefoxDriver();
    }

    @AfterClass
    public void afterTest() {
        this.driver.close();
    }

    @Test
    public void emailBoxVisible() {
        this.driver.get("http://intent-pizza.internal.intentmedia.net:8080");
        WebElement email = this.wait(By.id("user_session_email"));
        assertNotNull(email);
        assertTrue(email.isDisplayed());
        String name = email.getAttribute("name");
        assertNotNull(name);
        assertEquals(name, "user_session[email]");
        String size = email.getAttribute("size");
        assertNotNull(size);
        assertEquals(size, "30");        
    }

    /**
    ** Does email box limit it's input? It should not take arbitrary input size
    */
    @Test
    public void emailBoxMaxlengthConstrained() {
        this.driver.get("http://intent-pizza.internal.intentmedia.net:8080");
        WebElement email = this.wait(By.id("user_session_email"));
        String longEmail = "aLongEmaiLargedThanThirtyChars@longDomainName.com";
        email.sendKeys(longEmail);
        String inputEmail = email.getAttribute("value");
        assertNotEquals(longEmail, inputEmail);
        assertTrue(inputEmail.length(), 30);
    }

    @Test
    public void passwordBoxVisible() {
        this.driver.get("http://intent-pizza.internal.intentmedia.net:8080");
        WebElement password = this.wait(By.id("user_session_password"));
        assertNotNull(password);
        assertTrue(password.isDisplayed());
        String name = password.getAttribute("name");
        assertNotNull(name);
        assertEquals(name, "user_session[password]");
        String size = password.getAttribute("size");
        assertNotNull(size);
        assertEquals(size, "30");        
    }

    @Test
    public void canSubmitUsernamePassword() {
        this.driver.get("http://intent-pizza.internal.intentmedia.net:8080");
        WebElement email = this.driver.findElement(By.id("user_session_email"));
        WebElement password = this.wait(By.id("user_session_password"));
        String testEmail = "g5223758@trbvm.com";
        email.sendKeys(testEmail);
        password.sendKeys("test1234");
        WebElement loginBtn = this.driver.findElement(By.name("commit"));
        assertNotNull(loginBtn);
        loginBtn.click();
        WebElement p = this.wait(By.xpath("//p[1]"));
        String pText = p.getText();
        assertNotNull(pText);
        assertTrue(pText.contains(testEmail));
    }

    @Test
    public void invalidPasswordFails() {
        this.driver.get("http://intent-pizza.internal.intentmedia.net:8080");
        WebElement email = this.driver.findElement(By.id("user_session_email"));
        WebElement password = this.wait(By.id("user_session_password"));
        String testEmail = "g5223758@trbvm.com";
        email.sendKeys(testEmail);
        password.sendKeys("test12345"); 
        WebElement loginBtn = this.driver.findElement(By.name("commit"));
        assertNotNull(loginBtn);
        loginBtn.click();
        WebElement error = this.wait(By.id("error_explanation"));
        assertNotNull(error);
        WebElement li = error.findElement(By.xpath("ul/li[1]"));
        assertNotNull(li);
        String errorMsg = li.getText();
        assertNotNull(errorMsg);
        assert
    }
    
    
    private WebElement wait(By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return this.driver.findElement(by);
    }
}
