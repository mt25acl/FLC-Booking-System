/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package flc.model;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private static final int MAX_CAPACITY = 4;

    private final int lessonId;
    private ExerciseType exerciseType;
    private final String day;         // "Saturday" or "Sunday"
    private TimeSlot timeSlot;
    private int weekNumber;
    private List<Member> enrolledMembers;
    private List<Review> reviews;

    public Lesson(int lessonId, ExerciseType exerciseType, String day, TimeSlot timeSlot, int weekNumber) {
        this.lessonId = lessonId;
        this.exerciseType = exerciseType;
        this.day = day;
        this.timeSlot = timeSlot;
        this.weekNumber = weekNumber;
        this.enrolledMembers = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public int getLessonId() { return lessonId; }
    public ExerciseType getExerciseType() { return exerciseType; }
    public String getDay() { return day; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public int getWeekNumber() { return weekNumber; }
    public List<Member> getEnrolledMembers() { return enrolledMembers; }
    public List<Review> getReviews() { return reviews; }

    public boolean isFull() { return enrolledMembers.size() >= MAX_CAPACITY; }
    public int getAvailableSpaces() { return MAX_CAPACITY - enrolledMembers.size(); }
    public double getPrice() { return exerciseType.getPrice(); }

    public boolean addMember(Member member) {
        if (isFull()) return false;
        enrolledMembers.add(member);
        return true;
    }

    public boolean removeMember(Member member) {
        return enrolledMembers.remove(member);
    }

    public void addReview(Review review) { reviews.add(review); }

    public double getAverageRating() {
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    public double getTotalIncome() {
        return enrolledMembers.size() * exerciseType.getPrice();
    }

    @Override
    public String toString() {
        return String.format("Week %d | %s | %s | %s | £%.2f | Spaces: %d/%d",
                weekNumber, day, timeSlot, exerciseType, exerciseType.getPrice(),
                enrolledMembers.size(), MAX_CAPACITY);
    }
}
