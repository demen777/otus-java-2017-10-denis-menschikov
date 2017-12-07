package dm.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ATMDepartmentTest {
    private ATMDepartment atmDepartment;
    private ATM atm1;

    @BeforeEach
    void setUp() throws Cash.CashError, ATM.ATMError {
        Cell[] cells1 = {
                new CellImpl(Nominal.RUB10),
                new CellImpl(Nominal.RUB50),
                new CellImpl(Nominal.RUB100),
                new CellImpl(Nominal.RUB500),
                new CellImpl(Nominal.RUB1000),
                new CellImpl(Nominal.RUB5000)
        };
        atm1 = new ATMImpl(cells1);
        Cash cash1 = new Cash();
        cash1.setQuantity(Nominal.RUB10, 30);
        cash1.setQuantity(Nominal.RUB50, 10);
        cash1.setQuantity(Nominal.RUB100, 5);
        cash1.setQuantity(Nominal.RUB500, 3);
        cash1.setQuantity(Nominal.RUB1000, 2);
        cash1.setQuantity(Nominal.RUB5000, 1);
        atm1.loadCash(cash1);
        Cell[] cells2 = {
                new CellImpl(Nominal.RUB100),
                new CellImpl(Nominal.RUB500),
                new CellImpl(Nominal.RUB1000)
        };
        ATM atm2 = new ATMImpl(cells2);
        Cash cash2 = new Cash();
        cash2.setQuantity(Nominal.RUB100, 5);
        cash2.setQuantity(Nominal.RUB500, 3);
        cash2.setQuantity(Nominal.RUB1000, 2);
        atm2.loadCash(cash2);
        atmDepartment = new ATMDepartmentImpl();
        atmDepartment.addATM(atm1);
        atmDepartment.addATM(atm2);
    }

    @Test
    void getTotalCash() {
        Cash cash = atmDepartment.getTotalCash();
        assertEquals(30*10+10*50+(5+5)*100+(3+3)*500+(2+2)*1000+5000, cash.getValue());
    }

    @Test
    void restoreInitState() {
        Set<Nominal> nominalBeforeRemove = atm1.getAvailableNominals();
        atm1.removeCell(Nominal.RUB5000);
        Set<Nominal> nominalAfterRemove = new HashSet<>(nominalBeforeRemove);
        nominalAfterRemove.remove(Nominal.RUB5000);
        assertEquals(nominalAfterRemove, atm1.getAvailableNominals());
        atmDepartment.restoreInitState();
        assertEquals(0, atmDepartment.getTotalCash().getValue());
        assertEquals(nominalBeforeRemove, atm1.getAvailableNominals());
    }
}