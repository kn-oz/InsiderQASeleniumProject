package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CareersQAPage;
import pages.HomePage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.time.Duration;
import java.util.List;

public class US02_CareersQAPageTest extends BaseTest {

    HomePage homePage;
    CareersQAPage careersPage;

    @BeforeClass
    public void setUpPages() {
        homePage = new HomePage();
        careersPage = new CareersQAPage();
    }

    @Test(description = "Verify that the QA jobs page is accessible and filtering by Istanbul returns results.", priority = 1)
    public void TC_01_VerifyQAJobsFilterByIstanbul() {
        test().info("Open QA jobs page URL from configuration");
        Driver.getDriver().get(ConfigReader.getProperty("QAJobsUrl"));

        test().info("Accept cookies if banner is visible");
        try {
            careersPage.cookiesAcceptAll.click();
        } catch (Exception ignored) {
        }
        test().info("Click 'See all QA jobs'");
        careersPage.QApageSeeAllJobsButton.click();

        test().info("Open location dropdown and Select Istanbul location");
        careersPage.verifyPresenceOfLocationDropdownAndSelectLocation("Istanbul, Turkiye");

        test().info("Open departments dropdown and Filter jobs by Department:Quality Assurance");
        careersPage.verifyPresenceOfDepartmentDropdownAndSelectDepartment("Quality Assurance");

        test().info("Scroll to positions section and display them");
        careersPage.verifyJobListingsAreDisplayed();
        ReusableMethods.wait(2);

        test().info("Verify jobs are listed after filtering");
        Assert.assertFalse(careersPage.QAjobsList.isEmpty(),
                "Job list is empty after filtering by Istanbul.");

        test().pass("Filtering by Istanbul returned job results");

    }

    @Test(description = "Verify that QA job departments, locations, and positions are correct.", priority = 2)
    public void TC_02_VerifyQAJobDetails() {
        test().info("Convert job card fields to string lists");
        ReusableMethods.wait(2);
        List<String> departmentList = ReusableMethods.stringToList(careersPage.jobsDepartmentTextList);
        List<String> locationList = ReusableMethods.stringToList(careersPage.jobsLocationTextList);
        List<String> positionList = ReusableMethods.stringToList(careersPage.jobsPositionTextList);

        test().info("Validate Department contains 'Quality Assurance' for each job");
        for (String text : departmentList) {
            Assert.assertTrue(text.contains("Quality Assurance"), "Department mismatch: " + text);
        }

        test().info("Validate Location contains 'Istanbul, Turkiye' for each job");
        for (String text : locationList) {
            Assert.assertTrue(text.contains("Istanbul, Turkiye"), "Location mismatch: " + text);
        }

        test().info("Validate Position contains 'Quality Assurance' (case-insensitive)");
        for (String text : positionList) {
            Assert.assertTrue(text.toLowerCase().contains("quality assurance"),
                    "Position mismatch: " + text);
        }
        test().pass("All job details (department/location/position) are valid");
    }

    @Test(description = "Verify that each QA job redirects to the Lever application page.", priority = 3)
    public void TC_03_VerifyQAJobRedirectsToLever() {
        test().info("Collect 'View Role' buttons on filtered list");
        List<WebElement> viewRoleButtons = Driver.getDriver().findElements(By.xpath("(//*[text()='View Role'])"));
        String mainWindow = Driver.getDriver().getWindowHandle();
        test().info("Found " + viewRoleButtons.size() + " 'View Role' buttons");

        for (int i = 0; i < viewRoleButtons.size(); i++) {
            test().info("Open job #" + (i + 1) + " in a new tab");
            List<WebElement> buttons = Driver.getDriver().findElements(By.xpath("(//*[text()='View Role'])"));
            ((JavascriptExecutor) Driver.getDriver())
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", buttons.get(i));
            buttons.get(i).click();

            test().info("Switch to newly opened tab");
            for (String handle : Driver.getDriver().getWindowHandles()) {
                if (!handle.equals(mainWindow)) {
                    Driver.getDriver().switchTo().window(handle);
                    break;
                }
            }
            String currentWindow = Driver.getDriver().getCurrentUrl();
            test().info("Opened URL: " + currentWindow);
            Assert.assertTrue(currentWindow.contains("lever"),
                    "Redirection failed for 'View Role' button at index " + i);

            test().info("Close tab and return to main window");
            Driver.getDriver().close();
            Driver.getDriver().switchTo().window(mainWindow);
        }

        test().pass("All 'View Role' links redirect to Lever");
    }
}