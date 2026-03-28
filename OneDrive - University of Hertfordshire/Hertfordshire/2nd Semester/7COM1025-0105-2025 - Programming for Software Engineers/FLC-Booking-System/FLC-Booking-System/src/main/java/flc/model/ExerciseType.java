/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Devar
 */

package flc.model;

public enum ExerciseType {
    YOGA(12.00, "Yoga"),
    ZUMBA(10.00, "Zumba"),
    AQUACISE(8.00, "Aquacise"),
    BOX_FIT(15.00, "Box Fit"),
    BODY_BLITZ(14.00, "Body Blitz"),
    PILATES(13.00, "Pilates");

    private final double price;
    private final String displayName;

    ExerciseType(double price, String displayName) {
        this.price = price;
        this.displayName = displayName;
    }

    public double getPrice() { return price; }
    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}
