package com.qalens.otpverification.tests;

import com.qalens.otpverification.emails.MailosaurOTPReader;
import com.qalens.otpverification.emails.OTPReader;
import com.qalens.otpverification.pom.SignUpPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest {
    static String emailId ="metal-captain@"+ MailosaurOTPReader.serverDomain;
    WebDriver driver;
    OTPReader otpReader;
    @BeforeEach
    public void setUp(){
        driver=new ChromeDriver();
        otpReader = OTPReader.getReader();
    }
    @Test
    public void successfulRegistration(){
        String message = SignUpPage
                .launch(driver)
                .signUp("Atmaram","Naik", emailId)
                .provideCorrectOTPAndSubmit(otpReader.getLatestOTP(emailId))
                .getMessage();
        assertEquals(message,"Registration is successful","Registration Completed");
    }
    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}
