/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Yusuf Nafey & Salaheddine Soufi 
 */
public class main {
    
    private final static String INVALID_INPUT = "Invalid input, please try again";
    private final static String RETURNING = "Returning to main menu ";
    private final static String MESSAGE_PREFIX = "Please enter the ";
    private final static String MESSAGE_SUFFIX = ": ";

    public static void main(String[] args) {

        while (true) {
            main.showMenu();
            char menuOption = readChar("selection");
            switch (menuOption) {
                case 'A':
                    main.addItem();
                    break;
                case 'B':
                    main.printItemsList(Refrigerator.getAllItems());
                    main.removeItem(readString("Item ID to remove"));
                    break;
                case 'C':
                    main.manageNeeds();
                    break;
                case 'D':
                    main.setDate(readDate("new current date "));
                    break;
                case 'E':
                    main.printItemsList(Refrigerator.getAllItems());
                    break;
                case 'Q':
                    System.out.println("Thank you for using the program");
                    return;
                default:
                    System.err.println(INVALID_INPUT);
                    break;
            }
        }
    }
    
    private static void addMustHaveItem() {

        System.out.println("Adding a must have item");
        MustHaveItem p = new MustHaveItem();
        p.setId(new Date().getTime());
        p.setName(readString("name"));
        p.setPrice(readDouble("price $"));
        p.setDateAdded(Refrigerator.getCurrentDate());
        Refrigerator.addMustHaveItem(p);
        System.out.println("Must have item was added into the list successfully");
    }
    
    private static void removeItem(String st) {

        try {
            AbstractItem item = Refrigerator.getItemWithId(Long.parseLong(st));
            Refrigerator.getAllItems().remove(item);
            System.out.println("Item with ID " + st + " removed successfully");
        } catch (Exception ex) {
            System.out.println("No item with ID " + st);
        }
    }
    
    private static void removeMustHaveItem(String st) {
        
        try {
            AbstractItem item = Refrigerator.getMustHaveItemWithId(Long.parseLong(st));
            Refrigerator.getAllMustHaveItems().remove(item);
            System.out.println("Must have item with ID " + st + " removed successfully!");
        } catch (Exception ex) {
            System.out.println("No must have item with ID " + st);
        }
    }
    
    private static void setDate(Date date) {

        Refrigerator.setCurrentDate(date);
        System.out.println("Current date set to " + Refrigerator.getCurrentDateFormatted());
    }
    
    private static void manageNeeds() {

        while (true) {
            main.manage();
            switch (readChar("selection")) {
                case 'A':
                    main.addMustHaveItem();
                    break;
                case 'B':
                    main.printMustHaveItemsList();
                    main.removeMustHaveItem(readString("Must have item ID to remove"));
                    break;
                case 'C':
                    main.printMustHaveItemsList();
                    break;
                case 'D':
                    main.printItemsList(Refrigerator.getQuestionableItemsList());
                    break;
                case 'E':
                    main.printItemsList(Refrigerator.getExpiredItemsList());
                    break;
                case 'F':
                    Refrigerator.buildGroceryList();
                    main.printGroceryList();
                    break;
                case 'Q':
                    return;
                default:
                    System.err.println(INVALID_INPUT);
            }
        }
    }
    
    private static void addItem() {

        while (true) {
            main.askItemType();
            switch (readChar("selection")) {
                case 'A':
                    System.out.println("Adding a perishable item");
                    Perishable p = new Perishable();
                    p.setId(new Date().getTime());
                    p.setName(readString("name"));
                    p.setPrice(readDouble("price $"));
                    p.setDateAdded(Refrigerator.getCurrentDate());
                    p.setExpirationDate(readDate("expiration date "));
                    Refrigerator.addItem(p);
                    System.out.println("Perishable item was added into the refrigerator successfully");
                    break;
                case 'B':
                    System.out.println("Adding a leftover item");
                    Leftover l = new Leftover();
                    l.setId(new Date().getTime());
                    l.setName(readString("name"));
                    l.setPrice(readDouble("price $"));
                    l.setDateAdded(Refrigerator.getCurrentDate());
                    l.setQuestionableAfter(readInt("days it is questionable after"));
                    Refrigerator.addItem(l);
                    System.out.println("Leftover item was added into the refrigerator successfully");
                    break;
                case 'Q':
                    System.err.println(RETURNING);
                    return;
                default:
                    System.err.println(INVALID_INPUT);
            }
        }
    }
    
