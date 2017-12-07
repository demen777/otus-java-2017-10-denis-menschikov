package dm.otus.atm;

@SuppressWarnings("WeakerAccess")
public interface ATMDepartment {
    void addATM(ATM atm);
    Cash getTotalCash();
    void restoreInitState();
}
