package ca.bcit.mycoffeeapp;

/**
 * Created by nicholaschow on 2017-07-16.
 */

public enum MilkType {
    SOY ("Soy"),
    MILK ("Milk"),
    DOUBLE ("Double"),
    WHOLE ("Whole"),
    ALMOND ("Almond");


    private final String milkType;

    MilkType(String milkType) {
        this.milkType = milkType;
    }

    public String toString() {
        return this.milkType;
    }


}
