package ru.tsystems.railway.util;

import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VelocityUtil {

    public static final String VELOCITY_TEMPLATES_FOLDER = "/velocityTemplates/";
    public static final String VELOCITY_ENCODING = "UTF-8";

    static {
        Velocity.setProperty("resource.loader", "class");
        Velocity.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
    }

}
