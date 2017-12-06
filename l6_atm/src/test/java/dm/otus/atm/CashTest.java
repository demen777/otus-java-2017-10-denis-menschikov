package dm.otus.atm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CashTest {

    @Test
    void setQuantity_NegativeQuantity() {
        Cash cash = new Cash();
        Cash.CashError error = assertThrows(Cash.CashError.class, () -> cash.setQuantity(Nominal.RUB50, -50));
        assertEquals("Количество банкнот должно быть неотрицательным числом", error.getMessage());
    }

    @Test
    void getValue() throws Cash.CashError {
        Cash cash = new Cash();
        cash.setQuantity(Nominal.RUB10, 30);
        cash.setQuantity(Nominal.RUB50, 10);
        cash.setQuantity(Nominal.RUB100, 5);
        cash.setQuantity(Nominal.RUB500, 3);
        cash.setQuantity(Nominal.RUB1000, 2);
        cash.setQuantity(Nominal.RUB5000, 1);
        assertEquals(30*10+10*50+5*100+3*500+2*1000+5000, cash.getValue());
    }
}