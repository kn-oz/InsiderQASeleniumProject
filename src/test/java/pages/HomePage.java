package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

import java.time.Duration;

public class HomePage {

    public HomePage(){PageFactory.initElements(Driver.getDriver(),this);}

    @FindBy(xpath = "(//*[@id='navbarDropdownMenuLink'])[5]")
    public WebElement HomePageCompanyDropDown;

    @FindBy(xpath = "//*[@href='https://useinsider.com/careers/']")
    public WebElement HomePageCompanyDPCareers;

    @FindBy(xpath = "//*[@id='career-our-location']")
    public WebElement CareersPageLocationsSection;

    @FindBy(xpath = "//*[@class='elementor-widget-wrap elementor-element-populated e-swiper-container']")
    public WebElement CareersPagelifeAtInsiderSection;

    @FindBy(xpath = "//*[@id='career-find-our-calling']")
    public WebElement CareersPageTeamsSection;

    public void goToHomePage(String url) {
        Driver.getDriver().get(url);
    }

    public boolean isHomePageOpened(String expectedUrl) {
        return Driver.getDriver().getCurrentUrl().contains(expectedUrl);
    }

    private WebElement waitUntilClickable(WebElement element) {
        return new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

}
