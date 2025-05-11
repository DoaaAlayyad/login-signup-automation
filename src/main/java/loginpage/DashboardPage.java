package loginpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private By userAvatar = By.className("userAvatarImage");
    private By companyLogo = By.className("channelsHeader-companyLogo");
    private By userInfo = By.className("channelsHeader-info");
    
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }
    
    public boolean isUserAvatarDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userAvatar)).isDisplayed();
    }
    
    public boolean isCompanyLogoDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(companyLogo)).isDisplayed();
    }
    
    public boolean isUserInfoDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userInfo)).isDisplayed();
    }
}