package ca.bcit.mycoffeeapp;

/**
 * Created by nicholaschow on 2017-07-16.
 */

public enum Coffee {
    LATTE ("Latte"),
    MOCHA ("Mocha"),
    CAPPUCCINO ("Cappuccino"),
    FLATWHITE ("Flat White"),
    CHAILATTE ("Chai Latte"),
    AMERICANO ("Americano"),
    MACCHIATO ("Macchiato"),
    ESPRESSO ("Espresso"),
    LONDONFOG ("London Fog");

    private final String coffee;

    Coffee(String coffee) {
        this.coffee = coffee;
    }

    public String toString() {
        return this.coffee;
    }
}
