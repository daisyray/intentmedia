package com.intentmedia.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class OrderPizzaPageTest extends TestBase {
    @BeforeClass
    public void beforeClass() {
        this.driver = new FirefoxDriver();
        this.login();
        this.sleep();
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
    public void hasCorrectHeading() {
        WebElement h1 = this.driver.findElement(By.xpath("//h1[1]"));
        assertNotNull(h1);
        String text = h1.getText();
        assertEquals(text, "New Pizza");
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
    public void hasSizeBox() {
        WebElement sizeBox = this.driver.findElement(By.id("pizza_size"));
        assertNotNull(sizeBox);
        assertTrue(sizeBox.isDisplayed());
        String size = sizeBox.getAttribute("size");
        assertEquals(size, "30");
    }

    @Test
    public void nameIsMandatory() {
        WebElement sizeBox = this.driver.findElement(By.id("pizza_size"));
        sizeBox.sendKeys("large");
        WebElement commit = this.driver.findElement(By.name("commit"));
        commit.click();
        WebElement error = this.wait(By.id("error_explanation"));
        assertNotNull(error);
        assertEquals(error.getText(), "name can not be empty");
        this.sleep();
        assertEquals(this.driver.getCurrentUrl(), PIZZA_BASE_URL + "/pizzas/new");
    }

    @Test
    public void sizeIsMandatory() {
        WebElement nameBox = this.driver.findElement(By.id("pizza_name"));
        nameBox.sendKeys("cheese");
        WebElement commit = this.driver.findElement(By.name("commit"));
        commit.click();
        WebElement error = this.wait(By.id("error_explanation"));
        assertNotNull(error);
        assertEquals(error.getText(), "size can not be empty");
        this.sleep();
        assertEquals(this.driver.getCurrentUrl(), PIZZA_BASE_URL + "/pizzas/new");
    }

    @Test
    public void canSubmitPizzaOrder() {
        WebElement nameBox = this.driver.findElement(By.id("pizza_name"));
        nameBox.sendKeys("cheese");
        WebElement sizeBox = this.driver.findElement(By.id("pizza_size"));
        sizeBox.sendKeys("large");
        WebElement commit = this.driver.findElement(By.name("commit"));
        commit.click();
        this.wait(By.linkText("Add toppings"));
    }
}
