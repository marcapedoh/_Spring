package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.Email.EmailResponse;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
@Slf4j
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final String host = "imap.gmail.com";
    private final int port = 993;
    private final String username = "apedohmarc5@gmail.com";
    private final String password = "clwwgpvtvravtdgn";
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, email);
    }
    public void sendEmail(String to, String subject, String text) {
        if(isValidEmail(to)){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        }else{
            log.warn("Format de email non valide");
        }
    }
    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        int count = mimeMultipart.getCount();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
            } else if (bodyPart.isMimeType("multipart/*")) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }

    public List<EmailResponse> receiveEmails() {
        List<EmailResponse> ResponseGmailBox= new ArrayList<>();
        String contentString="";
        try {
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, port, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                // Traitez chaque e-mail ici
                String subject = message.getSubject();
                if(message.getContent() instanceof String){
                    contentString= (String) message.getContent();
                }else if(message.getContent() instanceof MimeMultipart){
                     contentString = getTextFromMimeMultipart((MimeMultipart) message.getContent());
                }
                //String content = message.getContent().toString();
                List<String> images = extractImagesFromContent(contentString);
            /*    System.out.println("Subject: " + subject);
                System.out.println("Content: " + content);*/
                ResponseGmailBox.add(new EmailResponse(subject,contentString,images));
            }
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseGmailBox ;
    }

    private List<String> extractImagesFromContent(String content) {
        List<String> images = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Elements imgTags = doc.select("img");
        for (Element imgTag : imgTags) {
            String imageUrl = imgTag.attr("src");
            images.add(imageUrl);
        }
        return images;
    }
}
