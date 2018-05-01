package service.util;

import service.model.Entity;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {

    private static final String APPLICATION_PROPERTIES_PATH = "E:\\Projects\\Traveling Agency\\travelingAgency\\src\\main\\resources\\properties\\application.properties";
    public static final Integer ADMINISTRATOR_USER_ID = 0;
    private static Properties applicationProperties;

    public static <T> T getBean(final String beanName, final Class<T> clazz) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return (T) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, beanName);
    }

    private static Properties getApplicationProperties() {
        if (applicationProperties == null) {
            applicationProperties = new Properties();
            try {
                applicationProperties.load(new FileInputStream(APPLICATION_PROPERTIES_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return applicationProperties;
    }

    public static String getApplicationProperty(String key) {
        return getApplicationProperties().getProperty(key);
    }

    public static boolean isPhotoUploaded(Entity entity) {
        return entity != null && entity.getString("Photo") != null;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
