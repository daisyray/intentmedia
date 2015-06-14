package com.intentmedia.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class OrderPizzaPageTest {
    private TestUtils utils;
    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        this.driver = new FirefoxDriver();
        this.utils = new TestUtils();
        this.utils.login(driver);
    }

    @AfterClass
    public void afterClass() {
        this.driver.close();
    }

    @BeforeMethod
    public void beforeMethod() {
        this.driver.get(TestUtils.PIZZA_BASE_URL + "/pizzas/new");
    }

    @Test
    public void hasCorrectTitle() {
        this.driver.get(TestUtils.PIZZA_BASE_URL);
        String title = this.driver.getTitle();
        assertNotNull(title);
        assertEquals(title, "Intent Pizza");
    }

    @Test
    public void isLogoPresent() {
        this.driver.get(TestUtils.PIZZA_BASE_URL);
        WebElement logo = this.utils.waitForElement(driver, By.xpath("//img[1]"));
        assertNotNull(logo);
        String logoUrl = logo.getAttribute("src");
        assertNotNull(logoUrl);
        assertEquals(logoUrl, TestUtils.PIZZA_BASE_URL + "/images/intent_pizza_logo.jpg");
    }

    @Test
    public void newOrderFormIsGood() {
        WebElement form = this.driver.findElement(By.id("new_pizza"));
        String method = form.getAttribute("method");
        assertNotNull(method);
        assertEquals(method, "post");
        String action = form.getAttribute("action");
        assertNotNull(action);
        assertEquals(action, TestUtils.PIZZA_BASE_URL + "/pizzas");
    }

    @Test
    public void nameLabelIsPresent() {
        WebElement label = this.driver.findElement(By.xpath("//form[@id='new_pizza']/p[1]/label"));
        assertNotNull(label);
        assertEquals(label.getText(), "Name");
        String forLabel = label.getAttribute("for");
        assertNotNull(forLabel);
        assertEquals(forLabel, "pizza_name");
    }

    @Test
    public void sizeLabelIsPresent() {
        WebElement label = this.driver.findElement(By.xpath("//form[@id='new_pizza']/p[2]/label"));
        assertNotNull(label);
        assertEquals(label.getText(), "Size");
        String forLabel = label.getAttribute("for");
        assertNotNull(forLabel);
        assertEquals(forLabel, "pizza_size");
    }

    @Test
    public void hasCorrectHeading() {
        WebElement h1 = this.driver.findElement(By.xpath("//h1[1]"));
        assertNotNull(h1);
        String text = h1.getText();
        assertEquals(text, "New pizza");
    }

    @Test
    public void hasNameBox() {
        WebElement nameBox = this.driver.findElement(By.id("pizza_name"));
        assertNotNull(nameBox);
        assertTrue(nameBox.isDisplayed());
        String size = nameBox.getAttribute("size");
        assertEquals(size, "30");
    }

    @Test
    public void nameBoxMaxLengthIsConstrained() {
        WebElement nameBox = this.driver.findElement(By.id("pizza_name"));
        nameBox.sendKeys("0123456789012345678901234567890123456789");
        String value = nameBox.getAttribute("value");
        assertNotNull(value);
        assertTrue(value.length() <= 30);
        assertEquals(value, "012345678901234567890123456789");
    }

    @Test
    public void nameIsMandatory() {
        WebElement sizeBox = this.driver.findElement(By.id("pizza_size"));
        sizeBox.sendKeys("large");
        WebElement commit = this.driver.findElement(By.name("commit"));
        commit.click();
        WebElement error = utils.waitForElement(driver, By.id("error_explanation"));
        assertNotNull(error);
        assertEquals(error.getText(), "name can not be empty");
        assertEquals(this.driver.getCurrentUrl(), TestUtils.PIZZA_BASE_URL + "/pizzas/new");
    }

    @Test
    public void nameAcceptsOnlyAlpha() {
        WebElement nameBox = this.driver.findElement(By.id("pizza_name"));
        nameBox.sendKeys("abc*****");
        String value = nameBox.getAttribute("value");
        assertNotNull(value);
        assertFalse(value.contains("*"));
        assertEquals(value, "abc");

        nameBox.sendKeys("ABC*****");
        value = nameBox.getAttribute("value");
        assertNotNull(value);
        assertFalse(value.contains("*"));
        assertEquals(value, "ABC");
   }

    @Test
    public void hasSizeBox() {
        WebElement sizeBox = this.driver.findElement(By.id("pizza_size"));
        assertNotNull(sizeBox);
        assertTrue(sizeBox.isDisplayed());
        String size = sizeBox.getAttribute("size");
        assertEquals(size, "30");
    }

    @Test
    public void sizeIsMandatory() {
        WebElement nameBox = this.driver.findElement(By.id("pizza_size"));
        nameBox.sendKeys("cheese");
        WebElement commit = this.driver.findElement(By.name("commit"));
        commit.click();
        WebElement error = utils.waitForElement(driver, By.id("error_explanation"));
        assertNotNull(error);
        assertEquals(error.getText(), "size can not be empty");
        assertEquals(this.driver.getCurrentUrl(), TestUtils.PIZZA_BASE_URL + "/pizzas/new");
    }

    @Test
    public void sizeBoxMaxLengthIsConstrained() {
        WebElement sizeBox = this.driver.findElement(By.id("pizza_size"));
        sizeBox.sendKeys("0123456789012345678901234567890123456789");
        String value = sizeBox.getAttribute("value");
        assertNotNull(value);
        assertTrue(value.length() <= 30);
        assertEquals(value, "012345678901234567890123456789");
    }

    @Test
    public void sizeAcceptsAlphaNumeric() {
        WebElement nameBox = this.driver.findElement(By.id("pizza_size"));
        nameBox.sendKeys("abc123*");
        String value = nameBox.getAttribute("value");
        assertNotNull(value);
        assertFalse(value.contains("*"));
        assertEquals(value, "abc123");
    }

    @Test
    public void canSubmitPizzaOrder() {
        WebElement nameBox = this.driver.findElement(By.id("pizza_name"));
        nameBox.sendKeys("cheese");
        WebElement sizeBox = this.driver.findElement(By.id("pizza_size"));
        sizeBox.sendKeys("large");
        WebElement commit = this.driver.findElement(By.name("commit"));
        commit.click();
        utils.waitForElement(driver, By.linkText("Add toppings"));

        // Did we goto next page?
        List<WebElement> ps = this.driver.findElements(By.tagName("p"));
        assertNotNull(ps);
        assertTrue(ps.size() >= 3);
        WebElement name = ps.get(0);
        assertNotNull(name);
        assertTrue(name.getText().contains("cheese"));
    }

    @Test
    public void gotoNewPizzaPageWithoutLoginShouldBeRedirected() {
        this.driver.get(TestUtils.PIZZA_BASE_URL + "/pizzas/new");
        String url = this.driver.getCurrentUrl();
        assertNotNull(url);
        assertEquals(url, TestUtils.PIZZA_BASE_URL);
    }
}
