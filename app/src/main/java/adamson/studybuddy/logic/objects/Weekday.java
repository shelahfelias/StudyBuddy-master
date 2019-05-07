package adamson.studybuddy.logic.objects;

import java.util.Arrays;

/**
 * The Weekday Class represents an Object in the Weekday SQL table and is usually returned by methods from the DatabaseHelper Interface
 */
public class Weekday {
    /**
     * public String constant indicating that the {@link Weekday#getName()} is monday
     */
    public static final String MONDAY = "monday";
    /**
     * public String constant indicating that the {@link Weekday#getName()} is tuesday
     */
    public static final String TUESDAY = "tuesday";
    /**
     * public String constant indicating that the {@link Weekday#getName()} is wednesday
     */
    public static final String WEDNESDAY = "wednesday";
    /**
     * public String constant indicating that the {@link Weekday#getName()} is thursday
     */
    public static final String THURSDAY = "thursday";
    /**
     * public String constant indicating that the {@link Weekday#getName()} is friday
     */
    public static final String FRIDAY = "friday";
    /**
     * public String constant indicating that the {@link Weekday#getName()} is saturday
     */
    public static final String SATURDAY = "saturday";

    /**
     * numeric id of the Weekday (unique)
     */
    private final int id;

    /**
     * name of the Weekday as string e.g "Monday"
     */
    private final String name;

    /**
     * {@link Lesson}s on that day, sorted by the schoolHourNo
     */
    private final Lesson[] lessons;

    /**
     * standard c'tor for Weekday class
     *
     * @param id      unique numeric id of the Weekday
     * @param name    name of the Weekday as string e.g "Monday"
     * @param lessons {@link Lesson}s on this day, sorted by the schoolHourNo
     */
    public Weekday(int id, String name, Lesson[] lessons) {
        this.id = id;
        this.name = name;
        Arrays.sort(lessons);
        this.lessons = lessons;
    }

    /**
     * gets id of the Weekday
     *
     * @return unique numeric id of the Weekday
     */
    public int getId() {
        return id;
    }

    /**
     * gets name of the Weekday
     *
     * @return name of the Weekday as string e.g "Monday"
     */
    public String getName() {
        return name;
    }

    /**
     * gets {@link Lesson} on that day
     *
     * @return Lesson on that day, sorted by the schoolHourNo, as array
     */
    public Lesson[] getLessons() {
        return lessons;
    }

    /**
     * method to indicate if one Weekday matches another one by the values of their fields
     *
     * @param otherWeekday the other Weekday
     * @return true if all fields are the same in both Weekdays, else false
     */
    public boolean match(Weekday otherWeekday) {
        return this.id == otherWeekday.id && this.name.equals(otherWeekday.name) &&
                Lesson.match(this.lessons, otherWeekday.lessons);
    }

    /**
     * builds a string from Weekday's values
     *
     * @return Weekday as String
     */
    @Override
    public String toString() {
        return "---Weekday--- \n" +
                "Id: \t" + id + "\n" +
                "Name: \t" + name + "\n" +
                "Periods: \t" + Arrays.toString(lessons) + "\n" +
                "---######---";
    }


}
