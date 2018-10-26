package com.booking.test;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class PageHotels {
    private WebDriver driver;

    @FindBy(className = "invisible_spoken")
    private WebElement error;

    @FindAll({
            @FindBy(xpath = "//a[@class='hotel_name_link url']")
    })
    private List<WebElement> hotels;

    @FindAll({
            @FindBy(xpath = "//a[contains(@data-id,'pri-')]")
    })
    private List<WebElement> budgetChoise;

    @FindBy(xpath = "//a[@data-id='any_deal-1' and @data-name='any_deal']")
    private WebElement deals;

    public PageHotels(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getHotelNames() {
        return hotels.stream().map(hotel -> hotel.findElement(By.xpath("./span[contains(@class,'sr-hotel__name')]")).getText()).collect(Collectors.toList());
    }

    public PageHotels selectCheapHotels() {
        budgetChoise.get(0).click();
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.attributeContains(By.xpath("//a[contains(@data-id,'pri-1')]"), "aria-checked", "true"));
        return PageFactory.initElements(driver, this.getClass());
    }

    public PageHotels unselectCheapHotels() {
        budgetChoise.get(0).click();
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.attributeContains(By.xpath("//a[contains(@data-id,'pri-1')]"), "aria-checked", "false"));
        return PageFactory.initElements(driver, this.getClass());
    }

    public PageHotels selectExpensiveHotels() {
        budgetChoise.get(budgetChoise.size()-1).click();
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.attributeContains(By.xpath("//a[contains(@data-id,'pri-" + budgetChoise.size() + "')]"), "aria-checked", "true"));
        return PageFactory.initElements(driver, this.getClass());
    }

    public PageHotels unselectExpensiveHotels() {
        budgetChoise.get(budgetChoise.size()-1).click();
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.attributeContains(By.xpath("//a[contains(@data-id,'pri-" + budgetChoise.size() + "')]"), "aria-checked", "false"));
        return PageFactory.initElements(driver, this.getClass());
    }

    public PageHotels selectDealsHotels() {
        deals.click();
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.attributeContains(By.xpath("//a[@data-id='any_deal-1' and @data-name='any_deal']"), "aria-checked", "true"));
        return PageFactory.initElements(driver, this.getClass());
    }

    public PageHotels unselectDealsHotels() {
        deals.click();
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.attributeContains(By.xpath("//a[@data-id='any_deal-1' and @data-name='any_deal']"), "aria-checked", "false"));
        return PageFactory.initElements(driver, this.getClass());
    }


    public boolean isErrorMessageDisplayed() {
        return error.isDisplayed();
    }



}
