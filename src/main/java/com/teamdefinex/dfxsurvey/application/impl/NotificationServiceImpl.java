package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.NotificationService;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.Enumeration;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    @Override
    @SneakyThrows
    public Result<Void> send(String title, String to, String template, Dictionary<String, String> parameters) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String html = loadTemplate(template);

        Enumeration<String> keys = parameters.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = parameters.get(key);
            html = html.replace("{{" + key + "}}", value);
        }

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(title);
        helper.setText(html, true);

        mailSender.send(message);

        return Result.success(null);
    }

    @SneakyThrows
    private String loadTemplate(String templateName) {
        ClassPathResource resource =
                new ClassPathResource("templates/" + templateName + ".html");

        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
