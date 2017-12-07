package dm.otus.atm;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartmentImpl implements ATMDepartment {
    private final List<ATM> atms;

    public ATMDepartmentImpl() {
        atms = new ArrayList<>();
    }

    @Override
    public void addATM(ATM atm) {
        atms.add(atm);
    }

    @Override
    public Cash getTotalCash() {
        Cash res = new Cash();
        for(ATM atm:atms) {
            res.addCash(atm.getCashInfo());
        }
        return res;
    }

    @Override
    public void restoreInitState() {
        for(Restorable atm:atms) {
            atm.restoreState();
        }
    }
}
