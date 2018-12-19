/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Yusuf Nafey & Salaheddine Soufi
 */
public class Refrigerator {

    private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("$00.00");

    private static Date currentDate = new java.util.Date();

    private static final ArrayList<AbstractItem> LIST = new ArrayList<>(0);
    private static final HashSet<AbstractItem> MUST_HAVE_ITEMS_LIST = new HashSet<>(0);

    /**
     *
     */
    protected Refrigerator() {

    }

    /**
     *
     * @param item
     */
    protected static void addItem(AbstractItem item) {
        LIST.add(item);
    }

    /**
     *
     * @param item
     */
    protected static void addMustHaveItem(AbstractItem item) {
        MUST_HAVE_ITEMS_LIST.add(item);
    }

    /**
     *
     * @param item
     */
    protected static void removeItem(AbstractItem item) {
        LIST.remove(item);
    }

    /**
     *
     * @param item
     */
    protected static void removeMustHaveItem(AbstractItem item) {
        MUST_HAVE_ITEMS_LIST.remove(item);
    }

    /**
     *
     * @return
     */
    protected static ArrayList<AbstractItem> getAllItems() {
        return LIST;
    }

    /**
     *
     * @return
     */
    protected static HashSet<AbstractItem> getAllMustHaveItems() {
        return MUST_HAVE_ITEMS_LIST;
    }

    /**
     * @return the currentDate
     */
    protected static Date getCurrentDate() {
        return currentDate;
    }

    /**
     * @return the currentDate as a formatted String
     */
    protected static String getCurrentDateFormatted() {
        return getDateFormat().format(currentDate);
    }

    /**
     * @return the Perishable Items Count
     */
    protected static int getPerishableCount() {
        AtomicInteger i = new AtomicInteger();
        LIST.stream().forEach(item -> {
            if (item instanceof Perishable) {
                i.getAndIncrement();
            }
        });
        return i.get();
    }

    /**
     * @return the Leftover Items Count
     */
    protected static int getLeftoverCount() {
        AtomicInteger i = new AtomicInteger();
        LIST.stream().forEach(item -> {
            if (item instanceof Leftover) {
                i.getAndIncrement();
            }
        });
        return i.get();
    }

    /**
     * @return the Expired Perishable Items Count
     */
    protected static int getExpiredItemCount() {
        AtomicInteger i = new AtomicInteger(0);
        LIST.stream().forEach(item -> {
            if (item instanceof Perishable) {
                if (Perishable.isExpired().test((Perishable) item)) {
                    i.getAndIncrement();
                }
            }
        });
        return i.get();
    }

    /**
     *
     * @return
     */
    protected static int getQuestionableItemCount() {
        AtomicInteger i = new AtomicInteger(0);
        LIST.stream().forEach(item -> {
            if (item instanceof Leftover) {
                if (Leftover.isQuestionable().test((Leftover) item)) {
                    i.getAndIncrement();
                }
            }
        });
        return i.get();
    }

    /**
     * @return the Expired Perishable Items list
     */
    protected static ArrayList<AbstractItem> getExpiredItemsList() {
        ArrayList<AbstractItem> i = new ArrayList<>(0);
        LIST.stream().forEach(item -> {
            if (item instanceof Perishable) {
                if (Perishable.isExpired().test((Perishable) item)) {
                    i.add((Perishable) item);
                }
            }
        });
        return i;
    }

    /**
     *
     * @return
     */
    protected static ArrayList<AbstractItem> getQuestionableItemsList() {
        ArrayList<AbstractItem> i = new ArrayList(0);
        LIST.stream().forEach(item -> {
            if (item instanceof Leftover) {
                if (Leftover.isQuestionable().test((Leftover) item)) {
                    i.add((Leftover) item);
                }
            }
        });
        return i;
    }

    /**
     *
     * @return
     */
    protected static double getValueOfExpiredItems() {
        AtomicReference<Double> d = new AtomicReference<>(0.0);
        getExpiredItemsList().stream().forEach(item -> {
            d.set(d.get() + item.getPrice());
        });
        return d.get();
    }

    /**
     *
     * @return
     */
    protected static double getValueOfQuestionableItems() {
        AtomicReference<Double> d = new AtomicReference<>(0.0);
        getQuestionableItemsList().stream().forEach(item -> {
            d.set(d.get() + item.getPrice());
        });
        return d.get();
    }

