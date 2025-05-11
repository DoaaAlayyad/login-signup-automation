package logintest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import loginpage.LoginPage;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));  // إضافة المهلة
        this.loginPage = new LoginPage(driver);
        
        // فتح الصفحة عبر الـ Page Object
        loginPage.open();
    }
    @Test
    public void testAccountLockAfterWrongAttempts() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // إدخال البريد
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        emailField.sendKeys("s12113710@stu.najah.edu");
        emailField.sendKeys(Keys.RETURN);

        // انتظار ظهور حقل الباسورد
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("authPwd")));

        boolean lockMessageDisplayed = false;
        int attemptCount = 0;  // عداد المحاولات

        // الاستمرار في إدخال كلمة مرور خاطئة حتى ظهور رسالة القفل
        while (!lockMessageDisplayed) {
            // إدخال كلمة المرور الخاطئة
            passwordField.clear();
            passwordField.sendKeys("11234567788");
            passwordField.sendKeys(Keys.RETURN);

            // إضافة تأخير (3 ثواني) بين المحاولات
            try {
                Thread.sleep(3000); // 3000 ملي ثانية = 3 ثواني
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // تحديث عداد المحاولات
            attemptCount++;

            // تحقق من رسالة القفل
            try {
                WebElement lockMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("authWindowContent__information")));
                lockMessageDisplayed = lockMsg.getText().contains("Your account has been locked out for 15 minutes due to multiple unsuccessful login attempts.");
            } catch (Exception e1) {
                try {
                    WebElement fallbackLockMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginPage_title")));
                    lockMessageDisplayed = fallbackLockMsg.getText().contains("Your account has been locked out for 15 minutes due to multiple unsuccessful login attempts.");
                } catch (Exception e2) {
                    lockMessageDisplayed = false;
                }
            }

            // إذا ظهرت رسالة القفل نوقف المحاولات
            if (lockMessageDisplayed) {
                break;
            }
        }

        // التأكد من ظهور رسالة القفل بعد المحاولات
        assertTrue(lockMessageDisplayed, "لم تظهر رسالة قفل الحساب بعد إدخال كلمة المرور الخاطئة بشكل متكرر.");
        
        // طباعة عدد المحاولات حتى القفل
        System.out.println("عدد المحاولات قبل ظهور رسالة القفل: " + attemptCount);
    }

    @Test
    public void testLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));  // زيادة الوقت

        // ننتظر حقل الإيميل
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        assertNotNull(emailField, "حقل الإيميل لم يظهر!");

        // التحقق من ظهور عنوان الصفحة
        WebElement loginPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginPage_title")));
        assertNotNull(loginPageTitle, "العنوان 'Log in to Rainbow' لم يظهر!");

        // تعبئة البريد
        emailField.sendKeys("s12113710@stu.najah.edu");

        // استخدام Enter بدلاً من الزر
        emailField.sendKeys(Keys.RETURN);  // أو Keys.ENTER

        // الانتظار لحقل كلمة المرور
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("authPwd")  // استخدام ID بدلاً من XPath
        ));
        assertNotNull(passwordField, "لم يتم العثور على حقل كلمة المرور، لم ننتقل للصفحة الصحيحة.");

        // تعبئة كلمة المرور
        passwordField.sendKeys("Doaa@26cs2003");

        // الضغط على Enter
        passwordField.sendKeys(Keys.RETURN);

        // الانتظار حتى ننتقل للصفحة الرئيسية
        WebElement userAvatar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("userAvatarImage")));
        WebElement companyLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("channelsHeader-companyLogo")));
        WebElement userInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("channelsHeader-info")));

        // التحقق من ظهور العناصر التي تدل على أننا في الصفحة الصحيحة
        assertTrue(userAvatar.isDisplayed(), "لم يتم العثور على صورة المستخدم.");
        assertTrue(companyLogo.isDisplayed(), "لم يتم العثور على شعار .");
        assertTrue(userInfo.isDisplayed(), "لم يتم العثور على معلومات المستخدم.");
    }
   /* @Test
    public void testValidEmailWrongPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        emailField.sendKeys("doaa@gmail.com");
        emailField.sendKeys(Keys.RETURN);

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("authPwd")));
        passwordField.sendKeys("12345678");
        passwordField.sendKeys(Keys.RETURN);

        // نتحقق من ظهور إحدى رسائل الخطأ
        boolean errorDisplayed = false;
        try {
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("authWindowContent__inputErrorMessage")));
            errorDisplayed = errorMsg.isDisplayed();
        } catch (Exception e1) {
            try {
                WebElement warningIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("warning")));
                errorDisplayed = warningIcon.isDisplayed();
            } catch (Exception e2) {
                errorDisplayed = false;
            }
        }

        assertTrue(errorDisplayed, "رسالة الخطأ لم تظهر عند إدخال كلمة مرور خاطئة.");
    }*/

    


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
