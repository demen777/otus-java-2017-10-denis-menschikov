package dm.otus.l15_msg.message_system;

public class MsgRegisterService extends SyncMessage {
    private final ServiceType serviceType;

    MsgRegisterService(ServiceType serviceType) {
        super(MsgRegisterService.class);
        this.serviceType = serviceType;
    }

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    @Override
    public void exec(Object receiver) { }
}
