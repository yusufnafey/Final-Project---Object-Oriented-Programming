/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;

/**
 *
 * @author Yusuf Nafey & Salaheddine Soufi
 */
public final class Perishable extends AbstractItem {
    private long Id;
    private String Name;
    private double Price;
    private Date DateAdded;
    private Date ExpirationDate;
    /**
     *
     */
    public Perishable() {

    }

    /**
     *
     * @param name
     * @param price
     * @param expirationDate
     */
    public Perishable(String name, double price, Date expirationDate) {
        this.Id = new java.util.Date().getTime() + new Random().nextInt(Integer.MAX_VALUE);
        this.Name = name;
        this.Price = price;
        this.DateAdded = Refrigerator.getCurrentDate();
        this.ExpirationDate = expirationDate;
    }

    /**
     *
     * @param perishable
     */
    public Perishable(Perishable perishable) {
        this.Id = perishable.getId();
        this.Name = perishable.getName();
        this.Price = perishable.getPrice();
        this.DateAdded = perishable.getDateAdded();
        this.ExpirationDate = perishable.getExpirationDate();
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
     * @return the dateAdded
     */
    @Override
    public Date getDateAdded() {
        return DateAdded;
    }

    /**
     * @param dateAdded the dateAdded to set
     */
    @Override
    public void setDateAdded(Date dateAdded) {
        this.DateAdded = dateAdded;
    }

    /**
     * @return the ExpirationDate
     */
    public Date getExpirationDate() {
        return ExpirationDate;
    }

    /**
     * @param expirationDate the ExpirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.ExpirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "id : " + this.getId() + "\n"
                + "name : " + this.getName() != null ? this.getName() : "" + "\n"
                + "price : " + this.getPrice() + "\n"
                + "dateAdded : " + this.getDateAdded() != null ? Refrigerator.getDateFormat().format(this.getDateAdded()) : ""
                + "expirationDate : " + this.getExpirationDate() != null ? Refrigerator.getDateFormat().format(this.getExpirationDate()) : "";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.Name);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.Price) ^ (Double.doubleToLongBits(this.Price) >>> 32));
        hash = 79 * hash + Objects.hashCode(this.DateAdded);
        hash = 79 * hash + Objects.hashCode(this.ExpirationDate);
        return hash;
    }

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

        final Perishable other = (Perishable) obj;

        if (this.Id != other.Id) {
            return false;
        }

        if (Double.doubleToLongBits(this.Price) != Double.doubleToLongBits(other.Price)) {
            return false;
        }

        if (!Objects.equals(this.Name, other.Name)) {
            return false;
        }

        if (!Objects.equals(this.DateAdded, other.DateAdded)) {
            return false;
        }

        return Objects.equals(this.ExpirationDate, other.ExpirationDate);
    }

    public static Predicate<Perishable> isExpired() {
        return perishable -> perishable.getExpirationDate().getTime() <= Refrigerator.getCurrentDate().getTime();
    }
}