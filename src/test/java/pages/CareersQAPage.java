package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.Driver;

import java.time.Duration;
import java.util.List;

import static utilities.Driver.driver;

public class CareersQAPage {

    public CareersQAPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // === Locators via @FindBy ===
    @FindBy(xpath = "//*[@class='btn btn-outline-secondary rounded text-medium mt-2 py-3 px-lg-5 w-100 w-md-50']")
    public WebElement QApageSeeAllJobsButton;

    @FindBy(xpath = "//*[@class='position-list-item-wrapper bg-light']")
    public List<WebElement> QAjobsList;

    @FindBy(xpath = "//*[@class='position-title font-weight-bold']")
    public List<WebElement> jobsPositionTextList;

    @FindBy(xpath = "//*[@class='position-department text-large font-weight-600 text-primary']")
    public List<WebElement> jobsDepartmentTextList;

    @FindBy(xpath = "//*[@class='position-location text-large']")
    public List<WebElement> jobsLocationTextList;

    @FindBy(id = "wt-cli-accept-all-btn")
    public WebElement cookiesAcceptAll;

    @FindBy(id = "filter-by-location")
    public WebElement locationDropdown;

    @FindBy(id = "filter-by-department")
    public WebElement departmentDropdown;

    @FindBy(id = "jobs-list")
    public WebElement jobsListArea;

    @FindBy(xpath = "//div[@data-location='istanbul-turkiye' and @data-team='qualityassurance']")
    public List<WebElement> jobListings;

    // === Methods with waits ===
    public void verifyVisibilityOfElement(WebElement element, String elementName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(visibleElement.isDisplayed(), elementName + " element cannot be displayed!");
    }

    public void verifyPresenceOfElementAndMakeSelection(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement presentElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                org.openqa.selenium.By.id(element.getAttribute("id"))));
        Select select = new Select(presentElement);
        select.selectByVisibleText(text);
    }

    public void verifyVisibilityOfElements(List<WebElement> elements, String elementName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> visibleElements = wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        Assert.assertTrue(visibleElements.size() > 0, elementName + " element cannot be displayed!");
    }

    public void verifyPresenceOfLocationDropdownAndSelectLocation(String text) {
        verifyPresenceOfElementAndMakeSelection(locationDropdown, text);
    }

    public void scrollToElementUsingJS(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void verifyPresenceOfDepartmentDropdownAndSelectDepartment(String text) {
        verifyPresenceOfElementAndMakeSelection(departmentDropdown, text);
    }

    public void verifyJobListingsAreDisplayed() {
        verifyVisibilityOfElement(jobsListArea, "Jobs List Area");
        scrollToElementUsingJS(jobsListArea);
        verifyVisibilityOfElements(jobListings, "Job Listings");
    }
}