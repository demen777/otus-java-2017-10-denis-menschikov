package dm.otus.l15_msg.api;

@SuppressWarnings("WeakerAccess")
public interface DBServiceAPI {
    Boolean checkLogin(String login, String password);
}
