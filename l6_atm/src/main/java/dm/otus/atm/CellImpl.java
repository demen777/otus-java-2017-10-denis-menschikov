package dm.otus.atm;

public class CellImpl implements Cell {
    final private Nominal nominal;
    private int quantity;

    public CellImpl(Nominal nominal) {
        this.nominal = nominal;
        this.quantity = 0;
    }

    @Override
    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public void loadCash(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void giveCash(int quantity) throws CellError {
        if (quantity > this.quantity) {
            throw new CellError("В ячейке отсуствует запрошенное количество банкнот");
        }
        this.quantity -= quantity;
    }
}
