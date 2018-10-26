package com.booking.test;

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
    @Test(description = "Message about the location",groups = {"error"})
    public void searchNotRegionTest(){
        OpenPage openPage = PageFactory.initElements(driver,OpenPage.class);
        openPage.setRegion("");
        openPage.setDate(LocalDate.now(), LocalDate.now().plusDays(2));
        PageHotels hotelsPage = openPage.search();
        Assert.assertTrue(hotelsPage.isErrorMessageDisplayed());
    }

    @Test (description = "Message incorrect date",groups = {"error"})
        public void searchOldDateTest() {
        OpenPage openPage = PageFactory.initElements(driver,OpenPage.class);
        FileWriteRead fwr = new FileWriteRead();
        fwr =  fwr.readDataFromFile();
        openPage.setRegion(fwr.cityFile);
        openPage.setDate(LocalDate.of(fwr.yearInFile,fwr.monthInFile,fwr.monthDayInFile),LocalDate.of(fwr.yearOldOutFile,fwr.monthOutFile,fwr.monthDayOutFile));
        PageHotels hotelsPage = openPage.search();
        Assert.assertTrue(hotelsPage.isErrorMessageDisplayed());
    }

    @Test (description = "Page with hotels without sorting",dependsOnGroups = {"error"})
    public void searchApartmentTest() {
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        FileWriteRead fwr = new FileWriteRead();
        fwr =  fwr.readDataFromFile();
        openPage.setRegion(fwr.cityFile);
        openPage.setDate(LocalDate.of(fwr.yearInFile,fwr.monthInFile,fwr.monthDayInFile),LocalDate.of(fwr.yearOutFile,fwr.monthOutFile,fwr.monthDayOutFile));
        PageHotels pageHotels = openPage.search();
        List<String> hotelNames = pageHotels.getHotelNames();
        Assert.assertFalse(hotelNames.isEmpty());
    }

   @Test (description = "Cheap hotels",dependsOnMethods = {"searchApartmentTest"})
   public void cheapApartmentTest() {
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        FileWriteRead fwr = new FileWriteRead();
        fwr =  fwr.readDataFromFile();
        openPage.setRegion(fwr.cityFile);
        openPage.setDate(LocalDate.of(fwr.yearInFile,fwr.monthInFile,fwr.monthDayInFile),LocalDate.of(fwr.yearOutFile,fwr.monthOutFile,fwr.monthDayOutFile));
        PageHotels pageHotels = openPage.search();
        System.out.println(pageHotels.selectCheapHotels().getHotelNames());
        pageHotels.unselectCheapHotels();

    }

    @Test(description = "Expensive hotels",dependsOnMethods = {"cheapApartmentTest"})
    public void expensiveApartmentTest(){
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        FileWriteRead fwr = new FileWriteRead();
        fwr =  fwr.readDataFromFile();
        openPage.setRegion(fwr.cityFile);
        openPage.setDate(LocalDate.of(fwr.yearInFile,fwr.monthInFile,fwr.monthDayInFile),LocalDate.of(fwr.yearOutFile,fwr.monthOutFile,fwr.monthDayOutFile));
        PageHotels pageHotels = openPage.search();
        System.out.println(pageHotels.selectExpensiveHotels().getHotelNames());
        pageHotels.unselectExpensiveHotels();

    }

    @Test (description = "Best deal",dependsOnMethods = {"expensiveApartmentTest"})
    public void dealsApartmentTest() throws InterruptedException {
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        FileWriteRead fwr = new FileWriteRead();
        fwr =  fwr.readDataFromFile();
        openPage.setRegion(fwr.cityFile);
        openPage.setDate(LocalDate.of(fwr.yearInFile,fwr.monthInFile,fwr.monthDayInFile),LocalDate.of(fwr.yearOutFile,fwr.monthOutFile,fwr.monthDayOutFile));
        PageHotels pageHotels = openPage.search();
        System.out.println(pageHotels.selectDealsHotels().getHotelNames());
        pageHotels.unselectDealsHotels();
    }

   @Test (description = "Best deal (break)",dependsOnMethods = {"dealsApartmentTest"},timeOut = 120010)
   public void dealsApartmentTest2() throws InterruptedException {
        OpenPage openPage = PageFactory.initElements(driver, OpenPage.class);
        Thread.sleep(120020);
        FileWriteRead fwr = new FileWriteRead();
        fwr =  fwr.readDataFromFile();
        openPage.setRegion(fwr.cityFile);
        openPage.setDate(LocalDate.of(fwr.yearInFile,fwr.monthInFile,fwr.monthDayInFile),LocalDate.of(fwr.yearOutFile,fwr.monthOutFile,fwr.monthDayOutFile));
        PageHotels pageHotels = openPage.search();
        System.out.println(pageHotels.selectDealsHotels().getHotelNames());
        pageHotels.unselectDealsHotels();
    }

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
