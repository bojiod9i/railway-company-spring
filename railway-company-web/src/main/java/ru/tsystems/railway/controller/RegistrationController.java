package ru.tsystems.railway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.domain.accounts.UserRole;
import ru.tsystems.railway.exception.UserCreationException;
import ru.tsystems.railway.service.AccountService;
import ru.tsystems.railway.service.MailService;
import ru.tsystems.railway.util.MessagesDescription;
import ru.tsystems.railway.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier("encoder")
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/registerPage", method = RequestMethod.GET)
    public String renderRegisterPage() {
        return "registration";
    }


    @RequestMapping(value = "/registerNewUser", method = RequestMethod.POST)
    public String registerNewUser(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "confirm_password", required = false) String passwordConfirmation,
            @RequestParam(value = "generatePassword", required = false, defaultValue = "false") Boolean generatePassword,
            Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if (generatePassword) {
            password = PasswordUtil.generate();
        } else {
            if (PasswordUtil.validate(password, passwordConfirmation) != PasswordUtil.PasswordStatus.SUCCESS) {
                redirectAttributes.addFlashAttribute("errorMsg",
                        messageSource.getMessage(MessagesDescription.INVALID_INPUTTING_PARAMS, null,
                                LocaleContextHolder.getLocale()));
                return "redirect:registerPage";
            }
        }
        try {
            User user = accountService.createNewUser(email, passwordEncoder.encode(password), UserRole.ROLE_CLIENT);
            LOGGER.info("User with " + user.getId() + " has successfully completed the registration");
            if (generatePassword) {
                mailService.sendWelcomeEmail(email, password, LocaleContextHolder.getLocale());
                LOGGER.debug("Password is generated for email " + email);
            } else {
                mailService.sendWelcomeEmail(email, LocaleContextHolder.getLocale());
            }
        } catch (UserCreationException e) {
            redirectAttributes.addFlashAttribute("errorMsg",
                    messageSource.getMessage(MessagesDescription.USER_ALREADY_EXIST_ERROR, null,
                            LocaleContextHolder.getLocale()));
            return "redirect:registerPage";
        }

        redirectAttributes.addFlashAttribute("infoMsg",
                messageSource.getMessage(MessagesDescription.USER_SUCCESSFULLY_REGISTERED, null,
                        LocaleContextHolder.getLocale()));
        return "redirect:login";
    }
}
