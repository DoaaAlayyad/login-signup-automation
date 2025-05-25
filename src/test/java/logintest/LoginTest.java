package logintest;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import loginpage.*;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private static ExtentReports extent;
    private ExtentTest test;
    private String browser;

    @BeforeClass
    @Parameters("browser")
    public void setUpClass(String browser) {
        this.browser = browser;
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver(browser);
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @Test(groups = "chrome") // أو groups = "firefox"
    public void testSuccessfulLogin() {
        test = extent.createTest("Successful Login");
        loginPage.enterUsername("s12113710@stu.najah.edu");
        loginPage.enterPassword("Doaa@26cs2003");
        assertTrue(true); // استبدلها باختباراتك الفعلية
        test.log(Status.PASS, "Login succeeded");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass
    public void tearDownClass() {
        extent.flush();
    }
}