package logintest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import loginpage.DashboardPage;
import loginpage.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private static ExtentReports extent;
    private ExtentTest test;

    @BeforeAll
    public static void setUpBeforeClass() {
        // إعداد التقرير
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/LoginTestReport.html");
        extent.attachReporter(spark);

        // معلومات النظام
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Tester", "Doaa Alayyad");
    }

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        loginPage.open(); // فتح صفحة تسجيل الدخول
    }

    @Test
    @Order(1)
    @DisplayName("Successful login with valid credentials")
    public void testSuccessfulLogin() {
        test = extent.createTest("Successful login with valid credentials", 
            "Test to verify login with valid credentials");

        try {
            loginPage.enterUsername("s12113710@stu.najah.edu");
            loginPage.enterPassword("Doaa@26cs2003");

            DashboardPage dashboardPage = new DashboardPage(driver);

            assertAll("Verify dashboard elements after login",
                () -> assertTrue(dashboardPage.isUserAvatarDisplayed(), "User avatar should be displayed"),
                () -> assertTrue(dashboardPage.isCompanyLogoDisplayed(), "Company logo should be displayed"),
                () -> assertTrue(dashboardPage.isUserInfoDisplayed(), "User info should be displayed")
            );

            test.log(Status.PASS, "Login successful with valid credentials");

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Login failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            test.fail("Unexpected error occurred: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("Account lock after wrong attempts")
    public void testAccountLockAfterWrongAttempts() {
        test = extent.createTest("Account lock after wrong attempts", 
            "Test to verify account lock after multiple wrong password attempts");

        try {
            loginPage.enterUsername("s12113710@stu.najah.edu");

            int attempts = 0;
            boolean isLocked = false;

            while (!isLocked && attempts < 10) {
                loginPage.enterPassword("wrongpassword" + attempts);
                attempts++;

                try {
                    Thread.sleep(3000); // الانتظار بين المحاولات
                } catch (InterruptedException e) {
                    test.log(Status.WARNING, "Sleep interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt(); // إعادة ضبط علم الـ interrupt
                }

                isLocked = loginPage.isLockMessageDisplayed(); // تحقق من رسالة القفل
            }

            assertTrue(isLocked, "Account should be locked after multiple wrong attempts");
            test.log(Status.PASS, "Account locked after " + attempts + " wrong attempts");
            System.out.println("Number of attempts until lock: " + attempts);

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Account lock test failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            test.fail("Unexpected error occurred: " + e.getMessage());
            throw e;
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // إغلاق المتصفح بعد كل اختبار
        }
    }

    @AfterAll
    public static void tearDownAfterClass() {
        extent.flush(); // حفظ التقرير
    }
}
