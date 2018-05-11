package service.converters;

import com.google.common.base.Objects;
import service.model.Classifier;
import service.model.EditableEntity;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.List;

/**
 * Created by Erik on 10/22/2017.
 */
@FacesConverter(value = "baseJSFSelectItemConverter")
public class BaseJSFSelectItemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        List<EditableEntity> items = (List<EditableEntity>) uiComponent.getAttributes().get("itemsList");
        for (EditableEntity item : items) {
            if (Objects.equal(item.getId(), Integer.valueOf(s))) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "";
        }
        return ((EditableEntity) o).getId().toString();
    }
}
