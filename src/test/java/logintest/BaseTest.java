package logintest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {
    public static void main(String[] args) {
        // تعيين المسار الصحيح لبرنامج geckodriver
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        // إنشاء كائن WebDriver باستخدام Firefox
        WebDriver driver = new FirefoxDriver();

        // فتح موقع
        driver.get("https://www.google.com");

        // التأكد من أن الصفحة فتحت
        System.out.println("Title of the page is: " + driver.getTitle());

        // إغلاق المتصفح
        driver.quit();
    }
}
