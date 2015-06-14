package com.intentmedia.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageTest extends TestBase {
    @BeforeClass
    public void beforeTest() {
        this.driver = new FirefoxDriver();
    }

    @AfterClass
    public void afterTest() {
        this.driver.close();
    }

    @Test
    @Override
    public void hasCorrectTitle() {
        super.hasCorrectTitle();
    }

    @Test
    @Override
    public void isLogoPresent() {
        super.isLogoPresent();
    }

    @Test
    public void emailBoxVisible() {
        this.driver.get(PIZZA_BASE_URL);
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
        this.driver.get(PIZZA_BASE_URL);
        WebElement email = this.wait(By.id("user_session_email"));
        String longEmail = "aLongEmaiLargedThanThirtyChars@longDomainName.com";
        email.sendKeys(longEmail);
        String inputEmail = email.getAttribute("value");
        assertNotEquals(longEmail, inputEmail);
        assertEquals(inputEmail.length(), 30);
    }

    @Test
    public void passwordBoxVisible() {
        this.driver.get(PIZZA_BASE_URL);
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
    public void createNewUserLinkPresent() {
        this.driver.get(PIZZA_BASE_URL);
        WebElement newUserLink = this.wait(By.linkText("Create a new user"));
        assertNotNull(newUserLink);
        String href = newUserLink.getAttribute("href");
        assertNotNull(href);
        assertEquals(href, PIZZA_BASE_URL + "/users/new");
    }

    @Test
    public void createNewUserLinkGoesToNewUserPage() {
        this.driver.get(PIZZA_BASE_URL);
        WebElement newUserLink = this.wait(By.linkText("Create a new user"));
        assertNotNull(newUserLink);
        newUserLink.click();
        WebElement h1 = this.wait(By.xpath("/html/body/h1")); // /html/body/h1
        assertEquals(h1.getText(), "Register");
    }

    @Test
    public void canSubmitUsernamePassword() {
        this.driver.get(PIZZA_BASE_URL);
        this.login();
        WebElement p = this.wait(By.xpath("//p[1]"));
        String pText = p.getText();
        assertNotNull(pText);
        assertTrue(pText.contains(TEST_EMAIL));
    }

    @Test
    public void invalidEmailFails() {
        this.driver.get(PIZZA_BASE_URL);
        this.login(TEST_EMAIL+"z", TEST_PASSWORD);
        this.testLoginErrors("Email is not valid");
    }

    @Test
    public void invalidPasswordFails() {
        this.driver.get(PIZZA_BASE_URL);
        this.login(TEST_EMAIL, TEST_PASSWORD+"*");
        this.testLoginErrors("Password is not valid");
    }
    
    @Test
    public void emptyEmailFails() {
        this.driver.get(PIZZA_BASE_URL);
        this.login("","X");
        this.testLoginErrors("Email cannot be blank");
    }

    @Test
    public void emptyPasswordFails() {
        this.driver.get(PIZZA_BASE_URL);
        this.login("X","");
        this.testLoginErrors("Password cannot be blank");
    }

    private void testLoginErrors(String msg) {
        WebElement error = this.wait(By.id("error_explanation"));
        assertNotNull(error);
        WebElement li = error.findElement(By.xpath("ul/li[1]"));
        assertNotNull(li);
        String errorMsg = li.getText();
        assertNotNull(errorMsg);
        assertTrue(errorMsg.contains(msg));
    }
}
