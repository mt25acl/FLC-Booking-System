/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Devar
 */
package flc.model;

public class Review {
    private Member member;
    private Lesson lesson;
    private int rating;
    private String comment;

    public Review(Member member, Lesson lesson, int rating, String comment) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1-5.");
        this.member = member;
        this.lesson = lesson;
        this.rating = rating;
        this.comment = comment;
    }

    public Member getMember() { return member; }
    public Lesson getLesson() { return lesson; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    public String getRatingLabel() {
        return switch (rating) {
            case 1 -> "Very Dissatisfied";
            case 2 -> "Dissatisfied";
            case 3 -> "Ok";
            case 4 -> "Satisfied";
            case 5 -> "Very Satisfied";
            default -> "Unknown";
        };
    }

    @Override
    public String toString() {
        return member.getName() + " rated " + rating + "/5 (" + getRatingLabel() + "): " + comment;
    }
}
