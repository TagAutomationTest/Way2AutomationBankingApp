package com.Way2Automation.Pages;

import com.Way2Automation.Utilities.BrowserActions;
import com.Way2Automation.Utilities.ElementActions;
import com.Way2Automation.Utilities.Validations;
import com.Way2Automation.Utilities.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class bankingManagerPage {

    /****Attributes */
    private WebDriver driver;
    private String BankingMangerPageUrl;


    /****Locators*/

    private By addCustomer_Btn = By.cssSelector("button[ng-click*='addCust()']");
    private By customers_Btn = By.cssSelector("button[ng-click*='showCust()']");
    private By openAccount_Btn = By.cssSelector("button[ng-click*='openAccount()']");

    /****Constructor*/
    public bankingManagerPage(WebDriver driver) {
        this.driver = driver;

    }


    /****Actions*/
    public bankingManagerPage assertOpeningBankingMangerPage() {
        Waits.waitForElementToBeVisible(driver, addCustomer_Btn);
        BankingMangerPageUrl = BrowserActions.getCurrentuRL(driver);
        Validations.validateTrue(BankingMangerPageUrl.contains("manager"), "BankingMangerPage mismatch");
        Waits.waitForElementToBeVisible(driver, addCustomer_Btn);

        return this;
    }

    public addBankingCustomerPage clickingOnAddCustomerButton() {
        ElementActions.clickOnElement(driver, addCustomer_Btn);

        return new addBankingCustomerPage(driver);
    }

    public CustomerListPage clickingOnCustomersButton() {
        ElementActions.clickOnElement(driver, customers_Btn);

        return new CustomerListPage(driver);
    }

    public openAccountPage clickingOnOpenAccountButton() {
        ElementActions.clickOnElement(driver, openAccount_Btn);

        return new openAccountPage(driver);
    }
}