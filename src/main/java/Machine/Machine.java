package Machine;

import Exceptions.NotFullPaidException;
import Exceptions.NotSufficientChangeException;
import Exceptions.SoldOutException;
import Exceptions.TooMuchMoneyException;
import Inventory.*;
import Items.Item;
import Money.Money;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
@ToString
@EqualsAndHashCode
/**
 * This class describes the vending machine
 */
public class Machine implements VendingMachine {
    private Inventory<Money> cashInventory = new Inventory<Money>();
    private Inventory<Item> itemInventory = new Inventory<Item>();
    private long totalSales;
    private Item currentItem;
    private long currentBalance;

    public Machine() {
        initialize();
    }

    public void initialize() {
        for (Money c : Money.values()) {
            cashInventory.put(c, 5);
        }
        for (Item i : Item.values()) {
            itemInventory.put(i, 5);
        }
    }

    @Override
    public long selectItemAndGetPrice(Item item) {
        if (itemInventory.hasItem(item)) {
            currentItem = item;
            return (long) currentItem.getPrice();
        }
        throw new SoldOutException("Sold Out, Please buy another item");

    }

    @Override
    public void insertMoney(Money Money) {
        if (currentBalance > 50) {
            throw new TooMuchMoneyException("You can't introduce more than 50 RON in the machine");
        }
        currentBalance = (long) (currentBalance + Money.getvalue());
        cashInventory.add(Money);
    }

    @Override
    public List<Money> refund() {
        List<Money> refund = getChange(currentBalance);
        updateCashInventory(refund);
        currentBalance = 0;
        currentItem = null;
        return refund;

    }

    @Override
    public Bucket<Item, List<Money>> collectItemAndChange() {
        Item item = collectItem();
        totalSales = (long) (totalSales + currentItem.getPrice());
        List<Money> change = collectChange();
        return new Bucket<Item, List<Money>>(item, change);

    }

    @Override
    public void reset() {
        cashInventory.clear();
        itemInventory.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;

    }

    private Item collectItem() throws NotSufficientChangeException, NotFullPaidException {
        if (isFullPaid()) {
            if (hasSufficientChange()) {
                itemInventory.deduct(currentItem);
                return currentItem;
            }
            throw new NotSufficientChangeException("Not Sufficient change in Inventory");
        }
        long remainingBalance = (long) (currentItem.getPrice() - currentBalance);
        throw new NotFullPaidException("Price not full paid, remaining : ", remainingBalance);
    }


    private List<Money> collectChange() {
        long changeAmount = (long) (currentBalance - currentItem.getPrice());
        List<Money> change = getChange(changeAmount);
        updateCashInventory(change);
        currentBalance = 0;
        currentItem = null;
        return change;
    }

    private boolean isFullPaid() throws TooMuchMoneyException {
        if (currentBalance > 50) {
            throw new TooMuchMoneyException("You can't introduce more than 50 RON in the machine");
        }
        if (currentBalance >= currentItem.getPrice() && currentBalance <= 50) {
            return true;
        }
        return false;
    }

    private List<Money> getChange(long amount) throws NotSufficientChangeException {
        List<Money> changes = Collections.EMPTY_LIST;

        if (amount > 0) {
            changes = new ArrayList<>();
            long balance = amount;
            while (balance > 0) {
                if (balance >= Money.ZeceLEI.getvalue() && cashInventory.hasItem(Money.ZeceLEI)) {
                    changes.add(Money.ZeceLEI);
                    balance = (long) (balance - Money.ZeceLEI.getvalue());
                    continue;

                } else if (balance >= Money.CinciLEI.getvalue() && cashInventory.hasItem(Money.CinciLEI)) {
                    changes.add(Money.CinciLEI);
                    balance = (long) (balance - Money.CinciLEI.getvalue());
                    continue;

                } else if (balance >= Money.UnLEU.getvalue() && cashInventory.hasItem(Money.UnLEU)) {
                    changes.add(Money.UnLEU);
                    balance = (long) (balance - Money.UnLEU.getvalue());
                    continue;

                } else if (balance >= Money.CinzeciBANI.getvalue() && cashInventory.hasItem(Money.CinzeciBANI)) {
                    changes.add(Money.CinzeciBANI);
                    balance = (long) (balance - Money.CinzeciBANI.getvalue());
                    continue;

                } else if (balance >= Money.ZeceBANI.getvalue() && cashInventory.hasItem(Money.ZeceBANI)) {
                    changes.add(Money.ZeceBANI);
                    balance = (long) (balance - Money.ZeceBANI.getvalue());
                    continue;

                } else {
                    throw new NotSufficientChangeException("NotSufficientChange, Please try another product ");
                }
            }
        }

        return changes;
    }

    public void printStats() {
        System.out.println("Total Sales : " + totalSales);
        System.out.println("Current Item Inventory : " + itemInventory);
        System.out.println("Current Cash Inventory : " + cashInventory);
    }

    public void welcomeMessage() {
        System.out.println("#########################################################");
        System.out.println("#################### Vending Machine ####################");
        System.out.println("#########################################################");
        System.out.println("Welcome!!");
    }

    public void printOptions() {
        System.out.println("These are your options:");
        System.out.println("1. List");
        System.out.println("2. Coke      7 RON");
        System.out.println("3. Lays      3 RON");
        System.out.println("4. Snickers  1.9 RON");
        System.out.println("5. Mask      2 RON");
        System.out.println("6. Sandwich  13 RON");
        System.out.println("7. Quit");
    }

    public void paymentMessage() {
        System.out.println("Please insert the money in the machine:");
    }

    private boolean hasSufficientChange() {
        return hasSufficientChangeForAmount((long) (currentBalance - currentItem.getPrice()));
    }

    private boolean hasSufficientChangeForAmount(long amount) {
        boolean hasChange = true;
        try {
            getChange(amount);
        } catch (NotSufficientChangeException nsce) {
            return hasChange = false;
        }
        return hasChange;
    }

    private void updateCashInventory(List<Money> change) {
        for (Money c : change) {
            cashInventory.deduct(c);
        }
    }

    public long getTotalSales() {
        return totalSales;
    }

    public void executeProduct (Item item) {
        selectItemAndGetPrice(item);
        paymentMessage();
        Scanner money = new Scanner(System.in);
        int cash = money.nextInt();
        System.out.println(cash);
    }

    public void executeOption(String option) {
        while (true) {
            Scanner console = new Scanner(System.in);
            option = console.nextLine();
            switch (option) {
                case "List": {
                    printOptions();
                    break;
                }
                case "Coke": {
                    executeProduct(Item.COLA);
                    break;
                }
                case "Lays": {
                    executeProduct(Item.LAYS);
                    break;
                }
                case "Snickers": {
                    executeProduct(Item.SNICKERS);
                    break;
                }
                case "Mask": {
                    executeProduct(Item.MASK);
                    break;
                }
                case "Sandwich": {
                    executeProduct(Item.SANDWICH);
                    break;
                }
                case "Quit": {
                    return;
                }
                default: {
                    System.out.println("Invalid option! Please chose one of the options above!");
                    printOptions();
                    break;
                }
            }
        }
    }
}
