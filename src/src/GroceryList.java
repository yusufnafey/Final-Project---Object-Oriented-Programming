/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Yusuf Nafey & Salaheddine Soufi
 */
public class GroceryList {
    public static final AtomicReference<Double> VALUE = new AtomicReference<>(0.0);

    public static final ArrayList<AbstractItem> ALREADY_IN = new ArrayList<>();
    public static final ArrayList<AbstractItem> ALREADY_IN_BUT_EXPIRED = new ArrayList<>();
    public static final ArrayList<AbstractItem> ALREADY_IN_BUT_QUESTIONABLE = new ArrayList<>();
    public static final ArrayList<AbstractItem> NOT_IN = new ArrayList<>();

    /**
     *
     */
    public static void resetAll() {
        ALREADY_IN.clear();
        ALREADY_IN_BUT_EXPIRED.clear();
        ALREADY_IN_BUT_QUESTIONABLE.clear();
        NOT_IN.clear();
        VALUE.set(0.0);
    }
}