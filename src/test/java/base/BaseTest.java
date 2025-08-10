package base;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utilities.Driver;
import utilities.TestBaseReport;

public class BaseTest extends TestBaseReport {
    @BeforeClass
    public void setUp() {
        Driver.getDriver();
    }
    @AfterClass
    public void tearDown() {
        Driver.quitDriver();
    }
}