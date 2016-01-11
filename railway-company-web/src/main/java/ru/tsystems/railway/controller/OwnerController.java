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

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@RequestMapping(value = "/owner")
public class OwnerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MailService mailService;

    @Autowired
    @Qualifier("encoder")
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/index")
    public String ownerPage(Model model) {
        model.addAttribute("employeeSet", accountService.employeeSet());
        return "owner/index";
    }

    @Transactional
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@RequestParam(value = "email") String email,
                              RedirectAttributes redirectAttributes, Principal principal) {
        User user = accountService.getUserByEmail(email);
        if (accountService.getUserByEmail(email) == null) {
            String password = PasswordUtil.generate();
            try {
                accountService.createNewUser(email, passwordEncoder.encode(password), UserRole.ROLE_EMPLOYEE);
                LOGGER.info("Employee with email " + email + " has successfully added by " + principal.getName());
                mailService.sendWelcomeEmployeeEmail(email, password);
                redirectAttributes.addFlashAttribute("infoMsg",
                        messageSource.getMessage(MessagesDescription.USER_SUCCESSFULLY_REGISTERED, null,
                                LocaleContextHolder.getLocale()));
            } catch (UserCreationException e) {
                redirectAttributes.addFlashAttribute("errorMsg",
                        messageSource.getMessage(MessagesDescription.USER_ALREADY_EXIST_ERROR, null,
                                LocaleContextHolder.getLocale()));
            }
        } else if (user.getUserRole() == UserRole.ROLE_CLIENT) {
            accountService.employUser(user.getId());
            mailService.sendEmployEmail(email);
            redirectAttributes.addFlashAttribute("infoMsg",
                    messageSource.getMessage(MessagesDescription.USER_WAS_EMPLOY, null,
                            LocaleContextHolder.getLocale()));
            LOGGER.info("Role user with id " + user.getId() + "was set to Employee by " + principal.getName());
        } else {
            redirectAttributes.addFlashAttribute("errorMsg",
                    messageSource.getMessage(MessagesDescription.USER_ROLE_UPPER_THAN_CLIENT_ERROR, null,
                            LocaleContextHolder.getLocale()));
        }

        return "redirect:/owner/index";
    }

    @RequestMapping(value = "/dismissEmployee", method = RequestMethod.POST)
    public String dismissEmployee(@RequestParam(value = "employeeId") Long employeeId,
                                  RedirectAttributes redirectAttributes, Principal principal) {
        if (accountService.dismissEmployee(employeeId)) {
            mailService.sendDismissEmail(accountService.getUserEmail(employeeId));
            LOGGER.info("Role user with id " + employeeId + " was set to User by " + principal.getName());
            redirectAttributes.addFlashAttribute("infoMsg",
                    messageSource.getMessage(MessagesDescription.USER_WAS_DISMISSED, null,
                            LocaleContextHolder.getLocale()));
        } else {
            redirectAttributes.addFlashAttribute("errorMsg",
                    messageSource.getMessage(MessagesDescription.SYSTEM_CANT_DISMISS_USER, null,
                            LocaleContextHolder.getLocale()));
        }
        return "redirect:/owner/index";
    }
}
