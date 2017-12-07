package dm.otus.atm;


public enum Nominal {
    RUB10(10),
    RUB50(50),
    RUB100(100),
    RUB500(500),
    RUB1000(1000),
    RUB5000(5000);

    private final int value;

    Nominal(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @SuppressWarnings("unused")
    public static Nominal fromInt(int value) {
        return Nominal.valueOf("RUB" + value);
    }
}
