package com.qalens.otpverification.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    public void initialize(final WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
}
