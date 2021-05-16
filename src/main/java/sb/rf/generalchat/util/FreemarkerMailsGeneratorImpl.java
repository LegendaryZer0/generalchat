package sb.rf.generalchat.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class FreemarkerMailsGeneratorImpl implements MailsGenerator {
    @Autowired
    private Configuration configuration;


    @Override
    public String getEmailforConfirm(String serverUrl, Long userId) {
        Template confirmMailTemplate;
        try {
            confirmMailTemplate = configuration.getTemplate("mails/ConfirmMail.ftlh");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        // подготовили данные для шаблона
        log.info("usersId {}",userId);
        Map<String, String> attributes = new HashMap<>();
        attributes.put("users_id", userId.toString());
        attributes.put("server_url", serverUrl);

        StringWriter writer = new StringWriter();
        try {
            // записали в StringWriter текст сообщения
            confirmMailTemplate.process(attributes, writer);
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
        // получили текст сообщения из шаблона
        return writer.toString();

    }
}
