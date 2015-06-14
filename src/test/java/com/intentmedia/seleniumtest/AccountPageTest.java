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

public class AccountPageTest extends TestBase {
    @BeforeClass
    public void beforeClass() {
        this.driver = new FirefoxDriver();
        this.login();
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
    public void canGotoAccountPageAfterLogin() {
        WebElement p = this.wait(By.xpath("//p[1]"));
        assertNotNull(p);
        String text = p.getText();
        assertNotNull(text);
        assertTrue(text.contains(TEST_EMAIL));
    }

    @Test
    public void hasOrderLink() {
        WebElement order = this.driver.findElement(By.linkText("Order a pizza"));
        assertNotNull(order);
        String href = order.getAttribute("href");
        assertNotNull(href);
        assertEquals(href, "/pizzas/new");
    }

    @Test
    public void hasShowOrdersLink() {
        WebElement order = this.driver.findElement(By.linkText("Show ordered pizzas"));
        assertNotNull(order);
        String href = order.getAttribute("href");
        assertNotNull(href);
        assertEquals(href, "/pizzas");
    }

    @Test
    public void hasEditLink() {
        WebElement order = this.driver.findElement(By.linkText("Edit"));
        assertNotNull(order);
        String href = order.getAttribute("href");
        assertNotNull(href);
        assertEquals(href, "/account/edit");
    }

    @Test
    public void linksAreInCorrectOrder() {
        List<WebElement> links = this.driver.findElements(By.tagName("a"));
        assertNotNull(links);
        assertEquals(links.size(), 3);
        assertEquals(links.get(0).getText(), "Order a pizza");
        assertEquals(links.get(1).getText(), "Show ordered pizzas");
        assertEquals(links.get(2).getText(), "Edit");
    }

    @Test
    public void hasLogoutLink() {
        WebElement logout = this.driver.findElement(By.linkText("Logout"));
        assertNotNull(logout);
        String href = logout.getAttribute("href");
        assertNotNull(href);
        assertEquals(href, "/account/logout");
    }
}
