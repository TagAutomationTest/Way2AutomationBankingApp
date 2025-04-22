package com.Way2Automation.Utilities;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BrowserActions {
    private BrowserActions() {
    }

    @Step("Navigate to Url {Url}")
    public static void navigateToUrl(WebDriver driver, String Url) {
        driver.get(Url);
        LogsUtil.info("Redirecting to URL :", Url);


    }

    @Step("Getting current url")
    public static String getCurrentuRL(WebDriver driver) {
        LogsUtil.info("Current URL is : ", driver.getCurrentUrl());
        return driver.getCurrentUrl();

    }

    @Step("Getting page title")
    public static String getPageTitle(WebDriver driver) {
        LogsUtil.info("Page title is : ", driver.getTitle());
        return driver.getTitle();

    }

    @Step
    public static void refreshBrowser(WebDriver driver) {
        LogsUtil.info("Refreshing the browser ");
        driver.navigate().refresh();
    }

    @Step("Switching to tab ")
    public static void switchToTab(WebDriver driver, int tabNumber) {
        LogsUtil.info("Switching to tab ");
        Set<String> windowHandles = driver.getWindowHandles();

// Convert Set to List for easy access by index
        List<String> tabs = new ArrayList<>(windowHandles);

// Switch to the second tab (index 1)
        driver.switchTo().window(tabs.get(tabNumber));


    }

    public static String getTextFromAlertThenDismiss(WebDriver driver) {

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.dismiss();
        return alertText;

    }

    @Step("Closing the browser")
    public static void closeBrowser(WebDriver driver) {
        LogsUtil.info("Closing the browser ");
        driver.quit();


    }
}
