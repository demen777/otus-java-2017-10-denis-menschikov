package dm.otus.l15_msg.messages;

import dm.otus.l15_msg.message_system.SyncMessageAnswer;

@SuppressWarnings("WeakerAccess")
public class SyncMsgCheckLoginAnswer extends SyncMessageAnswer {
    private final boolean isValidLogin;

    public SyncMsgCheckLoginAnswer(boolean isValidLogin) {
        super(SyncMsgCheckLoginAnswer.class);
        this.isValidLogin = isValidLogin;
    }

    public boolean isValidLogin() {
        return isValidLogin;
    }
}
