package dm.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ATMImplTest {
    private ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATMImpl();
    }

    private void loadCashInternal() {
        atm.loadCash(Nominal.RUB10, 20);
        atm.loadCash(Nominal.RUB50, 10);
        atm.loadCash(Nominal.RUB100, 5);
        atm.loadCash(Nominal.RUB500, 3);
        atm.loadCash(Nominal.RUB1000, 2);
        atm.loadCash(Nominal.RUB5000, 1);
        atm.loadCash(Nominal.RUB10, 10);
    }

    @Test
    void getCashInfo() {
        loadCashInternal();
        Map<Nominal, Integer> cashInfo = atm.getCashInfo();
        assertEquals(30, cashInfo.get(Nominal.RUB10).intValue());
        assertEquals(10, cashInfo.get(Nominal.RUB50).intValue());
        assertEquals(5, cashInfo.get(Nominal.RUB100).intValue());
        assertEquals(3, cashInfo.get(Nominal.RUB500).intValue());
        assertEquals(2, cashInfo.get(Nominal.RUB1000).intValue());
        assertEquals(1, cashInfo.get(Nominal.RUB5000).intValue());
    }

    @Test
    void getTotalValue() {
        loadCashInternal();
        assertEquals(30*10+10*50+5*100+3*500+2*1000+ 5000, atm.getTotalValue());
    }

    @Test
    void giveCash() throws ATM.GiveCashError {
        loadCashInternal();
        Map<Nominal, Integer> cash = atm.giveCash(6780);
        assertEquals(3, cash.get(Nominal.RUB10).intValue());
        assertEquals(1, cash.get(Nominal.RUB50).intValue());
        assertEquals(2, cash.get(Nominal.RUB100).intValue());
        assertEquals(1, cash.get(Nominal.RUB500).intValue());
        assertEquals(1, cash.get(Nominal.RUB1000).intValue());
        assertEquals(1, cash.get(Nominal.RUB5000).intValue());
    }

    @Test
    void giveCashNegativeSum() {
        loadCashInternal();
        ATM.GiveCashError error = assertThrows(ATM.GiveCashError.class, () -> atm.giveCash(-50));
        assertEquals("Запрошена отрицательная сумма", error.getMessage());
    }

    @Test
    void giveCashNotEnoughCash() {
        loadCashInternal();
        ATM.GiveCashError error = assertThrows(ATM.GiveCashError.class, () -> atm.giveCash(15000));
        assertEquals("По техническим причинам невозможно выдать указаную сумму", error.getMessage());
    }

    @Test
    void giveCashNoMultiplyToMinNominal() {
        loadCashInternal();
        ATM.GiveCashError error = assertThrows(ATM.GiveCashError.class, () -> atm.giveCash(61));
        assertEquals("По техническим причинам невозможно выдать указаную сумму", error.getMessage());
    }

    @Test
    void giveCashNoEnoughQuantitySmallNominal() {
        atm.loadCash(Nominal.RUB10, 20);
        atm.loadCash(Nominal.RUB1000, 2);
        ATM.GiveCashError error = assertThrows(ATM.GiveCashError.class, () -> atm.giveCash(210));
        assertEquals("По техническим причинам невозможно выдать указаную сумму", error.getMessage());
    }

}