package com.Way2Automation.Utilities;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scrolling {

    private Scrolling() {

    }

    //Scroll To Element
    @Step("Scrolling to element {locator}")
    public static void scrollToElement(WebDriver driver, By locator) {
        //Code
        LogsUtil.info("Scrolling to element :", locator.toString());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ElementActions.findElement(driver, locator));
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        try {
            LogsUtil.info("Scrolling to WebElement...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to scroll to WebElement", e);
        }
    }
}
