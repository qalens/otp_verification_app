package com.qalens.otpverification.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OtpPage extends BasePage{
    @FindBy(css = "input[name=\"otp\"]")
    private WebElement otpTextBox;

    @FindBy(id = "submitotp")
    private WebElement submitOTPButton;

    @FindBy(id = "resendotp")
    private WebElement resendOTPButton;

    public static OtpPage get(WebDriver driver){
        OtpPage page = new OtpPage();
        page.initialize(driver);
        return page;
    }

    public RegistrationSuccessPage provideCorrectOTPAndSubmit(String otp) {
        this.otpTextBox.sendKeys(otp);
        this.submitOTPButton.click();
        return RegistrationSuccessPage.get(driver);
    }
}
