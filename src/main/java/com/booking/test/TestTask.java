package com.booking.test;

import com.sun.org.glassfish.gmbal.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Listeners({TestListener.class})
public class TestTask {

    public WebDriver getDriver(){
        return driver;
    }

    private WebDriver driver;
    private String city;

    private static final String URL = "https://www.booking.com/";

    @BeforeTest
    public void setUp() {

        FileWriteRead fwr = new FileWriteRead();
        fwr =  fwr.readDataFromFile();
        city = fwr.cityFile;
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @BeforeMethod
    public void clearCache() {
        driver.manage().deleteAllCookies();
        driver.get(URL);

    }
    @Description("Test searchNotRegionTest.")
    @Test(description = "Message about the location",groups = {"error"})
    public void searchNotRegionTest(){
        OpenPage openPage = PageFactory.initElements(driver,OpenPage.class);
        openPage.setRegion("");
        openPage.setDate(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        PageHotels hotelsPage = openPage.search();
        Assert.assertTrue(hotelsPage.isErrorMessageDisplayed());
    }

    @Description("Test searchOldDateTest.")
    @Test (description = "Message incorrect date",groups = {"error"})
        public void searchOldDateTest() {
        OpenPage openPage = PageFactory.initElements(driver,OpenPage.class);
        openPage.setRegion(city);
        PageHotels hotelsPage = openPage.search();
        Assert.assertTrue(hotelsPage.isErrorMessageDisplayed());
    }

    @Description("Test searchApartmentTest.")
    @Test (description = "Page with hotels without sorting",dependsOnGroups = {"error"})
    public void searchApartmentTest() {
        OpenPage openPage = PageFactory.initElements(driver,OpenPage.class);
        openPage.setRegion(city);
        openPage.setDate(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        PageHotels pageHotels = openPage.search();
        List<String> hotelNames = pageHotels.getHotelNames();
        Assert.assertFalse(hotelNames.isEmpty());
    }

    @Description("Test cheapApartmentTest.")
    @Test (description = "Cheap hotels",dependsOnMethods = {"searchApartmentTest"})
    public void cheapApartmentTest() {
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        openPage.setRegion(city);
        openPage.setDate(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        PageHotels pageHotels = openPage.search();
        System.out.println(pageHotels.selectCheapHotels().getHotelNames());
        pageHotels.unselectCheapHotels();

    }

    @Description("Test expensiveApartmentTest.")
    @Test(description = "Expensive hotels",dependsOnMethods = {"cheapApartmentTest"})
    public void expensiveApartmentTest(){
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        openPage.setRegion(city);
        openPage.setDate(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        PageHotels pageHotels = openPage.search();
        System.out.println(pageHotels.selectExpensiveHotels().getHotelNames());
        pageHotels.unselectExpensiveHotels();

    }

    @Description("Test dealsApartmentTest.")
    @Test (description = "Best deal",dependsOnMethods = {"expensiveApartmentTest"})
    public void dealsApartmentTest() {
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        openPage.setRegion(city);
        openPage.setDate(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        PageHotels pageHotels = openPage.search();
        System.out.println(pageHotels.selectDealsHotels().getHotelNames());
        pageHotels.unselectDealsHotels();
    }

    @Description("Test dealsApartmentTest2.")
    @Test (description = "Best deal (break)",dependsOnMethods = {"dealsApartmentTest"},timeOut = 120010)
    public void dealsApartmentTest2() throws InterruptedException {
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        Thread.sleep(120020);
        openPage.setRegion(city);
        openPage.setDate(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        PageHotels pageHotels = openPage.search();
        System.out.println(pageHotels.selectDealsHotels().getHotelNames());
        pageHotels.unselectDealsHotels();
    }

    @Description("Test expectedExceptions.")
    @Test (expectedExceptions = RuntimeException.class)
    public void test(){
        throw new RuntimeException();
    }


    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
    }

}
