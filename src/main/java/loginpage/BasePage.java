package loginpage;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ExtentTest test;

    public BasePage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void captureScreenshot(String screenshotName) {
        try {
            String path = "test-output/screenshots/" + screenshotName + "_" + System.currentTimeMillis() + ".png";
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
            test.addScreenCaptureFromPath(path);
        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }

    protected void logInfo(String message) {
        test.log(Status.INFO, message);
    }

    protected void logPass(String message) {
        test.log(Status.PASS, message);
    }

    protected void logFail(String message) {
        test.log(Status.FAIL, message);
    }
}