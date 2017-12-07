package dm.otus.atm;

import java.util.Set;

@SuppressWarnings({"WeakerAccess"})
public interface ATM extends Restorable {
    @SuppressWarnings("unused")
    void addCell(Cell cell);
    void removeCell(Nominal nominal);
    Set<Nominal> getAvailableNominals();

    void loadCash(Cash cash) throws ATMError;
    Cash giveCash(int sum) throws ATMError;
    Cash getCashInfo();

    class ATMError extends Exception {
        public ATMError(String msg) {
            super(msg);
        }
    }
}
