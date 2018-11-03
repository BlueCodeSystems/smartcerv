package zm.gov.moh.core.model.submodule;

import zm.gov.moh.core.model.Criteria;

public interface CriteriaSubmodule extends Submodule {

    boolean evaluateByCriteria(Object string);
    void setCriteria(Criteria criteria);
    Criteria getCriteria();
}
