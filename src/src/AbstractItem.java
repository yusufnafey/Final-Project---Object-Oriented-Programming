/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.Date;

/**
 *
 * @author Yusuf Nafey & Salaheddine Soufi
 */
public abstract class AbstractItem {
    /**
     * 
     * @return 
     */
    protected abstract long getId();
    /**
     * 
     * @param id 
     */
    protected abstract void setId(long id);
    /**
     * 
     * @return 
     */
    protected abstract String getName();
    /**
     * 
     * @param name 
     */
    protected abstract void setName(String name);
    /**
     * 
     * @return 
     */
    protected abstract double getPrice();
    /**
     * 
     * @param price 
     */
    protected abstract void setPrice(double price);
    /**
     * 
     * @return 
     */
    protected abstract Date getDateAdded();
    /**
     * 
     * @param dateAdded 
     */
    protected abstract void setDateAdded(Date dateAdded);
}