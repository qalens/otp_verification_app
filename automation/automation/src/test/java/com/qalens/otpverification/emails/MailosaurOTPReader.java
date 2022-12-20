package com.qalens.otpverification.emails;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;

import java.io.IOException;
import java.util.Date;

public class MailosaurOTPReader implements OTPReader {
    static String apiKey = System.getenv("MAILOSAUR_API_KEY");
    static String serverId = System.getenv("MAILOSAUR_SERVER_ID");
    public static String serverDomain = serverId+".mailosaur.net";
    static MailosaurClient mailosaur = new MailosaurClient(apiKey);

    private static Date startDateTime = new Date();
    @Override
    public String getLatestOTP(String emailId) {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(serverId);
        params.withReceivedAfter(startDateTime);
        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentTo(emailId);
        Message message;
        try {
            message = mailosaur.messages().get(params, criteria);
            String otp = message.html().codes().stream().filter(code->code.value().length()==6).map(code->code.value()).findFirst().get();
            mailosaur.messages().delete(message.id());
            return otp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MailosaurException e) {
            throw new RuntimeException(e);
        }
    }
}