    /**
     * @param currentDate the currentDate to set
     */
    protected static void setCurrentDate(Date currentDate) {
        Refrigerator.currentDate = currentDate;
    }

    protected static AbstractItem getItemWithName(String name) {
        AtomicReference<AbstractItem> item = new AtomicReference<>();
        LIST.stream().forEach(el -> {
            if (el.getName().equalsIgnoreCase(name)) {
                item.set(el);
            }
        });
        return item.get();
    }

    /**
     *
     * @param name
     * @return
     */
    protected static AbstractItem getMustHaveItemWithName(String name) {
        AtomicReference<AbstractItem> item = new AtomicReference<>();
        MUST_HAVE_ITEMS_LIST.stream().forEach(el -> {
            if (el.getName().equalsIgnoreCase(name)) {
                item.set(el);
            }
        });
        return item.get();
    }

    /**
     *
     * @param id
     * @return
     */
    protected static AbstractItem getItemWithId(Long id) {
        AtomicReference<AbstractItem> item = new AtomicReference<>();
        LIST.stream().forEach(el -> {
            if (el.getId() == id) {
                item.set(el);
            }
        });
        return item.get();
    }

    /**
     *
     * @param id
     * @return
     */
    protected static AbstractItem getMustHaveItemWithId(Long id) {
        AtomicReference<AbstractItem> item = new AtomicReference<>();
        MUST_HAVE_ITEMS_LIST.stream().forEach(el -> {
            if (el.getId() == id) {
                item.set(el);
            }
        });
        return item.get();
    }

    /**
     *
     * @param id
     */
    protected static void removeItemFromListWithId(long id) {
        AtomicInteger index = new AtomicInteger(-1);
        LIST.stream().forEach(item -> {
            if (item.getId() == id) {
                index.set(LIST.indexOf(item));
            }
        });
        if (index.get() > -1) {
            LIST.remove(index.get());
        }
    }
    
    /**
     *
     * @param id
     */
    protected static void removeItemFromMustHaveListWithId(long id) {
        AtomicReference<MustHaveItem> itemToDelete = new AtomicReference<>();
        MUST_HAVE_ITEMS_LIST.stream().forEach(item -> {
            if (item.getId() == id) {
                itemToDelete.set((MustHaveItem) item);
            }
        });
        try {
            if (itemToDelete.get() != null) {
                MUST_HAVE_ITEMS_LIST.remove(itemToDelete.get());
            }
        } catch (Exception e) {
        }
    }
    /**
     *
     */
    protected static void SortListByName() {
        Collections.sort(LIST, (AbstractItem item, AbstractItem t1) -> {
            String s1 = item.getName();
            String s2 = t1.getName();
            return s1.compareToIgnoreCase(s2);
        });
    }
    /**
     * @return the DATEFORMAT
     */
    protected static SimpleDateFormat getDateFormat() {
        return DATEFORMAT;
    }

    /**
     *
     * @return
     */
    protected static DecimalFormat getDecimalFormat() {
        return DECIMALFORMAT;
    }

    public static void buildGroceryList() {

        GroceryList.resetAll();

        MUST_HAVE_ITEMS_LIST.stream().forEach(el -> {
            if (Refrigerator.getItemWithName(el.getName()) != null) {
                AbstractItem item = Refrigerator.getItemWithName(el.getName());
                if (item instanceof Perishable) {
                    if (Perishable.isExpired().test((Perishable) item)) {
                        GroceryList.ALREADY_IN_BUT_EXPIRED.add(item);
                        GroceryList.VALUE.set(GroceryList.VALUE.get() + item.getPrice());
                    } 
                    else {
                        GroceryList.ALREADY_IN.add(item);
                    }
                } 
                else if (item instanceof Leftover) {
                    if (Leftover.isQuestionable().test((Leftover) item)) {
                        GroceryList.ALREADY_IN_BUT_QUESTIONABLE.add(item);
                        GroceryList.VALUE.set(GroceryList.VALUE.get() + item.getPrice());
                    } else {
                        GroceryList.ALREADY_IN.add(item);
                    }
                }
            } 
            else {
                GroceryList.NOT_IN.add(el);
                GroceryList.VALUE.set(GroceryList.VALUE.get() + el.getPrice());
            }
        });
    }
}
