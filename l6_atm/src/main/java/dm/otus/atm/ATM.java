package dm.otus.atm;

import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface ATM {
    void loadCash(Nominal nominal, int quantity);
    Map<Nominal, Integer> giveCash(int sum) throws GiveCashError;
    Map<Nominal, Integer> getCashInfo();
    int getTotalValue();

    class GiveCashError extends Exception {
        public GiveCashError(String msg) {
            super(msg);
        }
    }
}
