package com.booking.test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;

public class OpenPage {

    private WebDriver driver;
    @FindBy(id = "ss")
    private WebElement regionBox;

    @FindBy(xpath = "//button[@data-sb-id='main']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[contains(@class,'calendar-restructure-sb')]")
    private WebElement inCalendarBox;


    public OpenPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step ("Step setRegion.")
    public void setRegion(String city) {
        regionBox.click();
        regionBox.clear();
        regionBox.sendKeys(city);
        regionBox.sendKeys(Keys.DOWN);
        regionBox.click();
    }
    @Step("Step setDate.")
    public void setDate(LocalDate inDate, LocalDate outDate) {
        inCalendarBox.click();
        driver.findElement(By.xpath("//table//td[@data-date='" + inDate.toString() + "']")).click();
        driver.findElement(By.xpath("//table//td[@data-date='" + outDate.toString() + "']")).click();

    }
    @Step("Step click search.")
    public PageHotels search() {
        searchButton.click();
        return PageFactory.initElements(this.driver, PageHotels.class);
    }
}
