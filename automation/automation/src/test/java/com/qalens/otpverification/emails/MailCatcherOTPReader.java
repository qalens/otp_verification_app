package com.qalens.otpverification.emails;

import io.restassured.specification.RequestSpecification;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class MailCatcherOTPReader implements OTPReader{
    private static Date startDateTime = new Date();
    private static class MessageSummary{
        public MessageSummary() {
        }
        private String id;
        private String sender;
        private List<String> recipients;
        private String subject;
        private String size;
        private Date created_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public List<String> getRecipients() {
            return recipients;
        }

        public void setRecipients(List<String> recipients) {
            this.recipients = recipients;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Date created_at) {
            this.created_at = created_at;
        }
    }
    RequestSpecification mailcatcherAPIs = given().baseUri(System.getenv("MAILCATCHER_URL"));

    @Override
    public String getLatestOTP(String emailId) {
        List<MessageSummary> messages = given(this.mailcatcherAPIs)
                .get("/messages")
                .body()
                .jsonPath().getList(".",MessageSummary.class);

        MessageSummary filteredMessage = messages.stream().filter(m->m.created_at.after(startDateTime)).sorted(Comparator.comparing(a -> a.created_at)).reduce((a, b) -> b).orElse(null);

        String contentFormat=given(this.mailcatcherAPIs)
                .get(String.format("/messages/%s.json",filteredMessage.id))
                .body()
                .jsonPath().getString("formats[1]");

        String content = given(this.mailcatcherAPIs)
                .get(String.format("/messages/%s.%s",filteredMessage.id,contentFormat))
                .body()
                .asString();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            String fstr = matcher.group();
            if (fstr.length()==6){
                return fstr;
            }
        }
        return null;
    }
}
