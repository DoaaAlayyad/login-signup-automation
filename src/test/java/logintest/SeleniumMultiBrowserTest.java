package logintest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumMultiBrowserTest {

    WebDriver driver;

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    public void testBrowser(String browser) {
        setUp(browser);
        runTest();
        tearDown();
    }

    public void setUp(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup(); // سطر واحد بيكفي
            driver = new ChromeDriver();
        }
    }

    public void runTest() {
        driver.get("https://www.google.com");
        System.out.println("Title is: " + driver.getTitle());
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
