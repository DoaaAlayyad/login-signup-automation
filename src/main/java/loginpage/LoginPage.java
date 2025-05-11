package loginpage;

import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    private String url = "https://web.openrainbow.net/rb/2.149.18/index.html#/login";

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to open the page
    public void open() {
        driver.get(url); // فتح الرابط مباشرة عند استدعاء هذه الدالة
    }

    // يمكنك إضافة المزيد من الدوال لتفاعل مع العناصر على الصفحة
}
