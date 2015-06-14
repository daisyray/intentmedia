package com.intentmedia.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class SeeOrderPageTest extends TestBase {
    @BeforeClass
    public void beforeClass() {
        this.driver = new FirefoxDriver();
        this.login();
        this.sleep();
        this.driver.get(PIZZA_BASE_URL + "/pizzas/new");
        WebElement name = this.wait(By.id("pizza_name"));
        WebElement size = this.driver.findElement(By.id("pizza_size"));
        name.sendKeys("cheese-test");
        size.sendKeys("large-large");
        this.driver.findElement(By.name("commit")).click();
    }

    @AfterClass
    public void afterTest() {
        this.driver.close();
    }

    @Test
    public void pageHasNameAndSize() {
        List<WebElement> ps = this.driver.findElements(By.tagName("p"));
        assertNotNull(ps);
        assertTrue(ps.size() >= 3);
        WebElement name = ps.get(0);
        assertNotNull(name);
        assertTrue(name.getText().contains("cheese-test"));

        WebElement size = ps.get(1);
        assertNotNull(size);
        assertTrue(size.getText().contains("large-large"));
    }

    @Test
    public void
}