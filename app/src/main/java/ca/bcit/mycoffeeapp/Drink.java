package ca.bcit.mycoffeeapp;

/**
 * Created by nicholaschow on 2017-07-16.
 */

public class Drink {

    private String drinkName;
    private int shots;
    private String flavour;
    private String hotOrCold;
    private String milkType;

    public Drink() {

    }

    public Drink(String drinkName, int shots, String flavour, String hotOrCold, String milkType) {
        this.drinkName = drinkName;
        this.shots = shots;
        this.flavour = flavour;
        this.hotOrCold = hotOrCold;
        this.milkType = milkType;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getHotOrCold() {
        return hotOrCold;
    }

    public void setHotOrCold(String hotOrCold) {
        this.hotOrCold = hotOrCold;
    }

    public String getMilkType() {
        return milkType;
    }

    public void setMilkType(String milkType) {
        this.milkType = milkType;
    }
}
