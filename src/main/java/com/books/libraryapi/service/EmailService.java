package com.books.libraryapi.service;

import java.util.List;

public interface EmailService {
    void sendEmails(String message, List<String> mailList);
}
