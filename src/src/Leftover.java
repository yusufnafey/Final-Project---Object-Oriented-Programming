/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 *
 * @author Yusuf Nafey & Salaheddine Soufi
 */
public final class Leftover extends AbstractItem {

    private long Id;
    private String Name;
    private double Price;
    private Date DateAdded;
    private int QuestionableAfter;

    /**
     *
     */
    public Leftover() {

    }

    /**
     *
     * @param name
     * @param price
     * @param questionableAfter
     */
    public Leftover(String name, double price, int questionableAfter) {
        this.Id = new java.util.Date().getTime() + new Random().nextInt(Integer.MAX_VALUE);
        this.Name = name;
        this.Price = price;
        this.DateAdded = Refrigerator.getCurrentDate();
        this.QuestionableAfter = questionableAfter;
    }

    /**
     *
     * @param leftover
     */
    public Leftover(Leftover leftover) {
        this.Id = leftover.getId();
        this.Name = leftover.getName();
        this.Price = leftover.getPrice();
        this.DateAdded = leftover.getDateAdded();
        this.QuestionableAfter = leftover.getQuestionableAfter();
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
     * @return the QuestionableAfter
     */
    public int getQuestionableAfter() {
        return QuestionableAfter;
    }
    
    /**
     * @param questionableAfter the QuestionableAfter to set
     */
    public void setQuestionableAfter(int questionableAfter) {
        this.QuestionableAfter = questionableAfter;
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
                + "dateAdded : " + this.getDateAdded() != null ? Refrigerator.getDateFormat().format(this.getDateAdded()) : ""
                + "questionableAfter : " + this.getQuestionableAfter();
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
        final Leftover other = (Leftover) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (Double.doubleToLongBits(this.Price) != Double.doubleToLongBits(other.Price)) {
            return false;
        }
        if (this.QuestionableAfter != other.QuestionableAfter) {
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
        hash = 11 * hash + this.QuestionableAfter;
        return hash;
    }
    
    /**
     *
     * @param dateAdded
     * @param questionableAfter
     * @return
     */
    public static Date getQuestionableDate(Date dateAdded, int questionableAfter) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateAdded);
        cal.add(Calendar.DATE, questionableAfter);
        return cal.getTime();
    }
    
    public static long getDaysLeftUntillItBecomesQuestionable(Date questionableDate, Date today) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(questionableDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(today);

        return TimeUnit.DAYS.convert(cal1.getTime().getTime() -cal2.getTime().getTime(), TimeUnit.MILLISECONDS);
        // return (cal1.compareTo(cal2));
    }
    
    /**
     *
     * @return
     */
    public static Predicate<Leftover> isQuestionable() {
        return leftover -> getQuestionableDate(leftover.getDateAdded(), leftover.getQuestionableAfter()).getTime() <= Refrigerator.getCurrentDate().getTime();
    }
}