package logintest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import loginpage.DashboardPage;
import loginpage.LoginPage;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    
    @BeforeEach // كانت @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        loginPage.open();
    }
    
    @Test
    @Order(1) // كانت priority = 1
    @DisplayName("Successful login with valid credentials") // كانت description
    public void testSuccessfulLogin() {
        loginPage.enterUsername("s12113257@stu.najah.edu");
        loginPage.enterPassword("Doaa@26cs2003");
        
        DashboardPage dashboardPage = new DashboardPage(driver);
        assertTrue(dashboardPage.isUserAvatarDisplayed());
        assertTrue(dashboardPage.isCompanyLogoDisplayed());
        assertTrue(dashboardPage.isUserInfoDisplayed());
    }
    
    @Test
    @Order(2) // كانت priority = 2
    @DisplayName("Account lock after wrong attempts") // كانت description
    public void testAccountLockAfterWrongAttempts() {
        loginPage.enterUsername("s12113257@stu.najah.edu");
        
        int attempts = 0;
        boolean isLocked = false;
        
        while (!isLocked && attempts < 10) {
            loginPage.enterPassword("wrongpassword" + attempts);
            attempts++;
            
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            isLocked = loginPage.isLockMessageDisplayed();
        }
        
        assertTrue(isLocked, "Account should be locked after multiple wrong attempts");
        System.out.println("Number of attempts until lock: " + attempts);
    }
    
    @AfterEach // كانت @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}