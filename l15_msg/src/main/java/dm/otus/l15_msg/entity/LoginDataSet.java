package dm.otus.l15_msg.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@SuppressWarnings("ALL")
@Entity
@Table(name="logins")
public class LoginDataSet extends DataSet {
    private String login;
    private String password;

    public LoginDataSet() {
    }

    public LoginDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
