package zm.gov.moh.common.submodule.form.model;

public class TableData {
    String  type;
    String value;
    int posX;
    int posY;
    Query[] query;

    public Query[] getQuery() {
        return query;
    }

    public void setQuery(Query[] query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
