package ca.bcit.mycoffeeapp;

/**
 * Created by nicholaschow on 2017-07-17.
 */

public class Order {
    private String userName;
    private Drink drink;

    public Order() {

    }

    public Order(String userName, Drink drink) {
        this.userName = userName;
        this.drink = drink;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }
}
