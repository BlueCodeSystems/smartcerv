package zm.gov.moh.common.model;

import android.content.Context;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import zm.gov.moh.common.submodule.form.adapter.FormAdapter;
import zm.gov.moh.common.submodule.form.model.FormModel;
import zm.gov.moh.core.utils.Utils;

public class VisitMetadata implements Serializable {

    List<String> visitTypes;
    List<FormModel> formModels;

    public VisitMetadata(Context context, String jsonVisit){

        formModels = new LinkedList<>();
        try {

            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<DeserializedData> jsonAdapter = moshi.adapter(DeserializedData.class);
            DeserializedData visitMetadata = jsonAdapter.fromJson(jsonVisit);

            this.visitTypes = visitMetadata.getVisitTypes();


            for (String formResource: visitMetadata.getForms())
                formModels.add(FormAdapter.getAdapter().fromJson(Utils.getStringFromInputStream(context.getAssets().open("forms/" + formResource +".json"))));

        }catch (Exception e){
            Exception e1 = e;
        }

    }

    public List<String> getVisitTypes() {
        return visitTypes;
    }

    public List<FormModel> getFormModels() {
        return formModels;
    }
}

class DeserializedData implements Serializable{

    private List<String> visitTypes;
    private List<String> forms;

    public List<String> getForms() {
        return forms;
    }

    public List<String> getVisitTypes() {
        return visitTypes;
    }

    public void setForms(LinkedList<String> forms) {
        this.forms = forms;
    }

    public void setVisitTypes(LinkedList<String> visitTypes) {
        this.visitTypes = visitTypes;
    }
}