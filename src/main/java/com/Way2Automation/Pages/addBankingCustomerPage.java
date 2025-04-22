package com.Way2Automation.Pages;

import com.Way2Automation.Utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Random;

public class addBankingCustomerPage {

    /****Attributes */
    private WebDriver driver;
    private String addBankingCustomerPageUrl;
    private WebElement CustomerForm;
    private String addCustomerAlertText;
    private String[] parts;
    private String proertyFilePath = "src/main/resources/environment.properties";
    private String keyName = "extractedCustomerId";

    /****Locators*/

    private By addCustomerForm = By.cssSelector("form[ng-submit*='addCustomer()']");
    private By customerFirstName_Input = By.cssSelector("input[ng-model*='fName']");
    private By customerLastName_Input = By.cssSelector("input[ng-model*='lName']");
    private By customerPostalCode_Input = By.cssSelector("input[ng-model*='postCd']");
    private By addCustomerBtn = By.cssSelector("button[type='submit']");

    /****Constructor*/
    public addBankingCustomerPage(WebDriver driver) {
        this.driver = driver;

    }


    /****Actions*/
    public addBankingCustomerPage assertOpeningAddCustomerPage() {
        Waits.waitForElementToBeVisible(driver, addCustomerBtn);
        addBankingCustomerPageUrl = BrowserActions.getCurrentuRL(driver);
        Validations.validateTrue(addBankingCustomerPageUrl.contains("addCust"), "add Banking Customer Page Url mismatch");
        Waits.waitForElementToBeVisible(driver, addCustomerForm);

        return this;
    }

    public addBankingCustomerPage fillAddCustomerForm(String firstName, String lastName, String postalCode) {
        CustomerForm = ElementActions.findElement(driver, addCustomerForm);
        ElementActions.sendDataToWebElement(driver, CustomerForm.findElement(customerFirstName_Input), firstName);
        ElementActions.sendDataToWebElement(driver, CustomerForm.findElement(customerLastName_Input), lastName);
        ElementActions.sendDataToWebElement(driver, CustomerForm.findElement(customerPostalCode_Input), postalCode);

        return this;
    }


    public addBankingCustomerPage ClickOnAddCustomerButton() {
        ElementActions.clickOnElement(driver, addCustomerBtn);

        return this;
    }

    public bankingManagerPage extractCustomerIdFromAlertThenCloseAlert() {

        addCustomerAlertText = BrowserActions.getTextFromAlertThenDismiss(driver);
        parts = addCustomerAlertText.split(":");
        PropertiesUtils.saveProperty(proertyFilePath, keyName, parts[1].trim());

        return new bankingManagerPage(driver);
    }


}