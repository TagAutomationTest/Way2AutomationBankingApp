package com.Way2Automation.Pages;

import com.Way2Automation.Utilities.ElementActions;
import com.Way2Automation.Utilities.Validations;
import com.Way2Automation.Utilities.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class bankingHomePage {
    /****Attributes */
    private WebDriver driver;
    private String actualBankingPageHeader;
    private String actualPageTitle;
    /****Locators*/

    private By bankingHomePageTitle = By.cssSelector("strong.mainHeading");
    private By bankingManagerLogin_Btn = By.xpath("//button[@ng-click='manager()' and contains(text(), 'Bank Manager')]");


    /****Constructor*/
    public bankingHomePage(WebDriver driver) {
        this.driver = driver;

    }

    /****Actions*/
    public bankingHomePage AssertOnBankingHomePageHeader(String expectedHeader) {
        Waits.waitForPageLoad(driver);
        actualBankingPageHeader = ElementActions.GetTextFromWebElement(driver, bankingHomePageTitle).toLowerCase();
        Validations.validateEquals(actualBankingPageHeader,
                expectedHeader.toLowerCase(), "Banking HomePage Header Mismatch");

        return this;
    }

    public bankingManagerPage clickingOnBankingMangaerLoginButton() {
        ElementActions.clickOnElement(driver, bankingManagerLogin_Btn);
        Waits.waitForPageLoad(driver);
        return new bankingManagerPage(driver);
    }
}
