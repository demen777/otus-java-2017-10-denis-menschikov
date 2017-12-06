package dm.otus.atm;

import java.util.Collection;

@SuppressWarnings({"WeakerAccess"})
public interface ATM {
    void addCell(Cell cell);
    void removeCell(Nominal nominal);
    Collection<Nominal> getAvailableNominals();

    void loadCash(Cash cash) throws ATMError;
    Cash giveCash(int sum) throws ATMError;
    Cash getCashInfo();

    class ATMError extends Exception {
        public ATMError(String msg) {
            super(msg);
        }
    }
}
