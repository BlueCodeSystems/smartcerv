package zm.gov.moh.core.model;

public class EntityId {
    private long local;
    private long remote;

    public long getLocal() {
        return local;
    }

    public void setLocal(long local) {
        this.local = local;
    }

    public long getRemote() {
        return remote;
    }

    public void setRemote(long remote) {
        this.remote = remote;
    }
}