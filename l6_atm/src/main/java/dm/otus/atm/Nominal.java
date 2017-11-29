package dm.otus.atm;


public enum Nominal {
    RUB10,
    RUB50,
    RUB100,
    RUB500,
    RUB1000,
    RUB5000;

    public int getValue() {
        return Integer.parseInt(this.name().substring(3));
    }

    @SuppressWarnings("unused")
    public static Nominal fromInt(int value) {
        return Nominal.valueOf("RUB" + value);
    }
}
