package com.Way2Automation.Utilities;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ElementActions {

    private ElementActions() {

    }

    /**
     * Sends the specified text to the given input locator.
     *
     * <p><strong>Usage:</strong></p>
     * <pre>
     *     ElementActions.sendData(driver, element, "textToSend");
     * </pre>
     *
     * @param driver  the WebDriver instance
     * @param locator locator the By locator used to find the web element
     * @param text    the text to input into the field
     */
    @Step("Sending data {data} to the element {locator}")
    public static void sendData(WebDriver driver, By locator, String text) {
        Waits.waitForElementToBeVisible(driver, locator);
        Scrolling.scrollToElement(driver, locator);
        findElement(driver, locator).sendKeys(text);
        LogsUtil.info("Data entered: ", text, " in the field: ", locator.toString());

    }

    /**
     * Sends the specified text to the given input WebElement.
     *
     * <p><strong>Usage:</strong></p>
     * <pre>
     *     ElementActions.sendData(driver, element, "textToSend");
     * </pre>
     *
     * @param driver  the WebDriver instance
     * @param element the WebElement to which text will be sent
     * @param text    the text to input into the field
     */

    @Step("Sending data {data} to the element {locator}")
    public static void sendDataToWebElement(WebDriver driver, WebElement element, String text) {
        Waits.waitForElementToBeVisible(driver, element);
        Scrolling.scrollToElement(driver, element);
        element.sendKeys(text);
        LogsUtil.info("Data entered: ", text, " in the field: ", element.toString());

    }

    /**
     * Retrieves the visible text from a web element identified by the given locator.
     *
     * <p><strong>Usage:</strong></p>
     * <pre>
     *     ElementActions.sendData(driver, element, "textToSend");
     * </pre>
     *
     * @param driver  the WebDriver instance
     * @param locator locator the By locator used to find the web element
     * @return the visible text content of the eleme
     */

    @Step("Getting text from element {locator}")
    public static String GetTextFromWebElement(WebDriver driver, By locator) {
        Waits.waitForElementToBeVisible(driver, locator);
        Scrolling.scrollToElement(driver, locator);
        LogsUtil.info("Get text from : ", locator.toString(), " Text: ", findElement(driver, locator).getText());
        return findElement(driver, locator).getText();
    }

    /**
     * Retrieves the current text from an input or textarea element using the DOM attribute "value".
     *
     * <p><strong>Usage:</strong></p>
     * <pre>
     *     String inputText = ElementActions.getTextFromInput(driver, By.id("emailInput"));
     * </pre>
     *
     * @param driver  the WebDriver instance controlling the browser
     * @param locator the By locator used to find the input or textarea element
     * @return the value of the input field as a String, or null if not present
     */
    @Step("Getting text filled to input")
    public static String getTextFromInput(WebDriver driver, By locator) {
        Waits.waitForElementToBeVisible(driver, locator);
        Scrolling.scrollToElement(driver, locator);
        LogsUtil.info("input : ", locator.toString(), "filled by text : " + findElement(driver, locator).getDomAttribute("value"));
        return findElement(driver, locator).getDomAttribute("value");
    }

    /**
     * Clicks on a web element located by the specified locator.
     *
     * <p><strong>Usage:</strong></p>
     * <pre>
     *     ElementActions.clickOnElement(driver, By.cssSelector(".submit-button"));
     * </pre>
     *
     * @param driver  the WebDriver instance controlling the browser
     * @param locator the By locator used to find the clickable web element
     */

    /*---------------------------------------------------------------------------------------------------------------*/
    @Step("Clicking on element {locator}")
    public static void clickOnElement(WebDriver driver, By locator) {
        Waits.waitForElementToBeClickable(driver, locator);
        Scrolling.scrollToElement(driver, locator);
        findElement(driver, locator).click();
        LogsUtil.info("Clicked on the element: ", locator.toString());

    }

    /**
     * Clicks on a web element.
     *
     * <p><strong>Usage:</strong></p>
     * <pre>
     *     ElementActions.clickOnElement(driver, By.cssSelector(".submit-button"));
     * </pre>
     *
     * @param driver  the WebDriver instance controlling the browser
     * @param element web element which  will click on
     */

    @Step("Clicking on element {element}")
    public static void clickOnElement(WebDriver driver, WebElement element) {
        Waits.waitForElementToBeClickable(driver, element);
        Scrolling.scrollToElement(driver, element);
        element.click();
        LogsUtil.info("Clicked on the element: ", element.toString());

    }

    /*--------------------------------------------------------------------------------*/
    @Step("Clicking on element using JavascriptExecutor {locator}..")
    public static void clickOnElementByJavaScrip(WebDriver driver, By locator) {
        Waits.waitForElementToBePresent(driver, locator);
        Scrolling.scrollToElement(driver, locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                findElement(driver, locator));
        LogsUtil.info("Clicked on the element using JavascriptExecutor: ", locator.toString());

    }

    @Step("Clicking on element using JavascriptExecutor...")
    public static void clickOnElementByJavaScript(WebDriver driver, WebElement element) {
        try {
            Waits.waitForElementToBeVisible(driver, element); // Optional but good for safety
            Scrolling.scrollToElement(driver, element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            LogsUtil.info("Clicked on the element using JavascriptExecutor.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to click element using JavaScript", e);
        }
    }

    /*--------------------------------------------------------------------------------*/
    public static WebElement findElement(WebDriver driver, By locator) {
        Waits.waitForElementToBeVisible(driver, locator);
        LogsUtil.info("Finding element : ", locator.toString());
        return driver.findElement(locator);

    }

    public static List<WebElement> findGroupOfElement(WebDriver driver, By locator) {
        Waits.waitForListOfElementsToBeVisible(driver, locator);
        LogsUtil.info("Finding group of element linked by locator : ", locator.toString());
        return driver.findElements(locator);

    }

    /*--------------------------------------------------------------------------------*/
    public static void hoverOnElement(WebDriver driver, By locator) {
        Waits.waitForElementToBeClickable(driver, locator);
        Waits.waitForListOfElementsToBeVisible(driver, locator);
        LogsUtil.info("Hovering on Menu element : ", locator.toString());
        new Actions(driver)
                .clickAndHold(findElement(driver, locator))
                .pause(Duration.ofSeconds(1))
                .release()
                .perform();

    }

    /*--------------------------------------------------------------------------------*/
    @Step("Selecting from dropdown list  {locator} option matched with {partialText}")
    public static void selectDropdownByPartialText(WebDriver driver, By locator, String partialText) {
        Waits.waitForListOfElementsToBeVisible(driver, locator);
        Select select = new Select(findElement(driver, locator));
        List<WebElement> options = select.getOptions();

        for (WebElement option : options) {
            if (option.getText().contains(partialText)) {
                select.selectByVisibleText(option.getText());
                return;
            }
        }

        throw new RuntimeException("No option with partial text: " + partialText);
    }

    @Step("select Random Option From Dropdown list {locator}}")
    public static void selectRandomOptionFromDropdown(WebDriver driver, By locator) {
        Waits.waitForListOfElementsToBeVisible(driver, locator);
        Select select = new Select(findElement(driver, locator));
        List<WebElement> options = select.getOptions();

        if (options.size() <= 1) {
            throw new RuntimeException("Not enough options to select randomly.");
        }

        // Skip the first one if it's a default like "Select..." (optional)
        int startIndex = 1; // or 0 if you want to include it
        int randomIndex = new Random().nextInt(options.size() - startIndex) + startIndex;

        WebElement randomOption = options.get(randomIndex);
        select.selectByVisibleText(randomOption.getText());

        System.out.println("Randomly selected: " + randomOption.getText());
    }
}