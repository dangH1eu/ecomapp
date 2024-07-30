package com.project.ecomapp.component;

import com.project.ecomapp.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
public class LocalizationUtil {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    @Autowired
    public LocalizationUtil(MessageSource messageSource, LocaleResolver localeResolver){
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    public String getLocalizedMessage(String messageKey, Object... params){
        HttpServletRequest request = WebUtil.getCurrentRequest();
        Locale locale = localeResolver.resolveLocale(request);
        return messageSource.getMessage(messageKey, params, locale);
    }




}
