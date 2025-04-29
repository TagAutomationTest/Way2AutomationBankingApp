package com.Way2Automation.Pages;

import com.Way2Automation.Utilities.BrowserActions;
import com.Way2Automation.Utilities.ElementActions;
import com.Way2Automation.Utilities.Validations;
import com.Way2Automation.Utilities.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class homePage {

    /****Attributes */
    private WebDriver driver;

    private List<WebElement> AngularJSElements;
    private String Link_title;

    private String currentPageUrl;

    /****Locators*/

    private By AngularJSElementsParent_Ul = By.cssSelector("ul[class*='boxed']");
    private By AngularJSElements_Li = By.tagName("li");
    private By AngularJSElement_LinkName = By.tagName("h2");
    private By AngularJSElement_Link = By.tagName("a");

    /****Constructor*/
    public homePage(WebDriver driver) {
        this.driver = driver;

    }


    /****Actions*/
    public homePage clickingOnJsElementByName(String JsElementName) {
        try {
            AngularJSElements = ElementActions.findElement(driver, AngularJSElementsParent_Ul).findElements(AngularJSElements_Li);
            for (WebElement Li : AngularJSElements) {
                Link_title = Li.findElement(AngularJSElement_LinkName).getText().trim();
                if (Link_title.equalsIgnoreCase(JsElementName)) {
                    ElementActions.clickOnElement(driver, Li.findElement(AngularJSElement_Link));
                    Waits.waitForPageLoad(driver);
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return this;
    }

    public homePage switchToBankingLoginTab() {
        BrowserActions.switchToTab(driver, 1);

        return this;
    }

    public bankingHomePage assertNewTabOpendProperly(String JsElementName) {
        currentPageUrl = BrowserActions.getCurrentuRL(driver);
        Validations.validateTrue(currentPageUrl.contains(JsElementName.toLowerCase()), "Page Url mismatching....");
        return new bankingHomePage(driver);
    }

}
