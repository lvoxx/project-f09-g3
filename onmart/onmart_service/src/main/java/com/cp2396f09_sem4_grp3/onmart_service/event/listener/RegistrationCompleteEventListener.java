package com.cp2396f09_sem4_grp3.onmart_service.event.listener;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.event.RegistrationCompleteEvent;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.SendEmailFailedException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.ResourceReader;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Value("${app.path.fe.home}")
    private String fePath;

    @Value("${app.path.fe.reset-password}")
    private String feResetPasswordPath;

    @Value("${app.mail.path.verify-email}")
    private String verifyUserPath;

    @Value("${app.mail.path.reset-password}")
    private String resetPasswordPath;

    @Value("${app.mail.company}")
    private String companyName;

    @Value("${app.mail.user}")
    private String fromUser;

    @Value("${app.mail.details.verify-email}")
    private String titleVerifyEmail;

    @Value("${app.mail.details.reset-password}")
    private String titleResetPassword;

    @Value("classpath:/templates/mail/email-template.html")
    Resource verifyEmailFile;

    @Value("classpath:/templates/mail/reset-password-template.html")
    Resource resetPasswordEmailFile;

    private final UserService userService;
    private final JavaMailSender mailSender;
    private User theUser;

    @Override
    public void onApplicationEvent(@NonNull RegistrationCompleteEvent event) {
        // 1. Get the newly registered user
        theUser = event.getUser();
        // 2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        // 3. Save the verification token for the user
        userService.saveUserVerificationToken(theUser, verificationToken);
        // 4 Build the verification url to be sent to the user
        String tokenPath = "?token=" + verificationToken;
        // 5. Send the email.
        try {
            sendVerificationEmail(tokenPath);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new SendEmailFailedException(e.getMessage());
        }
        log.info("Click the link to verify your registration :  {}", tokenPath);
    }

    public void sendVerificationEmail(String tokenPath) throws MessagingException, UnsupportedEncodingException {
        String subject = this.companyName;
        String senderName = this.titleVerifyEmail;

        // Read the HTML template into a String variable
        String mailContent = ResourceReader.asString(verifyEmailFile);

        // Replace placeholders in the HTML template with dynamic values
        mailContent = mailContent.replace("${home-page}", this.fePath);
        mailContent = mailContent.replace("${verify-user}", this.verifyUserPath + tokenPath);

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(this.fromUser, senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void resendVerificationEmail(String tokenPath, User user)
            throws MessagingException, UnsupportedEncodingException {
        String subject = this.companyName;
        String senderName = this.titleVerifyEmail;

        // Read the HTML template into a String variable
        String mailContent = ResourceReader.asString(verifyEmailFile);

        // Replace placeholders in the HTML template with dynamic values
        mailContent = mailContent.replace("${home-page}", this.fePath);
        mailContent = mailContent.replace("${verify-user}", this.verifyUserPath + tokenPath);

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(this.fromUser, senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendPasswordResetVerificationEmail(String tokenPath, User user)
            throws MessagingException, UnsupportedEncodingException {
        String subject = this.companyName;
        String senderName = this.titleResetPassword;

        // Read the HTML template into a String variable
        String mailContent = ResourceReader.asString(resetPasswordEmailFile);

        // Replace placeholders in the HTML template with dynamic values
        mailContent = mailContent.replace("${home-page}", this.fePath);
        mailContent = mailContent.replace("${verify-user}", this.fePath + this.feResetPasswordPath + tokenPath);

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(this.fromUser, senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
