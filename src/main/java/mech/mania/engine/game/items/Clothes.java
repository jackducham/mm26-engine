package mech.mania.engine.game.items;

public class Clothes extends Wearable {
    private double physicalDefense = 0;
    private double magicalDefense = 0;

    public Clothes(double buyPrice, double sellPrice, double physicalDefense, double magicalDefense) {
        super(buyPrice, sellPrice);
        this.physicalDefense = physicalDefense;
        this.magicalDefense = magicalDefense;
    }
}
