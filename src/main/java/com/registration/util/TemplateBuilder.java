package com.registration.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.StringWriter;

public final class TemplateBuilder {
    @Autowired
    private static TemplateEngine templateEngine;
    private static StringWriter writer = new StringWriter();

    public static String proccessTemplate(String templatePath, Context context) {
        templateEngine.process(templatePath, context, writer);
        return writer.toString();
    }
}
