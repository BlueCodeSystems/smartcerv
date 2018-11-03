package zm.gov.moh.core.model.submodule;

import android.graphics.Bitmap;

import zm.gov.moh.core.model.Criteria;
import zm.gov.moh.core.model.submodule.AbstractSubmodule;
import zm.gov.moh.core.model.submodule.CriteriaSubmodule;
import zm.gov.moh.core.model.submodule.IconSubmodule;

public abstract class AbstractIconCriteriaSubmodule extends AbstractSubmodule implements IconSubmodule,CriteriaSubmodule {

   Bitmap icon;
   Criteria criteria;

    public AbstractIconCriteriaSubmodule(String string, Class classInstance){
        super(string,classInstance);
    }

    public AbstractIconCriteriaSubmodule(String string, Class classInstance, Bitmap icon){
        super(string,classInstance);
        this.icon = icon;
    }

    public AbstractIconCriteriaSubmodule(String string, Class classInstance, Bitmap icon, Criteria criteria){
        this(string,classInstance,icon);
        this.criteria = criteria;
    }

    public AbstractIconCriteriaSubmodule(String string, Class classInstance, Criteria criteria){
        super(string,classInstance);
        this.criteria = criteria;
    }

    @Override
    public Bitmap getIcon() {
        return icon;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public boolean evaluateByCriteria(Object sample){

        try {
           return criteria.evaluate(sample);
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }
}
