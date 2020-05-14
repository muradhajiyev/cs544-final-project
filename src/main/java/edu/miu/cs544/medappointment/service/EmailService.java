package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.shared.EmailDto;
import org.springframework.mail.MailException;

public interface EmailService {
    void sendMail(EmailDto email) throws MailException;
}
