package com.Way2Automation.Utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;


public class Waits {

    private Waits() {


    }


    public static void waitForPageLoad(WebDriver driver) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class).until(
                        webDriver -> ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete")
                );
    }

    //wait for element to be present
    public static WebElement waitForElementToBePresent(WebDriver driver, By locator) {
        //Code
        LogsUtil.info("Waiting for element to be present :", locator.toString());
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class).until
                        (driver1 ->
                                driver1.findElement(locator));
    }

    //wait for element to be visible
    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator) {
        //
        LogsUtil.info("Waiting for element to be visible :", locator.toString());
        WebElement element = waitForElementToBePresent(driver, locator);
        return element.isDisplayed() ? element : null;
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator) {
        try {
            LogsUtil.info("Waiting for element to be Clickable :", locator.toString());
            //Code
            return new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class).until
                            (driver1 -> {
                                        WebElement element = waitForElementToBeVisible(driver, locator);
                                        return element.isEnabled() ? element : null;
                                    }
                            );
        } catch (Exception e) {
            throw e;
        }
    }

    public static void waitForListOfElementsToBeVisible(WebDriver driver, By locator) {
        try {
            LogsUtil.info("Waiting for group of elements inside List<WebElement> to be visible :", locator.toString());
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class).until
                            (d -> {
                                List<WebElement> elements = d.findElements(locator);
                                return elements.stream().allMatch(WebElement::isDisplayed) ? elements : null;
                            });
        } catch (Exception e) {
            throw e;
        }
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element) {
        try {
            LogsUtil.info("Waiting for WebElement to be clickable...");

            return new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                    .until(d -> {
                        try {
                            return (element.isDisplayed() && element.isEnabled()) ? element : null;
                        } catch (StaleElementReferenceException | NoSuchElementException ex) {
                            return null;
                        }
                    });

        } catch (Exception e) {
            throw new RuntimeException("WebElement was not clickable within timeout", e);
        }
    }


    public static void waitForElementToBeVisible(WebDriver driver, WebElement element) {
        try {
            LogsUtil.info("Waiting for WebElement to be visible...");

            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                    .until(d -> {
                        try {
                            return element.isDisplayed();
                        } catch (StaleElementReferenceException e) {
                            return false;
                        }
                    });

        } catch (Exception e) {
            throw new RuntimeException("Element was not visible in time", e);
        }
    }


}