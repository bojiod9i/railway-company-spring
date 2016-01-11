package ru.tsystems.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tsystems.railway.util.MessagesDescription;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(required = false, value = "auth_error") Boolean authError, ModelMap model) {
        if (authError != null && authError) {
            model.addAttribute("errorMsg", messageSource.getMessage(MessagesDescription.INVALID_LOGIN_PARAMS, null,
                    LocaleContextHolder.getLocale()));
        }
        return "login";
    }
}
