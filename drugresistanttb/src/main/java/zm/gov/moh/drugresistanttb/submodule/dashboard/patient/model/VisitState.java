package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model;

import org.threeten.bp.ZonedDateTime;

public class VisitState {

    public static final int STARTED = 1;
    public static final int ENDED = 0;
    private int state;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}