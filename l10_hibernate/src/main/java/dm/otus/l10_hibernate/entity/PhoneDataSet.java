package dm.otus.l10_hibernate.entity;

import dm.otus.l10_hibernate.base.DataSet;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {
    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
