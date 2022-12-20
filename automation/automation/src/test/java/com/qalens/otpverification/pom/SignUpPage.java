package com.qalens.otpverification.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends BasePage{
    public static SignUpPage launch(WebDriver driver){
        driver.get("http://localhost:3000");
        SignUpPage page = new SignUpPage();
        page.initialize(driver);
        return page;
    }
    @FindBy(css = "input[name=\"firstname\"]")
    private WebElement firstNameTextBox;

    @FindBy(css = "input[name=\"lastname\"]")
    private WebElement lastNameTextBox;

    @FindBy(css = "input[name=\"email\"]")
    private WebElement emailTextBox;

    @FindBy(id = "submitcontact")
    private WebElement submitButton;

    public OtpPage signUp(String firstName, String lastName, String email) {
        this.firstNameTextBox.sendKeys(firstName);
        this.lastNameTextBox.sendKeys(lastName);
        this.emailTextBox.sendKeys(email);
        this.submitButton.click();
        return OtpPage.get(this.driver);
    }
}
