package zm.gov.moh.smartcerv.component.login.model;

public class Credintials {

    private CharSequence username;
    private CharSequence password;

    public void setPassword(CharSequence password) {
        this.password = password;
    }

    public void setUsername(CharSequence username) {
        this.username = username;
    }

    public CharSequence getUsername() {
        return username;
    }

    public CharSequence getPassword() {
        return password;
    }

    public Credintials( final CharSequence username, final CharSequence password){
        this.username = username;
        this.password = password;
    }
}
