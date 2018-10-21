package com.booking.test;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;

public class OpenPage {

    private WebDriver driver;
    @FindBy(id="ss")
    private WebElement regionBox;
    @FindBy(name = "checkin_month")
    private WebElement monthin;
    @FindBy(name = "checkin_monthday")
    private WebElement monthdayin;
    @FindBy(name = "checkin_year")
    private WebElement yearin;
    @FindBy(name = "checkout_month")
    private WebElement monthout;
    @FindBy(name = "checkout_monthday")
    private WebElement monthdayout;
    @FindBy(name = "checkout_year")
    private WebElement yearout;
    @FindBy(xpath = "//button[@data-sb-id='main']")
    private WebElement searchButton;

    public OpenPage(WebDriver driver){
        this.driver =driver;
    }

    public void setRegion(String city) {
        regionBox.click();
        regionBox.clear();
        regionBox.sendKeys(city);
        regionBox.sendKeys(Keys.DOWN);
        regionBox.click();
    }

    public void setDate(LocalDate inDate, LocalDate outDate) {
        monthin.clear();
        monthin.sendKeys(String.valueOf(inDate.getMonth().getValue()));
        monthdayin.clear();
        monthdayin.sendKeys(String.valueOf(inDate.getDayOfMonth()));
        yearin.clear();
        yearin.sendKeys(String.valueOf(inDate.getYear()));

        monthout.clear();
        monthout.sendKeys(String.valueOf(outDate.getMonth().getValue()));
        monthdayout.clear();
        monthdayout.sendKeys(String.valueOf(outDate.getDayOfMonth()));
        yearout.clear();
        yearout.sendKeys(String.valueOf(outDate.getYear()));
    }
    public PageHotels search() {
        searchButton.click();
        return PageFactory.initElements(this.driver, PageHotels.class);
    }
}
