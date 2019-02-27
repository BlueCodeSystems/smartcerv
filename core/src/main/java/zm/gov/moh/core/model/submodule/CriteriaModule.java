package zm.gov.moh.core.model.submodule;

import zm.gov.moh.core.model.Criteria;

public interface CriteriaModule extends Module {

    boolean evaluateByCriteria(Object string);
    void setCriteria(Criteria criteria);
    Criteria getCriteria();
}
