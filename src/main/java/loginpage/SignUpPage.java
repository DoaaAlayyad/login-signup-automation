package loginpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage {
    WebDriver driver;

    private By signUpButton = By.id("notAlreadyAnAccount_buttonId");
    private By emailField = By.id("inputEmail");
    private By codeField = By.id("codeInput");
    private By passwordField = By.id("pwdInput");
    private By checkbox = By.className("dummyCheckboxClass");
    private By continueButton = By.cssSelector("button.btn");

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getSignUpButtonLocator() {
        return signUpButton;
    }

    public void clickSignUpButton() {
        driver.findElement(signUpButton).click();
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(emailField).submit(); // أو تضغط Enter
    }

    public void enterVerificationCode(String code) {
        driver.findElement(codeField).sendKeys(code);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void checkAgreement() {
        driver.findElement(checkbox).click();
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }
}
