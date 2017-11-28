package dm.otus.atm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ATMImpl implements ATM {

    private final HashMap<Nominal, Integer> cash;

    @SuppressWarnings("WeakerAccess")
    public ATMImpl() {
        cash = new HashMap<>();
        for(Nominal nominal:Nominal.values()){
            cash.put(nominal, 0);
        }
    }

    @Override
    public void loadCash(Nominal nominal, int quantity) {
        cash.put(nominal, cash.get(nominal)+quantity);
    }

    @Override
    public Map<Nominal, Integer> giveCash(int sum) throws GiveCashError {
        if (sum < 0) {
            throw new GiveCashError("Запрошена отрицательная сумма");
        }
        if (sum > getTotalValue()) {
    }

    @Override
    public Map<Nominal, Integer> getCashInfo() {
        return Collections.unmodifiableMap(cash);
    }

    @Override
    public int getTotalValue() {
        return cash.entrySet().stream().mapToInt(entry -> entry.getKey().getValue()*entry.getValue()).sum();
    }
}
