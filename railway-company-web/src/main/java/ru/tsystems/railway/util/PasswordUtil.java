package ru.tsystems.railway.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class PasswordUtil {

    private PasswordUtil() {
    }

    public static String generate() {
        return RandomStringUtils.random(8, true, true);
    }

    public static PasswordStatus validate(String pwd, String pwdConfirmation) {
        if (StringUtils.isBlank(pwd)) {
            return PasswordStatus.EMPTY;
        } else if (!StringUtils.equals(pwd, pwdConfirmation)) {
            return PasswordStatus.NOT_CONFIRMED;
        } else {
            return PasswordStatus.SUCCESS;
        }
    }

    public enum PasswordStatus {
        EMPTY, NOT_CONFIRMED, SUCCESS
    }
}
