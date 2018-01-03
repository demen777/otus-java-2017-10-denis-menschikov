package dm.otus.l10_hibernate.entity;

import dm.otus.l10_hibernate.base.DataSet;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("ALL")
@Entity
@Table(name="addresses")
public class AddressDataSet extends DataSet {
    private String street;

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
