/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Devar
 */
package flc.data;

import flc.model.*;
import flc.service.BookingService;

public class DataLoader {

    public static void load(BookingService service) {
        // ─── 10 Members ───────────────────────────────────────────────────────
        Member[] members = {
            new Member(1, "Alice Johnson",   "alice@email.com"),
            new Member(2, "Bob Smith",       "bob@email.com"),
            new Member(3, "Carol White",     "carol@email.com"),
            new Member(4, "David Brown",     "david@email.com"),
            new Member(5, "Emma Davis",      "emma@email.com"),
            new Member(6, "Frank Wilson",    "frank@email.com"),
            new Member(7, "Grace Taylor",    "grace@email.com"),
            new Member(8, "Harry Moore",     "harry@email.com"),
            new Member(9, "Isabella Harris", "isabella@email.com"),
            new Member(10,"James Martin",    "james@email.com")
        };
        for (Member m : members) service.addMember(m);

        // ─── 8 Weekends × 6 lessons = 48 Lessons ─────────────────────────────
        // Layout: each weekend has Sat(Morning, Afternoon, Evening) + Sun(Morning, Afternoon, Evening)
        ExerciseType[] satEx = {ExerciseType.YOGA, ExerciseType.ZUMBA, ExerciseType.BOX_FIT,
                                ExerciseType.AQUACISE, ExerciseType.BODY_BLITZ, ExerciseType.PILATES,
                                ExerciseType.YOGA, ExerciseType.ZUMBA};
        ExerciseType[] sunEx = {ExerciseType.AQUACISE, ExerciseType.BODY_BLITZ, ExerciseType.PILATES,
                                ExerciseType.YOGA, ExerciseType.ZUMBA, ExerciseType.BOX_FIT,
                                ExerciseType.AQUACISE, ExerciseType.BODY_BLITZ};

        int lessonId = 1;
        Lesson[][] weekLessons = new Lesson[8][6];
        for (int w = 1; w <= 8; w++) {
            TimeSlot[] slots = TimeSlot.values();
            weekLessons[w-1][0] = new Lesson(lessonId++, satEx[w-1], "Saturday", slots[0], w);
            weekLessons[w-1][1] = new Lesson(lessonId++, satEx[w-1], "Saturday", slots[1], w);
            weekLessons[w-1][2] = new Lesson(lessonId++, satEx[w-1], "Saturday", slots[2], w);
            weekLessons[w-1][3] = new Lesson(lessonId++, sunEx[w-1], "Sunday",   slots[0], w);
            weekLessons[w-1][4] = new Lesson(lessonId++, sunEx[w-1], "Sunday",   slots[1], w);
            weekLessons[w-1][5] = new Lesson(lessonId++, sunEx[w-1], "Sunday",   slots[2], w);
            for (Lesson l : weekLessons[w-1]) service.addLesson(l);
        }

        // ─── Bookings (various members across weeks) ──────────────────────────
        // Week 1
        service.bookLesson(members[0], weekLessons[0][0]); // Alice Sat Morn Yoga
        service.bookLesson(members[1], weekLessons[0][0]); // Bob   Sat Morn Yoga
        service.bookLesson(members[2], weekLessons[0][1]); // Carol Sat Aft  Yoga
        service.bookLesson(members[3], weekLessons[0][3]); // David Sun Morn Aquacise
        service.bookLesson(members[4], weekLessons[0][3]); // Emma  Sun Morn Aquacise
        service.bookLesson(members[5], weekLessons[0][4]); // Frank Sun Aft  Aquacise

        // Week 2
        service.bookLesson(members[0], weekLessons[1][0]); // Alice Sat Morn Zumba
        service.bookLesson(members[6], weekLessons[1][0]); // Grace Sat Morn Zumba
        service.bookLesson(members[7], weekLessons[1][1]); // Harry Sat Aft  Zumba
        service.bookLesson(members[8], weekLessons[1][3]); // Isab  Sun Morn Body Blitz
        service.bookLesson(members[9], weekLessons[1][3]); // James Sun Morn Body Blitz
        service.bookLesson(members[1], weekLessons[1][4]); // Bob   Sun Aft  Body Blitz

        // Week 3
        service.bookLesson(members[2], weekLessons[2][0]); // Carol Sat Morn Box Fit
        service.bookLesson(members[3], weekLessons[2][0]); // David Sat Morn Box Fit
        service.bookLesson(members[4], weekLessons[2][1]); // Emma  Sat Aft  Box Fit
        service.bookLesson(members[5], weekLessons[2][3]); // Frank Sun Morn Pilates
        service.bookLesson(members[6], weekLessons[2][3]); // Grace Sun Morn Pilates

        // Week 4
        service.bookLesson(members[0], weekLessons[3][0]); // Alice Sat Morn Aquacise
        service.bookLesson(members[7], weekLessons[3][0]); // Harry Sat Morn Aquacise
        service.bookLesson(members[8], weekLessons[3][3]); // Isab  Sun Morn Yoga
        service.bookLesson(members[9], weekLessons[3][3]); // James Sun Morn Yoga
        service.bookLesson(members[0], weekLessons[3][4]); // Alice Sun Aft  Yoga
        service.bookLesson(members[1], weekLessons[3][3]); // Bob   Sun Morn Yoga

        // Week 5-8 extra bookings
        service.bookLesson(members[2], weekLessons[4][0]);
        service.bookLesson(members[3], weekLessons[4][1]);
        service.bookLesson(members[4], weekLessons[5][0]);
        service.bookLesson(members[5], weekLessons[5][3]);
        service.bookLesson(members[6], weekLessons[6][0]);
        service.bookLesson(members[7], weekLessons[6][3]);
        service.bookLesson(members[8], weekLessons[7][0]);
        service.bookLesson(members[9], weekLessons[7][3]);

        // ─── 20+ Reviews ──────────────────────────────────────────────────────
        service.addReview(members[0], weekLessons[0][0], 5, "Loved it! Very relaxing.");
        service.addReview(members[1], weekLessons[0][0], 4, "Good class, well paced.");
        service.addReview(members[2], weekLessons[0][1], 4, "Instructor was great.");
        service.addReview(members[3], weekLessons[0][3], 3, "Quite tough for a beginner.");
        service.addReview(members[4], weekLessons[0][3], 4, "Great workout in the pool.");
        service.addReview(members[5], weekLessons[0][4], 5, "Amazing afternoon session.");
        service.addReview(members[0], weekLessons[1][0], 5, "Zumba was so much fun!");
        service.addReview(members[6], weekLessons[1][0], 3, "Good but slightly crowded.");
        service.addReview(members[7], weekLessons[1][1], 4, "Enjoyed every minute.");
        service.addReview(members[8], weekLessons[1][3], 5, "Body Blitz is intense, loved it.");
        service.addReview(members[9], weekLessons[1][3], 4, "Really good energy in class.");
        service.addReview(members[1], weekLessons[1][4], 2, "Instructor was a bit rushed.");
        service.addReview(members[2], weekLessons[2][0], 5, "Box Fit — best class ever!");
        service.addReview(members[3], weekLessons[2][0], 4, "High intensity, very rewarding.");
        service.addReview(members[4], weekLessons[2][1], 3, "Decent session.");
        service.addReview(members[5], weekLessons[2][3], 5, "Pilates changed my posture.");
        service.addReview(members[6], weekLessons[2][3], 4, "Calming and effective.");
        service.addReview(members[0], weekLessons[3][0], 4, "Great start to the weekend.");
        service.addReview(members[7], weekLessons[3][0], 3, "Water was a bit cold.");
        service.addReview(members[8], weekLessons[3][3], 5, "Yoga is my favourite class.");
        service.addReview(members[9], weekLessons[3][3], 4, "Peaceful and well-structured.");
        service.addReview(members[0], weekLessons[3][4], 5, "Back to back yoga — wonderful!");
        service.addReview(members[1], weekLessons[3][3], 4, "Really enjoyed this.");
    }
}
