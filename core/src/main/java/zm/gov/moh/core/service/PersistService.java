package zm.gov.moh.core.service;


public abstract class PersistService extends BaseIntentService {

    protected final short PREFERRED = 1;
    protected final short NOT_PREFERRED = 0;
    protected final String MID_DAY_TIME = "T12:00:00Z";

    public PersistService(ServiceManager.Service service){
        super(service);

    }
}
