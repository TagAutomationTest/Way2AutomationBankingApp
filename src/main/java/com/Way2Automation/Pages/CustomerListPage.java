package com.Way2Automation.Pages;

import com.Way2Automation.Utilities.BrowserActions;
import com.Way2Automation.Utilities.ElementActions;
import com.Way2Automation.Utilities.Validations;
import com.Way2Automation.Utilities.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CustomerListPage {

    /****Attributes */
    private WebDriver driver;
    private String addedCustomersPagePageUrl;
    private WebElement UsersList;
    private List<WebElement> rows;
    private List<WebElement> cells;
    private int ActualcustomerOrderInTheList;
    private List<String> actualUserDetails;
    private List<String> expectedUserDetails;
    private WebElement lastRow;
    private WebElement createdCustomerDelete_Btn;
    private String actualAccountNumber;

    /****Locators*/
    private By searchCustomer_Input = By.cssSelector("input[placeholder*='Search Customer']");
    private By userList_Table = By.cssSelector("table[class*='table-striped']");
    private By tableRows = By.tagName("tr");
    private By tableRows_Cells = By.tagName("td");
    private By Delete_Btn = By.tagName("button");
    ;

    /****Constructor*/
    public CustomerListPage(WebDriver driver) {
        this.driver = driver;

    }

    /****Actions*/
    public CustomerListPage assertOpeningCustomersListPage() {
        Waits.waitForElementToBeVisible(driver, searchCustomer_Input);
        addedCustomersPagePageUrl = BrowserActions.getCurrentuRL(driver);
        Validations.validateTrue(addedCustomersPagePageUrl.contains("list"), "added customers Page Url mismatch");

        return this;
    }


    public CustomerListPage AssertThatUserOrderInListIsTheSameAsTheValueRetrievedFromAlert(String CustomerFName, String customerIdExtractedFromAlert) {
        ActualcustomerOrderInTheList = getUserRowIndex(CustomerFName);
        Validations.validateEquals(String.valueOf(ActualcustomerOrderInTheList), customerIdExtractedFromAlert, "User order mismatch ..");
        return this;
    }

    public CustomerListPage AssertOnUserDetailsFromList(String CustomerFName, String CustomerLName, String CustomerPostalCode) {
        actualUserDetails = new ArrayList<>();
        expectedUserDetails = new ArrayList<>();

        expectedUserDetails.add(CustomerFName);
        expectedUserDetails.add(CustomerLName);
        expectedUserDetails.add(CustomerPostalCode);

        cells = getLastRowCells(CustomerFName);
        for (WebElement cell : cells) {
            actualUserDetails.add(cell.getText());
        }
        Validations.validateTrue(actualUserDetails.containsAll(expectedUserDetails), "user details mismatching.");

        return this;
    }

    public CustomerListPage AssertThatAccountNumberReflectedToAddedCustomerInList(String extractedAccountNumber) {
        UsersList = ElementActions.findElement(driver, userList_Table);
        rows = UsersList.findElements(tableRows);
        if (!rows.isEmpty()) {
            lastRow = rows.get(rows.size() - 1);
            cells = lastRow.findElements(tableRows_Cells);
            actualAccountNumber = cells.get(3).getText();
        }
        Validations.validateEquals(actualAccountNumber, extractedAccountNumber, "Account number mismatching.");

        return this;
    }

    public CustomerListPage deleteCustomerFromListing(String CustomerFName) {
        cells = getLastRowCells(CustomerFName);
        if (cells.get(0).getText().equalsIgnoreCase(CustomerFName)) {
            createdCustomerDelete_Btn = cells.get(4).findElement(Delete_Btn);
            ElementActions.clickOnElement(driver, createdCustomerDelete_Btn);
        }
        return this;
    }

    public CustomerListPage validateThatCustomerIsDeletedFromListing(String CustomerFName) {

        Validations.validateTrue(getUserRowIndex(CustomerFName) < 0, "Customer didn't delete from list");
        return this;
    }


    /*******************************************************************/
    public int getUserRowIndex(String CustomerFName) {
        UsersList = ElementActions.findElement(driver, userList_Table);
        rows = UsersList.findElements(tableRows);
        for (int i = 0; i < rows.size(); i++) {
            cells = rows.get(i).findElements(tableRows_Cells);
            String FirstNameCellText = cells.get(0).getText().trim();
            if (FirstNameCellText.equals(CustomerFName)) {
                return i; // Return 1-based index
            }
        }
        return -1; // Not found
    }


    public List<WebElement> getLastRowCells(String CustomerFName) {
        UsersList = ElementActions.findElement(driver, userList_Table);
        rows = UsersList.findElements(tableRows);
        if (!rows.isEmpty()) {
            lastRow = rows.get(rows.size() - 1);
        }
        return lastRow.findElements(tableRows_Cells);
    }
}