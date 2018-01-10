package dm.otus.sql.entity;

import dm.otus.sql.base.DataSet;

@SuppressWarnings("unused")
public class UserDataSet extends DataSet {
    private String name;
    private int age;


    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
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

}
