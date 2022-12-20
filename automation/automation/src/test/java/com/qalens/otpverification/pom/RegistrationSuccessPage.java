package com.qalens.otpverification.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationSuccessPage extends BasePage {
    @FindBy(id = "status-message")
    private WebElement registrationStatusMessage;
    public static RegistrationSuccessPage get(WebDriver driver){
        RegistrationSuccessPage page = new RegistrationSuccessPage();
        page.initialize(driver);
        return page;
    }

    public String getDisplayedMessage() {
        return this.registrationStatusMessage.getText();
    }
}