    private static void printGroceryList() {

        if (GroceryList.ALREADY_IN_BUT_EXPIRED.isEmpty()
                && GroceryList.ALREADY_IN_BUT_QUESTIONABLE.isEmpty()
                && GroceryList.NOT_IN.isEmpty()) {
            System.err.println("Must have list is empty, please add some items first");
            return;
        }
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        GroceryList.ALREADY_IN_BUT_EXPIRED.stream().forEach(item -> {
            total.set(total.get() + item.getPrice());
            System.out.println(printFormat(item.getName(), 25)
//                    + printFormat(Refrigerator.getDecimalFormat().format(item.getPrice()), 10)
                  + printFormat(Refrigerator.getDecimalFormat().format(Refrigerator.getMustHaveItemWithName(item.getName()).getPrice()), 10)
                    + printFormat("Perishable item already in but expired on " + Refrigerator.getDateFormat().format(((Perishable) item).getExpirationDate()), 100)
            );
        });
        GroceryList.ALREADY_IN_BUT_QUESTIONABLE.stream().forEach(item -> {
            total.set(total.get() + item.getPrice());
            System.out.println(printFormat(item.getName(), 25)
//                    + printFormat(Refrigerator.getDecimalFormat().format(item.getPrice()), 10)
                  + printFormat(Refrigerator.getDecimalFormat().format(Refrigerator.getMustHaveItemWithName(item.getName()).getPrice()), 10)
                    + printFormat("Leftover item already in but questionable since " + Refrigerator.getDateFormat().format(Leftover.getQuestionableDate(item.getDateAdded(), ((Leftover) item).getQuestionableAfter())), 100)
            );
        });
        GroceryList.NOT_IN.stream().forEach(item -> {
            total.set(total.get() + item.getPrice());
            System.out.println(printFormat(item.getName(), 25)
                    + printFormat(Refrigerator.getDecimalFormat().format(item.getPrice()), 10)
                    + printFormat("Must have item but not in the refrigerator", 100)
            );
        });
        System.out.println("Total value of grocery items: " + Refrigerator.getDecimalFormat().format(total.get()));
    }
    
    private static void printMustHaveItemsList() {

        if (Refrigerator.getAllMustHaveItems().size() > 0) {
            System.out.println(
                    printFormat("ITEM ID", 20)
                    + printFormat("NAME", 20)
                    + printFormat("PRICE", 10)
            );
            Refrigerator.getAllMustHaveItems().stream().forEach(item -> {
                System.out.println(""
                        + printFormat(item.getId() + "", 20)
                        + printFormat(item.getName(), 20)
                        + printFormat(Refrigerator.getDecimalFormat().format(item.getPrice()), 10)
                );
            });
        } else {
            System.err.println("No items in the must have items list yet, please add some");
        }
    }
    
    private static void printItemsList(ArrayList<AbstractItem> list) {
        if (list.size() > 0) {
            System.out.println(
                    printFormat("ITEM ID", 20)
                    + printFormat("NAME", 20)
                    + printFormat("PRICE", 10)
                    + printFormat("ADDED ON", 12)
                    + printFormat("ITEM TYPE", 18)
                    + printFormat("EXPIRATION/QUESTIONABLE", 30)
                    + printFormat("STATUS", 30)
            );
            list.stream().forEach(item -> {
                System.out.println(""
                        + printFormat(item.getId() + "", 20)
                        + printFormat(item.getName(), 20)
                        + printFormat(Refrigerator.getDecimalFormat().format(item.getPrice()), 10)
                        + printFormat(Refrigerator.getDateFormat().format(item.getDateAdded()), 12)
                        + (item instanceof Perishable
                                ? "Perishable        " + printFormat("Expiration date: " + Refrigerator.getDateFormat().format(((Perishable) item).getExpirationDate()), 30)
                                + (Perishable.isExpired().test(((Perishable) item)) ? "Item is expired" : "Item is good")
                                : "Leftover          " + printFormat("Questionable after: " + ((Leftover) item).getQuestionableAfter() + " days", 30)
                                + (Leftover.isQuestionable().test((Leftover) item) ? "Item is questionable"
                                : ("Item is still usable. "
                                + Leftover.getDaysLeftUntillItBecomesQuestionable(
                                        (Leftover.getQuestionableDate(item.getDateAdded(), ((Leftover) item).getQuestionableAfter())),
                                        Refrigerator.getCurrentDate()) + " days left.")))
                );
            });
            System.out.println(" |------------ STATISTICS ----------------|");
            System.out.println(" | Number of items in refrigerator:  " + printFormat(Refrigerator.getAllItems().size() + "", 5) + "|");
            System.out.println(" | Perishable items count:           " + printFormat(Refrigerator.getPerishableCount() + "", 5) + "|");
            System.out.println(" | Leftover items count:             " + printFormat(Refrigerator.getLeftoverCount() + "", 5) + "|");
            System.out.println(" | Expired items count:              " + printFormat(Refrigerator.getExpiredItemCount() + "", 5) + "|");
            System.out.println(" | Questionable items count:         " + printFormat(Refrigerator.getQuestionableItemCount() + "", 5) + "|");
            System.out.println(" | Good perishable items count:      " + printFormat((Refrigerator.getPerishableCount() - Refrigerator.getExpiredItemCount()) + "", 5) + "|");
            System.out.println(" | Usable leftover items count:      " + printFormat((Refrigerator.getLeftoverCount() - Refrigerator.getQuestionableItemCount()) + "", 5) + "|");
            System.out.println(" |-------------- COSTS -------------------|");
            System.out.println(" | Value of expired items:      " + printFormat(Refrigerator.getDecimalFormat().format(Refrigerator.getValueOfExpiredItems()) + "", 10) + "|");
            System.out.println(" | Value of questionable items: " + printFormat(Refrigerator.getDecimalFormat().format(Refrigerator.getValueOfQuestionableItems()) + "", 10) + "|");
            System.out.println(" |----------------------------------------|");
        } else {
            System.err.println("No items in the Refrigerator yet. Please add some!");
        }
    }
    
