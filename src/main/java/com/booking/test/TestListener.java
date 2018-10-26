package com.booking.test;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestListener extends TestTask implements ITestListener {

    private LocalDateTime currentTimeStart;
    private LocalDateTime currentTime;

    @Override
    public void onTestStart(ITestResult result) {
        currentTimeStart = LocalDateTime.now();

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = ((TestTask) testClass).getDriver();
        currentTime  = LocalDateTime.now();
        long time = ChronoUnit.MILLIS.between(currentTimeStart,currentTime);
        if (time > 120000) {
        File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
             FileUtils.copyFile(src, new File("c:\\screenshots\\error.png"));
          }
        catch (IOException e)  {
             System.out.println(e.getMessage());
           }

        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
