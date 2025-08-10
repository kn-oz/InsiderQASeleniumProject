package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TestBaseReport {

    private static ExtentReports extent;
    private static ExtentSparkReporter spark;

    private static final ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();

    public static ExtentTest test() {
        return testNode.get();
    }

    @BeforeSuite(alwaysRun = true)
    public void startReport() {
        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String outDir = System.getProperty("user.dir") + File.separator + "test-output";
        new File(outDir).mkdirs();
        String reportPath = outDir + File.separator + "ExtentReport_" + ts + ".html";

        spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Insider QA Case Study");
        spark.config().setReportName("UI Automation Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", "Live");
        extent.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
        extent.setSystemInfo("Author", "Your Name");
    }

    @BeforeMethod(alwaysRun = true)
    public void createTest(Method method) {
        // Use TestNG description if present
        String description = "";
        if (method.getAnnotation(Test.class) != null) {
            description = method.getAnnotation(Test.class).description();
        }
        ExtentTest t = extent.createTest(method.getName(), description);
        testNode.set(t);
    }

    @AfterMethod(alwaysRun = true)
    public void logResult(ITestResult result) throws IOException {
        ExtentTest t = test();
        if (t == null) return;

        switch (result.getStatus()) {
            case ITestResult.SUCCESS -> t.log(Status.PASS, "Passed");
            case ITestResult.SKIP -> t.log(Status.SKIP, "Skipped: " + result.getThrowable());
            case ITestResult.FAILURE -> {
                t.log(Status.FAIL, "Failed: " + result.getThrowable());
                String shot = ReusableMethods.reportImageAdd(result.getName());
                t.addScreenCaptureFromPath(shot);
            }
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) extent.flush();
    }
}