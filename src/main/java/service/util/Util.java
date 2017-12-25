package service.util;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import java.io.File;

public class Util {

    public static final String APPLICATION_PROPERTIES_PATH = "C:\\Projects\\DeCore\\Traveling Agency\\travelingAgency\\src\\main\\resources\\properties\\application.properties";
    public static <T> T getBean(final String beanName, final Class<T> clazz) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return (T) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, beanName);
    }

    public static String getApplicationPropertiesPath() {
        return APPLICATION_PROPERTIES_PATH;
    }
}
