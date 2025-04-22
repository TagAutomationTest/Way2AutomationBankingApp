package com.Way2Automation.tests;

import com.Way2Automation.Drivers.DriverManager;
import com.Way2Automation.Listners.TestNGListeners;
import com.Way2Automation.Pages.CustomerListPage;
import com.Way2Automation.Pages.addBankingCustomerPage;
import com.Way2Automation.Pages.bankingManagerPage;
import com.Way2Automation.Pages.homePage;
import com.Way2Automation.Utilities.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.Way2Automation.Utilities.TimestampUtils.getTimestamp;

@Listeners({TestNGListeners.class})
public class E2eTestCases {

    JsonUtils testData;
    String F_name;
    String L_name;
    String postalCode;

    @Test(priority = 1, alwaysRun = true)
    @Description("Scenario 1: Add banking user then validate he is added to users list")
    public void addBankingCustomerThenValidateHeAddedToList_Flow() {
        new homePage(DriverManager.getDriver())
                .clickOnJsElementByName(PropertiesUtils.getPropertyValue("angularJsElements"))
                .switchToBankingLoginTab()
                .assertNewTabOpendProperly(PropertiesUtils.getPropertyValue("angularJsElements"))

                .AssertOnBankingHomePageHeader(PropertiesUtils.getPropertyValue("BankLoginHeader"))
                .ClickOnBankingMangaerLoginButton()

                .assertOpeningBankingMangerPage()
                .clickingOnAddCustomerButton()

                .assertOpeningAddCustomerPage()
                .fillAddCustomerForm(F_name, L_name, postalCode)
                .ClickOnAddCustomerButton()
                .extractCustomerIdFromAlertThenCloseAlert()

                .clickingOnCustomersButton()

                .assertOpeningCustomersListPage()
                .AssertThatUserOrderInListIsTheSameAsTheValueRetrievedFromAlert(F_name, PropertiesUtils.getPropertyValue("extractedCustomerId"))
                .AssertOnUserDetailsFromList(F_name, L_name, postalCode);


    }

    @Test(dependsOnMethods = "addBankingCustomerThenValidateHeAddedToList_Flow", priority = 2)
    @Description("Scenario 2: Open account for created customer then validate it's reflected to him in customers list")
    public void openAccountForAddedCustomer_Flow() {
        new bankingManagerPage(DriverManager.getDriver())
                .clickingOnOpenAccountButton()
                .selectCustomerName(F_name)
                .selectRandomCurrency()
                .clickOnSubmitAccountButton()
                .extractCustomerIdFromAlertThenCloseAlert()
                .clickingOnCustomersButton()
                .AssertThatAccountNumberReflectedToAddedCustomerInList(PropertiesUtils.getPropertyValue("extractedAccountNumber"));

    }

    @Test(dependsOnMethods = "addBankingCustomerThenValidateHeAddedToList_Flow", priority = 3)
    @Description("Scenario 3: Delete the created customer in the first scenario then validate he displayed from customers list ")
    public void deleteCreatedCustomer_Flow() {
        new CustomerListPage(DriverManager.getDriver())
                .deleteCustomerFromListing(F_name)
                .validateThatCustomerIsDeletedFromListing(F_name);
    }


    @BeforeTest(alwaysRun = true)
    public void loadLoginTestData() {

        testData = new JsonUtils("test-data");
        F_name = testData.getJsonData("User-details.firstName") + getTimestamp();
        L_name = testData.getJsonData("User-details.lastName") + getTimestamp();
        postalCode = RandomDataUtlities.generateRandomPostalCode();
        LogsUtil.info("Login test data loaded from Json");
        DriverManager.createInstance(PropertiesUtils.getPropertyValue("browserType"));
        DriverManager.getDriver().get(PropertiesUtils.getPropertyValue("homeURL"));
    }

    @AfterTest(alwaysRun = true)
    public void teardown() {
        LogsUtil.info("Closing the browser");
        if (DriverManager.getDriver() != null) {
            BrowserActions.closeBrowser(DriverManager.getDriver());
        }
    }
}
