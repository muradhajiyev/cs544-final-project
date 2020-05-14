package edu.miu.cs544.medappointment.reciever;

import edu.miu.cs544.medappointment.service.EmailService;
import edu.miu.cs544.medappointment.shared.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailReceiver {

    @Autowired
    private EmailService emailService;

    @JmsListener(destination = "MailNotificationQueue")
    public void receiveMessage(EmailDto emailDto){
        System.out.println("Received " + emailDto);
        emailService.sendMail(emailDto);
    }
}
