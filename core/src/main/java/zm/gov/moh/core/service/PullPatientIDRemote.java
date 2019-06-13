package zm.gov.moh.core.service;

import zm.gov.moh.core.repository.database.entity.custom.Identifier;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullPatientIDRemote extends RemoteService {


    final int SOURCE = 1;
    final int BATCH_SIZE = 10;
    final int RESERVED = 5;
    final short NOT_ASSIGNED = 0;


    public PullPatientIDRemote(){
        super(ServiceManager.Service.PULL_PATIENT_ID_REMOTE);
    }

    @Override
    protected void executeAsync() {

        int identifiersNotAssigned = db.identifierDao().count(NOT_ASSIGNED);

        if(RESERVED > identifiersNotAssigned) {

            ConcurrencyUtils.consumeAsync(identifierList -> {

                        for (String identifier : identifierList.getIdentifiers())
                            db.identifierDao().insert(new Identifier(identifier));

                        notifyCompleted();
                    }, this::onError,
                    restApi.getIdentifiers(accessToken, SOURCE, BATCH_SIZE),
                    TIMEOUT);
        }
        else
            notifyCompleted();
    }
}
