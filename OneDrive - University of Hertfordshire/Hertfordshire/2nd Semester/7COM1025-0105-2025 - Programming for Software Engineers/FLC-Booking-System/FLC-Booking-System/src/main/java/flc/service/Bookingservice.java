/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Devar
 */
package flc.service;

import flc.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class BookingService {
    private List<Lesson> lessons;
    private List<Member> members;
    private List<Booking> bookings;
    private List<Review> reviews;

    public BookingService() {
        this.lessons = new ArrayList<>();
        this.members = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    // ─── Data Accessors ───────────────────────────────────────────────────────

    public List<Lesson> getAllLessons() { return lessons; }
    public List<Member> getAllMembers() { return members; }
    public List<Booking> getAllBookings() { return bookings; }
    public List<Review> getAllReviews() { return reviews; }

    public void addLesson(Lesson lesson) { lessons.add(lesson); }
    public void addMember(Member member) { members.add(member); }

    public Member getMemberById(int id) {
        return members.stream().filter(m -> m.getMemberId() == id).findFirst().orElse(null);
    }

    public Lesson getLessonById(int id) {
        return lessons.stream().filter(l -> l.getLessonId() == id).findFirst().orElse(null);
    }

    // ─── Timetable Queries ────────────────────────────────────────────────────

    public List<Lesson> getLessonsByDay(String day) {
        return lessons.stream()
                .filter(l -> l.getDay().equalsIgnoreCase(day))
                .sorted(Comparator.comparingInt(Lesson::getWeekNumber)
                        .thenComparing(l -> l.getTimeSlot().ordinal()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByExercise(String exerciseName) {
        return lessons.stream()
                .filter(l -> l.getExerciseType().getDisplayName().equalsIgnoreCase(exerciseName))
                .sorted(Comparator.comparingInt(Lesson::getWeekNumber)
                        .thenComparing(l -> l.getTimeSlot().ordinal()))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByWeek(int weekNumber) {
        return lessons.stream()
                .filter(l -> l.getWeekNumber() == weekNumber)
                .sorted(Comparator.comparing(Lesson::getDay)
                        .thenComparing(l -> l.getTimeSlot().ordinal()))
                .collect(Collectors.toList());
    }

    // ─── Booking Operations ───────────────────────────────────────────────────

    public String bookLesson(Member member, Lesson lesson) {
        if (lesson.isFull()) return "ERROR: Lesson is full.";
        if (member.hasConflict(lesson)) return "ERROR: Time conflict with existing booking.";
        Booking booking = new Booking(member, lesson);
        lesson.addMember(member);
        member.addBooking(booking);
        bookings.add(booking);
        return "SUCCESS: Booked " + lesson.getExerciseType() + " (" + lesson.getDay() + ", " + lesson.getTimeSlot() + ") for " + member.getName();
    }

    public String changeBooking(Booking booking, Lesson newLesson) {
        if (newLesson.isFull()) return "ERROR: New lesson is full.";
        Member member = booking.getMember();
        // Temporarily remove old to avoid false conflict
        Lesson oldLesson = booking.getLesson();
        oldLesson.removeMember(member);
        member.removeBooking(booking);
        if (member.hasConflict(newLesson)) {
            // Restore old booking
            oldLesson.addMember(member);
            member.addBooking(booking);
            return "ERROR: Time conflict with existing booking.";
        }
        booking.setLesson(newLesson);
        newLesson.addMember(member);
        member.addBooking(booking);
        return "SUCCESS: Booking changed to " + newLesson.getExerciseType() + " (" + newLesson.getDay() + ", " + newLesson.getTimeSlot() + ")";
    }

    public String cancelBooking(Booking booking) {
        Lesson lesson = booking.getLesson();
        Member member = booking.getMember();
        lesson.removeMember(member);
        member.removeBooking(booking);
        bookings.remove(booking);
        return "SUCCESS: Booking cancelled for " + member.getName() + " - " + lesson.getExerciseType();
    }

    public List<Booking> getBookingsByMember(Member member) {
        return bookings.stream()
                .filter(b -> b.getMember().equals(member))
                .collect(Collectors.toList());
    }

    // ─── Review Operations ─────────────────────────────────────────────────────

    public String addReview(Member member, Lesson lesson, int rating, String comment) {
        if (rating < 1 || rating > 5) return "ERROR: Rating must be between 1 and 5.";
        boolean attended = lesson.getEnrolledMembers().contains(member);
        if (!attended) return "ERROR: Member did not attend this lesson.";
        Review review = new Review(member, lesson, rating, comment);
        lesson.addReview(review);
        reviews.add(review);
        return "SUCCESS: Review submitted.";
    }

    // ─── Reports ──────────────────────────────────────────────────────────────

    public String generateAttendanceReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("═".repeat(70)).append("\n");
        sb.append("   FURZEFIELD LEISURE CENTRE — ATTENDANCE & RATINGS REPORT\n");
        sb.append("═".repeat(70)).append("\n\n");
        Map<Integer, List<Lesson>> byWeek = lessons.stream()
                .collect(Collectors.groupingBy(Lesson::getWeekNumber));
        for (int week : byWeek.keySet().stream().sorted().toList()) {
            sb.append("  WEEK ").append(week).append("\n");
            sb.append("  ").append("─".repeat(66)).append("\n");
            List<Lesson> weekLessons = byWeek.get(week);
            weekLessons.sort(Comparator.comparing(Lesson::getDay).thenComparing(l -> l.getTimeSlot().ordinal()));
            for (Lesson l : weekLessons) {
                String avg = l.getReviews().isEmpty() ? "No reviews" : String.format("%.1f/5", l.getAverageRating());
                sb.append(String.format("  %-10s | %-12s | %-10s | Members: %d | Avg Rating: %s%n",
                        l.getDay(), l.getTimeSlot().getLabel(), l.getExerciseType(),
                        l.getEnrolledMembers().size(), avg));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String generateIncomeReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("═".repeat(60)).append("\n");
        sb.append("   FURZEFIELD LEISURE CENTRE — INCOME REPORT\n");
        sb.append("═".repeat(60)).append("\n\n");
        Map<ExerciseType, Double> incomeMap = new LinkedHashMap<>();
        for (ExerciseType et : ExerciseType.values()) incomeMap.put(et, 0.0);
        for (Lesson l : lessons) {
            incomeMap.merge(l.getExerciseType(), l.getTotalIncome(), Double::sum);
        }
        ExerciseType topExercise = null;
        double topIncome = -1;
        for (Map.Entry<ExerciseType, Double> entry : incomeMap.entrySet()) {
            if (entry.getValue() > 0) {
                sb.append(String.format("  %-12s  £%.2f%n", entry.getKey(), entry.getValue()));
                if (entry.getValue() > topIncome) {
                    topIncome = entry.getValue();
                    topExercise = entry.getKey();
                }
            }
        }
        sb.append("\n").append("─".repeat(60)).append("\n");
        if (topExercise != null) {
            sb.append(String.format("  TOP EARNER: %s — £%.2f%n", topExercise, topIncome));
        }
        sb.append("═".repeat(60)).append("\n");
        return sb.toString();
    }
}
