package logintest;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
//import utils.GmailReader;

import loginpage.SignUpPage;

public class SignUpTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://web.openrainbow.net/rb/2.155.1/index.html#/login");

        SignUpPage signUpPage = new SignUpPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(d -> d.findElement(signUpPage.getSignUpButtonLocator()).isDisplayed());

        signUpPage.clickSignUpButton();

        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(d -> d.findElement(signUpPage.getSignUpButtonLocator()) != null);

        System.out.println("✅ تم فتح صفحة التسجيل!");

        // 1. إدخال البريد الإلكتروني
        String email = "alayyadoaa@gmail.com";
        String appPassword = "AppPassword_الي_جبته";
        String password = "Doaa26cs@@@@";

        signUpPage.enterEmail(email);
        System.out.println("📩 تم إدخال الإيميل!");

        // 2. الانتظار لوصول الكود
        System.out.println("⏳ في انتظار الكود...");
        String code = null;
        for (int i = 0; i < 10; i++) {
           // code = GmailReader.readVerificationCode(email, appPassword);
            if (code != null) {
				break;
			}
            try { Thread.sleep(5000); } catch (Exception ignored) {}
        }

        if (code == null) {
            System.out.println("❌ لم يتم العثور على كود التحقق!");
            driver.quit();
            return;
        }

        System.out.println("✅ الكود: " + code);
        signUpPage.enterVerificationCode(code);

        // 3. كلمة السر، checkbox، متابعة
        signUpPage.enterPassword(password);
        signUpPage.checkAgreement();
        signUpPage.clickContinue();

        System.out.println("🎉 تمت عملية التسجيل بنجاح!");
    }
}
