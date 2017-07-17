package ca.bcit.mycoffeeapp;

/**
 * Created by nicholaschow on 2017-07-16.
 */

public class Drink {

    private Coffee drinkName;
    private int shots;
    private Flavour flavour;
    private boolean hotOrCold;
    private MilkType milkType;

    public Drink() {

    }

    /**
     * Drink Constructor
     * @param drinkName
     * @param shots shots can vary, default should be 2.
     * @param flavour
     * @param hotOrCold Hot = 1 , cold = 0
     * @param milkType
     */
    public Drink (Coffee drinkName, int shots, Flavour flavour, boolean hotOrCold, MilkType milkType) {
        this.drinkName = drinkName;
        this.shots = shots;
        this.flavour = flavour;
        this.hotOrCold = hotOrCold;
        this.milkType = milkType;
    }


    public Coffee getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(Coffee drinkName) {
        this.drinkName = drinkName;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public Flavour getFlavour() {
        return flavour;
    }

    public void setFlavour(Flavour flavour) {
        this.flavour = flavour;
    }

    public boolean isHotOrCold() {
        return hotOrCold;
    }

    public void setHotOrCold(boolean hotOrCold) {
        this.hotOrCold = hotOrCold;
    }

    public MilkType getMilkType() {
        return milkType;
    }

    public void setMilkType(MilkType milkType) {
        this.milkType = milkType;
    }




}
