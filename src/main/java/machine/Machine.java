package machine;

import exceptions.NotFullPaidException;
import exceptions.NotSufficientChangeException;
import exceptions.SoldOutException;
import exceptions.TooMuchMoneyException;
import inventory.*;
import items.Item;
import money.Money;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Machine implements VendingMachine {
    private Inventory<Money> cashInventory = new Inventory<>();
    private Inventory<Item> itemInventory = new Inventory<>();
    private long totalSales;
    private Item currentItem;
    private long currentBalance;

    public Machine() {
        initialize();
    }

    public void initialize() {
        for (Money c : Money.values()) {
            cashInventory.put(c, 50);
        }
        for (Item i : Item.values()) {
            itemInventory.put(i, 50);
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
    public void insertMoney(Money money) {
        if (currentBalance > 50) {
            throw new TooMuchMoneyException("You can't introduce more than 50 RON in the machine");
        }
        currentBalance = (long) (currentBalance + money.getvalue());
        cashInventory.add(money);
    }

    @Override
    public List<Money> refund() {
        List<Money> refund = getChange(currentBalance);
        updateCashInventory(refund);
        currentBalance = 0;
        currentItem = null;
        return refund;

    }

//    @Override
//    public Bucket<Item, List<Money>> collectItemAndChange() {
//        Item item = collectItem();
//        totalSales = (long) (totalSales + currentItem.getPrice());
//        List<Money> change = collectChange();
//        return new Bucket<Item, List<Money>>(item, change);
//
//    }

    @Override
    public void reset() {
        cashInventory.clear();
        itemInventory.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;

    }

    private void collectItem(Item item) throws NotSufficientChangeException, NotFullPaidException {
        if (isFullPaid()) {
            if (hasSufficientChange()) {
                itemInventory.deduct(currentItem);
            } else {
                throw new NotSufficientChangeException("Not Sufficient change in Inventory");
            }
        } else {
            long remainingBalance = (long) (currentItem.getPrice() - currentBalance);
            throw new NotFullPaidException("Price not full paid, remaining : ", remainingBalance);
        }
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
        return currentBalance >= currentItem.getPrice();
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
        System.out.println("#################### Vending Machine ####################");
        System.out.println("Welcome!!");
    }

    public void printOptions() {
        System.out.println("Here is the current inventory:");
        printStats();
        System.out.println("These are your options:");
        System.out.println("1. List");
        System.out.println("2. Coke      7 RON");
        System.out.println("3. Lays      3 RON");
        System.out.println("4. Snickers  1.9 RON");
        System.out.println("5. Mask      2 RON");
        System.out.println("6. Sandwich  13 RON");
        System.out.println("7. Change");
        System.out.println("8. Quit");
        System.out.println("Please select one of the items above:");
    }

    public void paymentMessage() {
        System.out.println("Please insert the money in the machine:");
        System.out.println("Your options are:");
        System.out.println("ZeceLEI(10)");
        System.out.println("CinciLEI(5)");
        System.out.println("UnLEU(1)");
        System.out.println("CinzeciBANI(0.5)");
        System.out.println("ZeceBANI(0.1)");
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

    public void executeProduct(Item item) {
        selectItemAndGetPrice(item);
        System.out.println("\nYou chose " + item.getName() + " and must introduce " + item.getPrice() + " RON in the machine");
        paymentMessage();
        while (this.currentBalance < item.getPrice()) {
            Scanner money = new Scanner(System.in);
            String cash = money.nextLine();
            switch (cash) {
                case "ZeceLEI", "10" -> {
                    this.currentBalance += Money.ZeceLEI.getvalue();
                    cashInventory.add(Money.ZeceLEI);
                    break;
                }
                case "CinciLEI", "5" -> {
                    this.currentBalance += Money.CinciLEI.getvalue();
                    cashInventory.add(Money.CinciLEI);
                    break;
                }
                case "UnLEU", "1" -> {
                    this.currentBalance += Money.UnLEU.getvalue();
                    cashInventory.add(Money.UnLEU);
                    break;
                }
                case "ZeceBANI", "0.1" -> {
                    this.currentBalance += Money.ZeceBANI.getvalue();
                    cashInventory.add(Money.ZeceBANI);
                    break;
                }
                case "CinzeciBANI", "0.5" -> {
                    this.currentBalance += Money.CinzeciBANI.getvalue();
                    cashInventory.add(Money.CinzeciBANI);
                    break;
                }
                default -> {
                    System.out.println("You must put one of the options above");
                    break;
                }
            }
        }
        collectItem(item);
        this.totalSales += item.getPrice();
        long amount = (long) (currentBalance - item.getPrice());
        List<Money> change = getChange(amount);
        System.out.println("Here is your change:");
        for (Money money : change) {
            System.out.println(money);
        }
        updateCashInventory(change);
        this.currentBalance = 0;
        printOptions();
    }

    public void executeOption() {

        while (true) {
            Scanner console = new Scanner(System.in);
            String option = console.nextLine();
            switch (option) {
                case "List", "1" -> {
                    System.out.println("Here is the current inventory:");
                    printStats();
                    printOptions();
                    break;
                }
                case "Coke", "2" -> {
                    executeProduct(Item.COLA);
                    break;
                }
                case "Lays", "3" -> {
                    executeProduct(Item.LAYS);
                    break;
                }
                case "Snickers", "4" -> {
                    executeProduct(Item.SNICKERS);
                    break;
                }
                case "Mask", "5" -> {
                    executeProduct(Item.MASK);
                    break;
                }
                case "Sandwich", "6" -> {
                    executeProduct(Item.SANDWICH);
                    break;
                }
                case "Change", "7" -> {
                    if (this.currentItem == null) {
                        System.out.println("You have to chose a product first");
                    } else {

                    }
                    break;
                }
                case "Quit", "8" -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid option! Please chose one of the options above!");
                    printOptions();
                    break;
                }
            }
        }
    }
}
