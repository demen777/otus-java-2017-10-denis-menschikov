package dm.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    private ATM atm;

    @BeforeEach
    void setUp() {
        Cell[] cells = {
                new CellImpl(Nominal.RUB10),
                new CellImpl(Nominal.RUB50),
                new CellImpl(Nominal.RUB100),
                new CellImpl(Nominal.RUB500),
                new CellImpl(Nominal.RUB1000),
                new CellImpl(Nominal.RUB5000)
        };
        atm = new ATMImpl(cells);
    }

    private void loadCashInternal() throws Cash.CashError, ATM.ATMError {
        Cash cash = new Cash();
        cash.setQuantity(Nominal.RUB10, 30);
        cash.setQuantity(Nominal.RUB50, 10);
        cash.setQuantity(Nominal.RUB100, 5);
        cash.setQuantity(Nominal.RUB500, 3);
        cash.setQuantity(Nominal.RUB1000, 2);
        cash.setQuantity(Nominal.RUB5000, 1);
        atm.loadCash(cash);
    }


    @Test
    void getCashInfo() throws ATM.ATMError, Cash.CashError {
        loadCashInternal();
        Cash cashInfo = atm.getCashInfo();
        assertEquals(30, cashInfo.getQuantity(Nominal.RUB10));
        assertEquals(10, cashInfo.getQuantity(Nominal.RUB50));
        assertEquals(5, cashInfo.getQuantity(Nominal.RUB100));
        assertEquals(3, cashInfo.getQuantity(Nominal.RUB500));
        assertEquals(2, cashInfo.getQuantity(Nominal.RUB1000));
        assertEquals(1, cashInfo.getQuantity(Nominal.RUB5000));
    }

    @Test
    void giveCash() throws ATM.ATMError, Cash.CashError {
        loadCashInternal();
        Cash cash = atm.giveCash(6780);
        assertEquals(3, cash.getQuantity(Nominal.RUB10));
        assertEquals(1, cash.getQuantity(Nominal.RUB50));
        assertEquals(2, cash.getQuantity(Nominal.RUB100));
        assertEquals(1, cash.getQuantity(Nominal.RUB500));
        assertEquals(1, cash.getQuantity(Nominal.RUB1000));
        assertEquals(1, cash.getQuantity(Nominal.RUB5000));
    }

    @Test
    void giveCashNegativeSum() throws ATM.ATMError, Cash.CashError {
        loadCashInternal();
        ATM.ATMError error = assertThrows(ATM.ATMError.class, () -> atm.giveCash(-50));
        assertEquals("Запрошена отрицательная сумма", error.getMessage());
    }

    @Test
    void giveCashNotEnoughCash() throws ATM.ATMError, Cash.CashError {
        loadCashInternal();
        ATM.ATMError error = assertThrows(ATM.ATMError.class, () -> atm.giveCash(15000));
        assertEquals("По техническим причинам невозможно выдать указаную сумму", error.getMessage());
    }

    @Test
    void giveCashNoMultiplyToMinNominal() throws ATM.ATMError, Cash.CashError {
        loadCashInternal();
        ATM.ATMError error = assertThrows(ATM.ATMError.class, () -> atm.giveCash(61));
        assertEquals("По техническим причинам невозможно выдать указаную сумму", error.getMessage());
    }

    @Test
    void giveCashNoEnoughQuantitySmallNominal() throws ATM.ATMError, Cash.CashError {
        Cash cash = new Cash();
        cash.setQuantity(Nominal.RUB10, 20);
        cash.setQuantity(Nominal.RUB1000, 2);
        atm.loadCash(cash);
        ATM.ATMError error = assertThrows(ATM.ATMError.class, () -> atm.giveCash(210));
        assertEquals("По техническим причинам невозможно выдать указаную сумму", error.getMessage());
    }

    @Test
    void removeCell() throws ATM.ATMError, Cash.CashError {
        loadCashInternal();
        atm.removeCell(Nominal.RUB50);
        Cash cashInfo = atm.getCashInfo();
        assertEquals(30, cashInfo.getQuantity(Nominal.RUB10));
        assertEquals(0, cashInfo.getQuantity(Nominal.RUB50));
        assertEquals(5, cashInfo.getQuantity(Nominal.RUB100));
        assertEquals(3, cashInfo.getQuantity(Nominal.RUB500));
        assertEquals(2, cashInfo.getQuantity(Nominal.RUB1000));
        assertEquals(1, cashInfo.getQuantity(Nominal.RUB5000));
    }
}