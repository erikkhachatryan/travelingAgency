package service.util;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

public class Util {
    public static <T> T getBean(final String beanName, final Class<T> clazz) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return (T) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, beanName);
    }
}
