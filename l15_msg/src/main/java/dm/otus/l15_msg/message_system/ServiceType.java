package dm.otus.l15_msg.message_system;

import java.util.Objects;

public class ServiceType {
    private final String name;

    public ServiceType(String name) {
        this.name = name;
    }

    public ServiceType(Class cacheInfoClass) {
        name = cacheInfoClass.getName();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceType serviceType = (ServiceType) o;
        return Objects.equals(name, serviceType.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
