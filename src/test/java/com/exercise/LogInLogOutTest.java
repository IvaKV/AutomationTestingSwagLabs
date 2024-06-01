package com.exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogInLogOutTest {
    static WebDriver driver;

    @BeforeSuite
    public void logIn () {

        driver = new ChromeDriver();

        driver.get("http://saucedemo.com/");
        driver.manage().window().maximize();
        //Find username field using xPath ==> class AND type
        driver.findElement(By.xpath("//input[@class='input_error form_input' and @type='text']")).sendKeys("standard_user");

        //Find password field using xPath ==> starts with
        driver.findElement(By.xpath("//input[starts-with(@placeholder, 'Pass')]")).sendKeys("secret_sauce");

        //Find Submit button using name
        driver.findElement(By.name("login-button")).click();

        //Verifying Login
        String expectedURL = "https://www.saucedemo.com/inventory.html";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);

        //Verifying that the user is navigated to homepage
        String actualHomepageText = driver.findElement(By.xpath("//span[@class='title']")).getText();
        String expectedHomepageText = "Products";
        Assert.assertEquals(actualHomepageText, expectedHomepageText);
    }


    @Test(priority = 1)
    public void verifyFilterFunctionality () {
        //Clicking filter button
        driver.findElement(By.className("product_sort_container")).click();

        //Selecting from low to high
        driver.findElement(By.xpath("//option[@value='lohi']")).click();

        //Retrieving price from first item
        String lowerPrice = driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div[1]/div[2]/div[2]/div")).getText();
        lowerPrice = lowerPrice.replace("$", "");
        double lowPrice = Double.parseDouble(lowerPrice);     //Ova go vidov od google :)

        //Retrieving price from second item
        String higherPrice = driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div[6]/div[2]/div[2]/div")).getText();
        higherPrice = higherPrice.replace("$", "");
        double highPrice = Double.parseDouble(higherPrice);

        Assert.assertTrue(highPrice > lowPrice);
    }

    @AfterSuite
    public void logOut () {
        //Clicking Hamburger Menu button
        driver.findElement(By.id("react-burger-menu-btn")).click();

        //Clicking Log out
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();

        //Verifying that we are back to the Login page
        String expectedURL = "https://www.saucedemo.com/";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);
        //Closing window
        driver.quit();
    }
}
