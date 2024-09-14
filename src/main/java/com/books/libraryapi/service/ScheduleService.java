package com.books.libraryapi.service;

import com.books.libraryapi.model.entity.Loan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private static final String CRON_LATE_LOANS = "0 0 0 1/1 * ?";

    @Value("${application.mail.lateloans.message}")
    private String message;

    private final LoanService loanService;
    private final EmailService emailService;

    public ScheduleService(LoanService loanService, EmailService emailService) {
        this.loanService = loanService;
        this.emailService = emailService;
    }


    @Scheduled(cron = CRON_LATE_LOANS)
    public void sendEmailToLateLoans(){
        List<Loan> list = loanService.getAllLateLoans();
        List<String> mailList = list.stream()
                .map(loan -> loan.getCustomerEmail())
                .collect(Collectors.toList());

        emailService.sendEmails(message, mailList);

    }
}
