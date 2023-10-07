package com.krouba.Cedounga;

public interface EmailSenderService {
    void sendEmail(String fullName, String to, String subject, String content);
}
