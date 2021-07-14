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
    private double totalSales;
    private Item currentItem;
    private double currentBalance;
    private double sessionSales;

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
    public double selectItemAndGetPrice(Item item) {
        if (itemInventory.hasItem(item)) {
            currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold Out, Please buy another item");

    }


    @Override
    public void reset() {
        sessionSales = 0;
        currentItem = null;
        currentBalance = 0;

    }

    @Override
    public void collectItem() throws NotSufficientChangeException, NotFullPaidException {
        if (isFullPaid()) {
            if (hasSufficientChange()) {
                itemInventory.deduct(currentItem);
            } else {
                throw new NotSufficientChangeException("Not Sufficient change in Inventory");
            }
        } else {
            double remainingBalance = sessionSales - currentBalance;
            throw new NotFullPaidException("Price not full paid, remaining : ", remainingBalance);
        }
    }

    private void collectChange(double sessionSales) {
        List<Money> change = getChange(Math.round(sessionSales * 100.0) / 100.0);
        if (sessionSales != 0) {
            System.out.println("Here is your change:");
        }
        for (Money money : change) {
            System.out.println(money);
        }
        updateCashInventory(change);
        this.currentBalance = 0;
        printOptions();
    }

    private boolean isFullPaid() throws TooMuchMoneyException {
        try {
            if (currentBalance > 50) {
                return (Math.round(currentBalance * 100.0) / 100.0) >= currentItem.getPrice();
            }
        } catch (TooMuchMoneyException tooMuchMoneyException) {
            System.out.println("You can not introduce more than 50 RON in the machine at once.");
        }
        return (Math.round(currentBalance * 100.0) / 100.0) >= currentItem.getPrice();
    }

    private List<Money> getChange(double amount) throws NotSufficientChangeException {
        List<Money> changes = Collections.EMPTY_LIST;

        if (amount > 0) {
            changes = new ArrayList<>();
            double balance = amount;
            while (balance > 0) {
                if (balance >= Money.ZeceLEI.getvalue() && cashInventory.hasItem(Money.ZeceLEI)) {
                    changes.add(Money.ZeceLEI);
                    balance = balance - Money.ZeceLEI.getvalue();

                } else if (balance >= Money.CinciLEI.getvalue() && cashInventory.hasItem(Money.CinciLEI)) {
                    changes.add(Money.CinciLEI);
                    balance = balance - Money.CinciLEI.getvalue();

                } else if (balance >= Money.UnLEU.getvalue() && cashInventory.hasItem(Money.UnLEU)) {
                    changes.add(Money.UnLEU);
                    balance = balance - Money.UnLEU.getvalue();

                } else if (balance >= Money.CinzeciBANI.getvalue() && cashInventory.hasItem(Money.CinzeciBANI)) {
                    changes.add(Money.CinzeciBANI);
                    balance = balance - Money.CinzeciBANI.getvalue();

                } else if (balance >= Money.ZeceBANI.getvalue() && cashInventory.hasItem(Money.ZeceBANI)) {
                    changes.add(Money.ZeceBANI);
                    balance = balance - Money.ZeceBANI.getvalue();

                } else {
                    throw new NotSufficientChangeException("NotSufficientChange, Please try another product ");
                }
            }
        }

        return changes;
    }

    public void printStats() {
        System.out.println("Total Sales : " + totalSales);
        double aux;
        if (this.currentItem != null && currentBalance != 0) {
            aux = sessionSales + currentItem.getPrice();
        } else {
            aux = 0;
        }
        System.out.println("This session sales : " + aux);
        System.out.println("Sum of money you've introduce until now : " + Math.round(currentBalance * 100.0) / 100.0);
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
        return hasSufficientChangeForAmount((Math.round(currentBalance * 100.0) / 100.0) - sessionSales);
    }

    private boolean hasSufficientChangeForAmount(double amount) {
        boolean hasChange = true;
        try {
            getChange(amount);
        } catch (NotSufficientChangeException nsce) {
            hasChange = false;
        }
        return hasChange;
    }

    private void updateCashInventory(List<Money> change) {
        for (Money c : change) {
            cashInventory.deduct(c);
        }
    }

    @Override
    public void paymentAction(Item item) {
        double aux = (item.getPrice() + sessionSales - this.currentBalance);
        if (aux > 0) {
            System.out.println("\nYou choose " + item.getName() + " and must introduce " + aux + " RON in the machine");
            paymentMessage();
        } else {
            System.out.println("\nYou choose " + item.getName() + " and you don't have to introduce any other money, for now");
        }
        while (this.currentBalance < item.getPrice() + sessionSales) {
            Scanner money = new Scanner(System.in);
            String cash = money.nextLine();
            switch (cash) {
                case "ZeceLEI", "10" -> {
                    this.currentBalance += Money.ZeceLEI.getvalue();
                    cashInventory.add(Money.ZeceLEI);
                }
                case "CinciLEI", "5" -> {
                    this.currentBalance += Money.CinciLEI.getvalue();
                    cashInventory.add(Money.CinciLEI);
                }
                case "UnLEU", "1" -> {
                    this.currentBalance += Money.UnLEU.getvalue();
                    cashInventory.add(Money.UnLEU);
                }
                case "ZeceBANI", "0.1" -> {
                    this.currentBalance += Money.ZeceBANI.getvalue();
                    cashInventory.add(Money.ZeceBANI);
                }
                case "CinzeciBANI", "0.5" -> {
                    this.currentBalance += Money.CinzeciBANI.getvalue();
                    cashInventory.add(Money.CinzeciBANI);
                }
                default -> System.out.println("You must put one of the options above");

            }
        }
        collectItem();
        this.totalSales += item.getPrice();
    }


    @Override
    public void executeProduct(Item item) {
        selectItemAndGetPrice(item);
        paymentAction(item);
        printOptions();
    }

    @Override
    public void executeOption() {
        sessionSales = 0;
        while (true) {
            Scanner console = new Scanner(System.in);
            String option = console.nextLine();
            switch (option) {
                case "List", "1" -> printOptions();

                case "Coke", "2" -> {
                    executeProduct(Item.COLA);
                    sessionSales += Item.COLA.getPrice();
                }
                case "Lays", "3" -> {
                    executeProduct(Item.LAYS);
                    sessionSales += Item.LAYS.getPrice();
                }
                case "Snickers", "4" -> {
                    executeProduct(Item.SNICKERS);
                    sessionSales += Item.SNICKERS.getPrice();
                }
                case "Mask", "5" -> {
                    executeProduct(Item.MASK);
                    sessionSales += Item.MASK.getPrice();
                }
                case "Sandwich", "6" -> {
                    executeProduct(Item.SANDWICH);
                    sessionSales += Item.SANDWICH.getPrice();
                }
                case "Change", "7" -> {
                    if (this.currentItem == null) {
                        System.out.println("You have to chose a product first");
                    } else {
                        collectChange(this.currentBalance - this.sessionSales);
                    }
                    reset();
                }
                case "Quit", "8" -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid option! Please chose one of the options above!");
                    printOptions();
                }
            }
        }
    }
}
