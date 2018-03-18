package dm.otus.l15_msg.message_system;

class MsgRegisterServiceAnswer extends SyncMessageAnswer {
    private final boolean isSuccess;

    public MsgRegisterServiceAnswer(boolean isSuccess) {
        super(MsgRegisterServiceAnswer.class);
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
