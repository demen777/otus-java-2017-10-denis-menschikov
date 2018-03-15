package dm.otus.l15_msg.db;

import dm.otus.l15_msg.entity.LoginDataSet;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class LoginDAO {
    private final Session session;

    public LoginDAO(Session session) { this.session = session; }

    public boolean checkLogin(String login, String password) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<LoginDataSet> criteria = builder.createQuery(LoginDataSet.class);
        Root<LoginDataSet> from = criteria.from(LoginDataSet.class);
        criteria.where(builder.equal(from.get("login"), login));
        Query<LoginDataSet> query = session.createQuery(criteria);
        LoginDataSet loginDataSet = query.uniqueResult();
        return loginDataSet != null && password.equals(loginDataSet.getPassword());
    }
}
