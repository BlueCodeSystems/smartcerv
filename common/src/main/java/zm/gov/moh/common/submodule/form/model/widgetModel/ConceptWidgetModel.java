package zm.gov.moh.common.submodule.form.model.widgetModel;

import java.io.Serializable;

public interface ConceptWidgetModel extends Serializable {

     void setConceptId(long id);
     long getConceptId();
     void setDataType(String dataType);
     String getDataType();

     String getStyle();
     void setStyle(String style);
}
