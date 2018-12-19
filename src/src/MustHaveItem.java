/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Yusuf Nafey & Salaheddine Soufi
 */
public final class MustHaveItem extends AbstractItem {

    private long Id;
    private String Name;
    private double Price;
    private Date DateAdded;

    /**
     *
     */
    public MustHaveItem() {

    }

    /**
     *
     * @param name
     * @param price
     */
    public MustHaveItem(String name, double price) {
        this.Id = new java.util.Date().getTime() + new Random().nextInt(Integer.MAX_VALUE);
        this.Name = name;
        this.Price = price;
    }

    /**
     *
     * @param mustHaveItem
     */
    public MustHaveItem(MustHaveItem mustHaveItem) {
        this.Id = mustHaveItem.getId();
        this.Name = mustHaveItem.getName();
        this.Price = mustHaveItem.getPrice();
        this.DateAdded = mustHaveItem.getDateAdded();
    }

    /**
     * @return the Id
     */
    @Override
    public long getId() {
        return Id;
    }

    /**
     * @param id the Id to set
     */
    @Override
    public void setId(long id) {
        this.Id = id;
    }

    /**
     * @return the Name
     */
    @Override
    public String getName() {
        return Name;
    }

    /**
     * @param name the Name to set
     */
    @Override
    public void setName(String name) {
        this.Name = name;
    }

    /**
     * @return the Price
     */
    @Override
    public double getPrice() {
        return Price;
    }

    /**
     * @param price the Price to set
     */
    @Override
    public void setPrice(double price) {
        this.Price = price;
    }

    /**
     * @return the DateAdded
     */
    @Override
    public Date getDateAdded() {
        return DateAdded;
    }

    /**
     * @param dateAdded the DateAdded to set
     */
    @Override
    public void setDateAdded(Date dateAdded) {
        this.DateAdded = dateAdded;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "id : " + this.getId() + "\n"
                + "name : " + this.getName() != null ? this.getName() : "" + "\n"
                + "price : " + this.getPrice() + "\n"
                + "dateAdded : " + this.getDateAdded() != null ? Refrigerator.getDateFormat().format(this.getDateAdded()) : "";
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MustHaveItem other = (MustHaveItem) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (Double.doubleToLongBits(this.Price) != Double.doubleToLongBits(other.Price)) {
            return false;
        }
        if (!Objects.equals(this.Name, other.Name)) {
            return false;
        }
        return Objects.equals(this.DateAdded, other.DateAdded);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 11 * hash + Objects.hashCode(this.Name);
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.Price) ^ (Double.doubleToLongBits(this.Price) >>> 32));
        hash = 11 * hash + Objects.hashCode(this.DateAdded);
        return hash;
    }
}