    private static Date readDate(String message) {

        while (true) {
            String st = readString(message + "(" + Refrigerator.getDateFormat().toPattern() + ")");
            try {
                Date date = Refrigerator.getDateFormat().parse(st.trim());
                return date;
            } catch (ParseException e) {
                System.err.println(INVALID_INPUT);
            }
        }
    }
    
    private static String printFormat(String string, int length) {

        StringBuilder out = new StringBuilder(string);
        if (string.length() < length) {
            for (int x = out.length(); x < length; x++) {
                out.append(" ");
            }
        } else {
            return string.substring(0, length);
        }
        return out.toString();
    }
    
    public static char readChar(String message) {

        while (true) {
            System.out.print(MESSAGE_PREFIX + message + MESSAGE_SUFFIX);
            try {
                String temp = new Scanner(System.in).nextLine().trim();
                if (temp != null && temp.length() == 1) {
                    return temp.toUpperCase().charAt(0);
                }
            } catch (Exception e) {
                System.err.println(INVALID_INPUT);
            }
        }
    }
    
    public static int readInt(String message) {

        while (true) {
            try {
                System.out.print(MESSAGE_PREFIX + message + MESSAGE_SUFFIX);
                return new Scanner(System.in).nextInt();
            } catch (Exception e) {
                System.err.println(INVALID_INPUT);
            }
        }
    }
    
    public static int readInt(String message, int min, int max) {

        while (true) {
            try {
                System.out.print(MESSAGE_PREFIX + message + "[Integer between " + min + " - " + max + "]" + MESSAGE_SUFFIX);
                int number = new Scanner(System.in).nextInt();
                if (number < min || number > max) {
                    throw new Exception();
                }
                return number;
            } catch (Exception e) {
                System.err.println(INVALID_INPUT);
            }
        }
    }
    
    public static double readDouble(String message) {

        while (true) {
            try {
                System.out.print(MESSAGE_PREFIX + message + MESSAGE_SUFFIX);
                return new Scanner(System.in).nextDouble();
            } catch (Exception e) {
                System.err.println(INVALID_INPUT);
            }
        }
    }
    
    public static String readString(String message) {

        while (true) {
            try {
                System.out.print(MESSAGE_PREFIX + message + MESSAGE_SUFFIX);
                String temp = new Scanner(System.in).nextLine().trim();
                if (temp != null && !temp.isEmpty()) {
                    return temp.toUpperCase().trim();
                }
            } catch (Exception e) {
                System.err.println(INVALID_INPUT);
            }
        }
    }
    
    private static void showMenu() {

        System.out.println(""
                + " |-------------------------------------------------------------------|\n"
                + " |-----       *MAIN  MENU* [Current Date : " + Refrigerator.getCurrentDateFormatted() + "]         ------|\n"
                + " |-------------------------------------------------------------------|\n"
                + " | A/a: Enter A or a to add an item                                  |\n"
                + " | B/b: Enter B or b to remove an item                               |\n"
                + " | C/c: Enter C or c to manage needs                                 |\n"
                + " | D/d: Enter D or d to set the date                                 |\n"
                + " | E/e: Enter E or e to print the list of all items in refrigerator  |\n"
                + " | Q/q: Enter Q or q to quit                                         |\n"
                + " |-------------------------------------------------------------------|"
        );
    }
    
    private static void manage() {

        System.out.println(""
                + " |----------------------------------------------------------------|\n"
                + " |-----                      *MANAGE*                       ------|\n"
                + " |----------------------------------------------------------------|\n"
                + " | A/a: Enter A or a to add a must have item                      |\n"
                + " | B/b: Enter B or b to remove a must have item                   |\n"
                + " | C/c: Enter C or c to print the list of must have items         |\n"
                + " | D/d: Enter D or d to print the list of questionable items      |\n"
                + " | E/e: Enter E or e to print the list of expired items           |\n"
                + " | F/f: Enter F or f to print the grocery list                    |\n"
                + " | Q/q: Enter Q or q go back to main menu                         |\n"
                + " |----------------------------------------------------------------|"
        );
    }
    
    private static void askItemType() {

        System.out.println(""
                + " |----------------------------------------------------------------|\n"
                + " |-----                  *ADDING AN ITEM*                   ------|\n"
                + " |----------------------------------------------------------------|\n"
                + " | A/a: Enter A or a to add a perishable item                     |\n"
                + " | B/b: Enter B or b to add a leftover item                       |\n"
                + " | Q/q: Enter Q or q go back to main menu                         |\n"
                + " |----------------------------------------------------------------|"
        );
    }
}
