package zm.gov.moh.core.repository.database.entity.derived;

public class StringIntEntry {
    private String string;
    private int integer;

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public int getInteger() {
        return integer;
    }
}
