package loginpage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.id("username");
    private By passwordField = By.id("authPwd");
    private By loginPageTitle = By.id("loginPage_title");
    private By errorMessage = By.className("authWindowContent__inputErrorMessage");
    private By lockMessage = By.className("authWindowContent__information");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void open() {
        driver.get("https://web.openrainbow.net/rb/2.149.18/index.html#/login");
    }

    public void enterUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        element.clear();
        element.sendKeys(username);
        element.sendKeys(Keys.ENTER); // استخدام ENTER بدلاً من submit
    }
    public void waitForPageLoad() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState")
                    .equals("complete")
            );
        } catch (Exception e) {
            System.out.println("Page load timeout: " + e.getMessage());
        }
    }
    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("authPwd")));
        passwordField.clear();
        
        // إدخال كلمة السر حرفًا حرفًا
        for (char c : password.toCharArray()) {
            passwordField.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(100); // تأخير بسيط بين الأحرف
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // إضافة ENTER للنهاية
        passwordField.sendKeys(Keys.ENTER);
    }
    public boolean isLockMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lockMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}