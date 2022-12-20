package com.qalens.otpverification.emails;

public interface OTPReader {
    static OTPReader getReader() {
        String otpReader = System.getenv("OTP_READER");
        if (otpReader==null || otpReader.equals("mailcatcher")){
            return new MailCatcherOTPReader();
        }
        return new MailosaurOTPReader();
    }

    String getLatestOTP(String emailId);
}
