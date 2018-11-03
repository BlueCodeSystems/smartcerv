package zm.gov.moh.core.model.submodule;

import android.graphics.Bitmap;

import zm.gov.moh.core.model.Criteria;

public class CriteriaSubmoduleImpl extends AbstractIconCriteriaSubmodule {

    CriteriaSubmoduleImpl(String name, Class classInstance, Bitmap icon, Criteria criteria ){
        super(name,classInstance,icon, criteria);
    }

    CriteriaSubmoduleImpl(String name, Class classInstance, Criteria criteria ){
        super(name,classInstance, criteria);
    }

    @Override
    public void setCriteria(Criteria criteria) {
        super.setCriteria(criteria);
    }

    @Override
    public Criteria getCriteria() {
        return super.getCriteria();
    }
}
