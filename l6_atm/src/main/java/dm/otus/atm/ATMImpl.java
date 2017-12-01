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
    public void loadCash(Nominal nominal, int quantity) throws CashError {
        if (quantity < 0) {
            throw new CashError("Загрузка отрицательного количества банкнот не возможна");
        }
        cash.put(nominal, cash.get(nominal)+quantity);
    }

    @Override
    public Map<Nominal, Integer> giveCash(int sum) throws CashError {
        if (sum < 0) {
            throw new CashError("Запрошена отрицательная сумма");
        }
        Map<Nominal,Integer> res = new HashMap<>();
        int restSum = sum;
        for(int i=Nominal.values().length-1;i>=0;i--) {
            Nominal curNominal = Nominal.values()[i];
            int divRes = restSum / curNominal.getValue();
            if (divRes > cash.get(curNominal)) {
                divRes = cash.get(curNominal);
            }
            if (divRes > 0) {
                res.put(curNominal, divRes);
                restSum -= divRes * curNominal.getValue();
                cash.put(curNominal, cash.get(curNominal)-divRes);
            }
            if (restSum == 0) {
                return res;
            }
        }
        throw new CashError("По техническим причинам невозможно выдать указаную сумму");
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
