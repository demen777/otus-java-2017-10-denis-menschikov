package dm.otus.l10_hibernate.entity;

import dm.otus.l10_hibernate.base.DataSet;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class UserDataSet extends DataSet {
    private String name;
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones;

    public UserDataSet(String name, int age, AddressDataSet address, List<PhoneDataSet> phone) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones = phone;
    }

    public UserDataSet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }
}
