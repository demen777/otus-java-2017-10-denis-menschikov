package dm.otus.atm;

import java.util.Map;

@SuppressWarnings({"WeakerAccess"})
public interface ATM {
    void loadCash(Nominal nominal, int quantity) throws CashError;
    Map<Nominal, Integer> giveCash(int sum) throws CashError;
    Map<Nominal, Integer> getCashInfo();
    int getTotalValue();

    class CashError extends Exception {
        public CashError(String msg) {
            super(msg);
        }
    }
}
