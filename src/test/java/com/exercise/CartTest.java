package com.exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

public class CartTest extends LogInLogOutTest{
    // Kika se nadevam ne e problem ama si vezbav vikendov i si napraviv suite so poveke test kejsovi i na poveke funkcionalnosti :)

    @Test(priority = 2)
    public void verifyAddItemToCartHomepage () {
        //Adding item to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        //Verifying the added item to cart
        String actualNumberOfItemsInCart = "1";
        String expectedNumberOfItemsInCart = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']")).getText();
        Assert.assertEquals(actualNumberOfItemsInCart, expectedNumberOfItemsInCart);
    }

    @Test (priority = 3)
    public void verifyRemoveItemFromCartHomepage() {
        //Removing Item from cart
        driver.findElement(By.xpath("//button[text()='Remove']")).click();

        //Verifying that the item has been removed
        String actualNumberOfItemsInCart = driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).getText();
        String expectedNumberOfItemsInCart = "";
        Assert.assertEquals(actualNumberOfItemsInCart, expectedNumberOfItemsInCart);
    }

    @Test(priority = 4)
    public void verifyRemoveItemCartPage() throws InterruptedException {

        //Adding item to cart again
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        //Navigating to the cart page
        driver.findElement(By.xpath("//div[starts-with(@id, 'shopping')]")).click();

        //Verify that the user is navigated to Cart Page
        String expectedPageTitle = "Your Cart";
        String actualPageTitle = driver.findElement(By.xpath("//span[@class='title']")).getText();
        Assert.assertEquals(actualPageTitle, expectedPageTitle);

        //Verifying that there is an item in the cart
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String expectedItemsInCart = "1";
        String actualItemsInCart = driver.findElement(By.xpath("//div[@class='cart_quantity']")).getText();
        Assert.assertEquals(actualItemsInCart, expectedItemsInCart);

        //Removing item from cart
        Thread.sleep(1000);    //Ova go staviv za da si gledam shto se slucuva, iako ne treba i raboti bez ova :)
        driver.findElement(By.xpath("//button[contains(@id, 'remove')]")).click();

        //Verifying that the item has been removed
        String removedCartItem = driver.findElement(By.xpath("//div[@class='removed_cart_item']")).getText();
        String actualCartItem = "";
        Assert.assertEquals(actualCartItem, removedCartItem);
    }

    @Test(priority = 5)
    public void backToHomepage () {
        //Navigating back to homepage
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.id("continue-shopping")).click();

        //Verifying that we are navigated back to homepage
        String expectedURL = "https://www.saucedemo.com/inventory.html";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);
    }
}
