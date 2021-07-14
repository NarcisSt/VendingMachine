package items;

public enum Item {

    COLA("Coke", 7), LAYS("Lays", 3), SNICKERS("Snickers", 1.9), MASK("Mask", 2), SANDWICH("Sandwich", 13);

    private String name;
    private double price;

    private Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

}
