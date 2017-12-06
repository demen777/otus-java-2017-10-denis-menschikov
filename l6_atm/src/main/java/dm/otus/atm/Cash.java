package dm.otus.atm;

import java.util.Collection;
import java.util.HashMap;

public class Cash {
    private final HashMap<Nominal, Integer> quantities;

    public Cash(){
        quantities = new HashMap<>();
    }

    public int getValue()
    {
        return quantities.entrySet().stream().mapToInt(entry -> entry.getKey().getValue()*entry.getValue()).sum();
    }

    public void setQuantity(Nominal nominal, int quantity) throws CashError
    {
        if (quantity < 0) {
            throw new CashError("Количество банкнот должно быть неотрицательным числом");
        }
        quantities.put(nominal, quantity);
    }

    public int getQuantity(Nominal nominal)
    {
        return quantities.getOrDefault(nominal, 0);
    }

    public Collection<Nominal> getNominals()
    {
        return quantities.keySet();
    }

    public class CashError extends Exception {
        public CashError(String msg) {
            super(msg);
        }
    }
}
