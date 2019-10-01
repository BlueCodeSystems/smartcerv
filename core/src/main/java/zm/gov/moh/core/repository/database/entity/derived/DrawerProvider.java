package zm.gov.moh.core.repository.database.entity.derived;

import androidx.room.ColumnInfo;

public class DrawerProvider {
    @ColumnInfo(name = "username")
    String userName;

    @ColumnInfo(name = "given_name")
    String firstName;

    @ColumnInfo(name = "family_name")
    String lastName;

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
