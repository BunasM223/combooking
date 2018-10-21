package com.booking.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestTask {

    private WebDriver driver;
    private static final String URL = "https://www.booking.com/";

    @BeforeTest
    public void setUp() throws Exception{
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void clearCache(){
        driver.manage().deleteAllCookies();
        driver.get(URL);
    }
    @Test
    public void searchNotRegion(){
        OpenPage Open = PageFactory.initElements(driver,OpenPage.class);
        Open.setRegion("");
        Open.setDate(LocalDate.now(), LocalDate.now().plusDays(2));
        PageHotels hotelsPage = Open.search();
        Assert.assertTrue(hotelsPage.isErrorMessageDisplayed());
    }

    @Test
    public void searchOldDate() {
        OpenPage Open = PageFactory.initElements(driver,OpenPage.class);
        Open.setRegion("Minsk");
        Open.setDate(LocalDate.of(2018, 10, 10), LocalDate.of(2014, 10, 10));
        PageHotels hotelsPage = Open.search();
        Assert.assertTrue(hotelsPage.isErrorMessageDisplayed());
    }

    @Test
    public void searchApartment() {
        OpenPage Open = PageFactory.initElements(driver, OpenPage.class);
        Open.setRegion("Minsk");
        Open.setDate(LocalDate.of(2018, 10, 27 ), LocalDate.of(2018, 10, 28));
        PageHotels pageHotels = Open.search();
        List<String> hotelNames = pageHotels.getHotelNames();
        Assert.assertFalse(hotelNames.isEmpty());
    }


    @Test
    public void cheapApartment() {
        OpenPage Open = PageFactory.initElements(driver, OpenPage.class);
        Open.setRegion("Minsk");
        Open.setDate(LocalDate.of(2018, 10, 27 ), LocalDate.of(2018, 10, 28));
        PageHotels pageHotels = Open.search();
        System.out.println(pageHotels.selectCheapHotels().getHotelNames());
        pageHotels.unselectCheapHotels();

    }
    @Test
    public void ExpensiveApartment(){
        OpenPage Open = PageFactory.initElements(driver, OpenPage.class);
        Open.setRegion("Minsk");
        Open.setDate(LocalDate.of(2018, 10, 27 ), LocalDate.of(2018, 10, 28));
        PageHotels pageHotels = Open.search();
        System.out.println(pageHotels.selectExpensiveHotels().getHotelNames());
        pageHotels.unselectExpensiveHotels();

    }

    @Test
    public void DealsApartment(){
        OpenPage Open = PageFactory.initElements(driver, OpenPage.class);
        Open.setRegion("Minsk");
        Open.setDate(LocalDate.of(2018, 10, 27 ), LocalDate.of(2018, 10, 28));
        PageHotels pageHotels = Open.search();
        System.out.println(pageHotels.selectDealsHotels().getHotelNames());
        pageHotels.unselectDealsHotels();

    }


    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
