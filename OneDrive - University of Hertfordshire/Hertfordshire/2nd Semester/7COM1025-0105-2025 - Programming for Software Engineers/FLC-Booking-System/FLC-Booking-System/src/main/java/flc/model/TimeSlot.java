/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Devar
 */

package flc.model;

public enum TimeSlot {
    MORNING("Morning", "09:00"),
    AFTERNOON("Afternoon", "13:00"),
    EVENING("Evening", "18:00");

    private final String label;
    private final String time;

    TimeSlot(String label, String time) {
        this.label = label;
        this.time = time;
    }

    public String getLabel() { return label; }
    public String getTime() { return time; }

    @Override
    public String toString() { return label + " (" + time + ")"; }
}
