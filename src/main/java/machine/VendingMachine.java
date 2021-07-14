package machine;

import items.Item;

public interface VendingMachine {
    double selectItemAndGetPrice(Item item);
    void reset();
    void collectItem();
    void paymentAction(Item item);
    void executeProduct(Item item);
    void executeOption();

}
