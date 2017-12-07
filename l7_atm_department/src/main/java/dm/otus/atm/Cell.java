package dm.otus.atm;

interface Cell {
    Nominal getNominal();
    void loadCash(int quantity);
    int getQuantity();
    void giveCash(int quantity) throws CellError;

    class CellError extends Exception {
        @SuppressWarnings("SameParameterValue")
        CellError(String msg) {
            super(msg);
        }
    }
}
