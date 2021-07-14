package machine;

import inventory.Bucket;
import items.Item;
import money.Money;

import java.util.List;

public interface VendingMachine {
    public long selectItemAndGetPrice(Item item);
    public void insertMoney(Money money);
    public List<Money> refund();
    public Bucket<Item, List<Money>> collectItemAndChange();
    public void reset();

}
