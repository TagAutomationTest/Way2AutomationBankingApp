package com.Way2Automation.Pages;

import com.Way2Automation.Utilities.BrowserActions;
import com.Way2Automation.Utilities.ElementActions;
import com.Way2Automation.Utilities.PropertiesUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class openAccountPage {

    /****Attributes */
    private WebDriver driver;

    private String addAccountAlertText;
    private String[] parts;
    private String keyName = "extractedAccountNumber";
    private String proertyFilePath = "src/main/resources/environment.properties";

    /****Locators*/
    private By customer_DDL = By.cssSelector("select#userSelect");
    private By currency_DDL = By.cssSelector("select#currency");
    private By submitAccount_Btn = By.xpath("//button[@type='submit' and text()='Process']");

    /****Constructor*/
    public openAccountPage(WebDriver driver) {
        this.driver = driver;

    }

    /****Actions*/
    public openAccountPage selectCustomerName(String firstname) {
        ElementActions.selectDropdownByPartialText(driver, customer_DDL, firstname);
        return this;
    }

    public openAccountPage selectRandomCurrency() {
        ElementActions.selectRandomOptionFromDropdown(driver, currency_DDL);
        return this;
    }

    public openAccountPage clickOnSubmitAccountButton() {
        ElementActions.clickOnElement(driver, submitAccount_Btn);
        return this;
    }

    public bankingManagerPage extractCustomerIdFromAlertThenCloseAlert() {

        addAccountAlertText = BrowserActions.getTextFromAlertThenDismiss(driver);
        parts = addAccountAlertText.split(":");
        PropertiesUtils.saveProperty(proertyFilePath, keyName, parts[1].trim());

        return new bankingManagerPage(driver);
    }
}
