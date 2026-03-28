/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Devar
 */
package flc.model;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private int memberId;
    private String name;
    private String email;
    private List<Booking> bookings;

    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.bookings = new ArrayList<>();
    }

    public int getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Booking> getBookings() { return bookings; }

    public void addBooking(Booking b) { bookings.add(b); }
    public void removeBooking(Booking b) { bookings.remove(b); }

    public boolean hasConflict(Lesson lesson) {
        for (Booking b : bookings) {
            if (b.getLesson().getDay().equals(lesson.getDay()) &&
                b.getLesson().getTimeSlot().equals(lesson.getTimeSlot())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + memberId + "] " + name;
    }
}
