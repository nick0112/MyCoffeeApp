package ca.bcit.mycoffeeapp;

/**
 * Created by nicholaschow on 2017-07-16.
 */

public enum Flavour {
    VANILLA ("Vanilla"),
    HAZELNUT ("Hazelnut"),
    Peppermint ("Peppermint"),
    Caramel ("Caramel"),
    Cinnamon ("Cinnamon");

    private final String flavour;

    Flavour(String coffee) {
        this.flavour = coffee;
    }

    public String toString() {
        return this.flavour;
    }
}
