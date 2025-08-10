package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CareersQAPage;
import pages.HomePage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

public class US01_HomePageTest extends BaseTest {

    HomePage homePage;
    CareersQAPage careersPage;

    @BeforeClass
    public void setUpPages() {
        homePage = new HomePage();
        careersPage = new CareersQAPage();
    }

    @Test(description = "Verify if the Home page is accessible", priority = 1)
    public void TC_01_homePageAccessible() {
        test().info("Opening Home page URL from configuration");
        Driver.getDriver().get(ConfigReader.getProperty("HomepageUrl"));

        test().info("Validating that current URL matches the expected URL");
        String expectedUrl = "https://useinsider.com/";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "URL is different than expected: " + actualUrl);

        test().pass("Home page is accessible and URL validation passed");
    }

    @Test(description = "Verify if the Career page, including its Locations, Teams, and Life at Insider sections are accessible.", priority = 2)
    public void TC_02_careersPageAccessible() {
        test().info("Opening 'Company' dropdown on the Home page");
        homePage.HomePageCompanyDropDown.click();

        test().info("Navigating to Careers page");
        homePage.HomePageCompanyDPCareers.click();

        test().info("Validating that current URL matches the Careers page URL");
        String expectedUrl = "https://useinsider.com/careers/";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "URL is different than expected: " + actualUrl);

        test().pass("Careers page is accessible and URL validation passed");
    }

    @Test(description = "Verify if the Locations section is accessible", priority = 3)
    public void TC_03_locationsBlockVisible() {
        test().info("Waiting for Locations section to be visible");
        ReusableMethods.waitForVisibility(homePage.CareersPageLocationsSection, 10);

        test().info("Validating that Locations section is displayed");
        Assert.assertTrue(homePage.CareersPageLocationsSection.isDisplayed(), "Locations section is not displayed");

        test().pass("Locations section is visible");
    }

    @Test(description = "Verify if the Teams section is accessible", priority = 4)
    public void TC_04_teamsBlockVisible() {
        test().info("Waiting for Teams section to be visible");
        ReusableMethods.waitForVisibility(homePage.CareersPageTeamsSection, 10);

        test().info("Validating that Teams section is displayed");
        Assert.assertTrue(homePage.CareersPageTeamsSection.isDisplayed(), "Teams section is not displayed");

        test().pass("Teams section is visible");
    }

    @Test(description = "Verify if the Life at Insider section is accessible", priority = 5)
    public void TC_05_lifeAtInsiderBlockVisible() {
        test().info("Waiting for Life at Insider section to be visible");
        ReusableMethods.waitForVisibility(homePage.CareersPagelifeAtInsiderSection, 10);

        test().info("Validating that Life at Insider section is displayed");
        Assert.assertTrue(homePage.CareersPagelifeAtInsiderSection.isDisplayed(), "Life at Insider section is not displayed");

        test().pass("Life at Insider section is visible");
    }
}