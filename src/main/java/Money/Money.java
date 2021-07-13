package Money;

public enum Money {
    UnLEU(1), CinciLEI(5), ZeceLEI(10), ZeceBANI(0.1), CinzeciBANI(0.5);

    private double value;

    private Money(double value) {
        this.value = value;
    }

    public double getvalue() {
        return value;
    }

    }
