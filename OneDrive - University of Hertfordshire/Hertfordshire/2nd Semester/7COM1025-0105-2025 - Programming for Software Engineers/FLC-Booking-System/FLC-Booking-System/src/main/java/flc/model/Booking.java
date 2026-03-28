/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flc.model;

/**
 *
 * @author Devar
 */


public class Booking {
    private static int counter = 1;
    private final int bookingId;
    private final Member member;
    private Lesson lesson;

    public Booking(Member member, Lesson lesson) {
        this.bookingId = counter++;
        this.member = member;
        this.lesson = lesson;
    }

    public int getBookingId() { return bookingId; }
    public Member getMember() { return member; }
    public Lesson getLesson() { return lesson; }

    public void setLesson(Lesson lesson) { this.lesson = lesson; }

    @Override
    public String toString() {
        return "Booking #" + bookingId + " | " + member.getName() + " | " + lesson;
    }
