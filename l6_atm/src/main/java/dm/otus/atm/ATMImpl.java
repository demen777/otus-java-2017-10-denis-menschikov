package dm.otus.atm;

import java.util.*;

public class ATMImpl implements ATM {
    private final TreeMap<Nominal, Cell> cells;

    @SuppressWarnings("WeakerAccess")
    public ATMImpl(Cell[] initialCells) {
        cells = new TreeMap<>();
        for(Cell cell:initialCells) {
            addCell(cell);
        }
    }


    @Override
    public void addCell(Cell cell) {
        cells.put(cell.getNominal(), cell);
    }

    @Override
    public void removeCell(Nominal nominal) {
        cells.remove(nominal);
    }

    @Override
    public Collection<Nominal> getAvailableNominals() {
        return cells.keySet();
    }

    @Override
    public void loadCash(Cash cash) throws ATMError {
        for(Nominal nominal:cash.getNominals()){
            if(!cells.containsKey(nominal)) {
                throw new ATMError(String.format("Прием номинала %s не предусмотрен", nominal));
            }
        }
        for(Nominal nominal:cash.getNominals()) {
            cells.get(nominal).loadCash(cash.getQuantity(nominal));
        }
    }

    @Override
    public Cash giveCash(int sum) throws ATMError {
        if (sum < 0) {
            throw new ATMError("Запрошена отрицательная сумма");
        }
        Cash res = new Cash();
        int restSum = sum;
        for(Nominal nominal:cells.descendingKeySet()) {
            Cell cell = cells.get(nominal);
            int divRes = restSum / nominal.getValue();
            if (divRes > cell.getQuantity()) {
                divRes = cell.getQuantity();
            }
            if (divRes > 0) {
                try {
                    res.setQuantity(nominal, divRes);
                } catch (Cash.CashError cashError) {
                    cashError.printStackTrace();
                }
                restSum -= divRes * nominal.getValue();
            }
            if (restSum == 0) {
                break;
            }
        }
        if (restSum != 0) {
            throw new ATMError("По техническим причинам невозможно выдать указаную сумму");
        }
        for(Nominal nominal:res.getNominals()) {
            try {
                cells.get(nominal).giveCash(res.getQuantity(nominal));
            } catch (Cell.CellError cellError) {
                cellError.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public Cash getCashInfo() {
        Cash cash = new Cash();
        for(Nominal nominal:cells.keySet()){
            try {
                cash.setQuantity(nominal, cells.get(nominal).getQuantity());
            } catch (Cash.CashError cashError) {
                cashError.printStackTrace();
            }
        }
        return cash;
    }
}
