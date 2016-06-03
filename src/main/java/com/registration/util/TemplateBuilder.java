package com.registration.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class TemplateBuilder {
    private static TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        TemplateBuilder.templateEngine = templateEngine;
    }

    public static String processTemplate(String templatePath, Context context) {
        return templateEngine.process(templatePath, context);
    }
}
