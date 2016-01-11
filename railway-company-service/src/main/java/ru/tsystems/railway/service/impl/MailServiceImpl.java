package ru.tsystems.railway.service.impl;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.tsystems.railway.domain.accounts.UserRole;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.service.DocumentService;
import ru.tsystems.railway.service.MailService;
import ru.tsystems.railway.util.VelocityUtil;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Locale;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String password, Locale locale) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = null;
        try {
            message = new MimeMessageHelper(mimeMessage, true, VelocityUtil.VELOCITY_ENCODING);
            message.setTo(to);

            Template subjectTemplate = Velocity.getTemplate(
                    VelocityUtil.VELOCITY_TEMPLATES_FOLDER + locale.getLanguage() + '_' + EmailTemplate.WELCOME_EMAIL
                            .getSubjectFilename(), VelocityUtil.VELOCITY_ENCODING);
            String subject = getResultString(new VelocityContext(), subjectTemplate);
            message.setSubject(subject);

            VelocityContext context = createVelocityContextForWelcomeEmail(to, password);
            Template velocityTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER
                            + locale.getLanguage() + '_' + EmailTemplate.WELCOME_EMAIL.getFileName(),
                    VelocityUtil.VELOCITY_ENCODING);
            String emailBody = getResultString(context, velocityTemplate);

            mimeMessage.setContent(emailBody, "text/plain; charset=UTF-8");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Mail service isn't available");
        }
    }

    @Override
    public void sendWelcomeEmail(String to, Locale locale) {
        sendWelcomeEmail(to, null, locale);
    }

    @Override
    public void sendWelcomeEmployeeEmail(String to, String password) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = null;
        try {
            message = new MimeMessageHelper(mimeMessage, true, VelocityUtil.VELOCITY_ENCODING);
            message.setTo(to);

            Template subjectTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER +
                    EmailTemplate.WELCOME_EMPLOYEE_EMAIL.getSubjectFilename(), VelocityUtil.VELOCITY_ENCODING);
            String subject = getResultString(new VelocityContext(), subjectTemplate);
            message.setSubject(subject);

            VelocityContext context = createVelocityContextForWelcomeEmail(to, password);
            Template velocityTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER +
                    EmailTemplate.WELCOME_EMPLOYEE_EMAIL.getFileName(), VelocityUtil.VELOCITY_ENCODING);
            String emailBody = getResultString(context, velocityTemplate);

            mimeMessage.setContent(emailBody, "text/plain; charset=UTF-8");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Mail service isn't available");
        }
    }

    @Override
    public void sendEmployEmail(String to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = null;
        try {
            message = new MimeMessageHelper(mimeMessage, true, VelocityUtil.VELOCITY_ENCODING);
            message.setTo(to);

            Template subjectTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER +
                    EmailTemplate.EMPLOY_EMAIL.getSubjectFilename(), VelocityUtil.VELOCITY_ENCODING);
            String subject = getResultString(new VelocityContext(), subjectTemplate);
            message.setSubject(subject);

            VelocityContext context = createVelocityContextForSetStatusEmail(UserRole.ROLE_EMPLOYEE.name());
            Template velocityTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER +
                    EmailTemplate.EMPLOY_EMAIL.getFileName(), VelocityUtil.VELOCITY_ENCODING);
            String emailBody = getResultString(context, velocityTemplate);

            mimeMessage.setContent(emailBody, "text/plain; charset=UTF-8");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Mail service isn't available");
        }
    }

    @Override
    public void sendDismissEmail(String to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = null;
        try {
            message = new MimeMessageHelper(mimeMessage, true, VelocityUtil.VELOCITY_ENCODING);
            message.setTo(to);

            Template subjectTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER + EmailTemplate.DISMISS_EMAIL
                    .getSubjectFilename(), VelocityUtil.VELOCITY_ENCODING);
            String subject = getResultString(new VelocityContext(), subjectTemplate);
            message.setSubject(subject);

            VelocityContext context = createVelocityContextForSetStatusEmail(UserRole.ROLE_CLIENT.name());
            Template velocityTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER +
                    EmailTemplate.DISMISS_EMAIL.getFileName(), VelocityUtil.VELOCITY_ENCODING);
            String emailBody = getResultString(context, velocityTemplate);

            mimeMessage.setContent(emailBody, "text/plain; charset=UTF-8");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Mail service isn't available");
        }
    }

    @Override
    public void sendNotifyPurchaseTicketEmail(String to, Ticket ticket, Locale locale) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = null;
        try {
            message = new MimeMessageHelper(mimeMessage, true, VelocityUtil.VELOCITY_ENCODING);
            message.setTo(to);

            Template subjectTemplate = Velocity.getTemplate(
                    VelocityUtil.VELOCITY_TEMPLATES_FOLDER + locale.getLanguage() + '_' + EmailTemplate.NOTIFY_PURCHASE
                            .getSubjectFilename(), VelocityUtil.VELOCITY_ENCODING);
            String subject = getResultString(new VelocityContext(), subjectTemplate);
            message.setSubject(subject);

            Multipart mp = new MimeMultipart();
            Template velocityTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER
                            + locale.getLanguage() + '_' + EmailTemplate.NOTIFY_PURCHASE.getFileName(),
                    VelocityUtil.VELOCITY_ENCODING);
            String emailBody = getResultString(new VelocityContext(), velocityTemplate);

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(emailBody, "text/plain; charset=UTF-8");
            mp.addBodyPart(htmlPart);

            MimeBodyPart attachment = new MimeBodyPart();
            InputStream attachmentDataStream = new ByteArrayInputStream(documentService.ticketPdfView(ticket));
            attachment.setFileName("ticket.pdf");
            attachment.setContent(attachmentDataStream, "application/pdf");
            mp.addBodyPart(attachment);
            mimeMessage.setContent(mp);

            mimeMessage.setContent(emailBody, "text/plain; charset=UTF-8");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Mail service isn't available");
        }
    }

    private String getResultString(VelocityContext context, Template velocityTemplate) {
        StringWriter writer = new StringWriter();
        velocityTemplate.merge(context, writer);
        return writer.toString();
    }

    private VelocityContext createVelocityContextForSetStatusEmail(String status) {
        VelocityContext context = new VelocityContext();
        context.put("status", status);
        return context;
    }

    private VelocityContext createVelocityContextForWelcomeEmail(String to, String password) {
        VelocityContext context = new VelocityContext();
        context.put("userEmail", to);
        context.put("password", password);
        return context;
    }

    private enum EmailTemplate {
        WELCOME_EMAIL("welcomeEmail.vm", "welcomeEmailSubject.vm"),
        WELCOME_EMPLOYEE_EMAIL("welcomeEmployeeEmail.vm", "welcomeEmployeeEmailSubject.vm"),
        EMPLOY_EMAIL("statusSetEmail.vm", "statusSetEmailSubject.vm"),
        DISMISS_EMAIL("statusSetEmail.vm", "statusSetEmailSubject.vm"),
        NOTIFY_PURCHASE("notifyPurchaseTicket.vm", "notifyPurchaseTicket.vm");

        private String fileName;

        private String subjectFilename;

        EmailTemplate(String fileName, String subjectFilename) {
            this.fileName = fileName;
            this.subjectFilename = subjectFilename;
        }

        public String getSubjectFilename() {
            return subjectFilename;
        }

        public String getFileName() {
            return fileName;
        }
    }

}
