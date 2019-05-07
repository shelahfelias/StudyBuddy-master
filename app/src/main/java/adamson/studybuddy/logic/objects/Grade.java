package adamson.studybuddy.logic.objects;


/**
 * The Grade Class represents an Object in the Grade SQL table and is usually returned by methods from the DatabaseHelper Interface
 */
public class Grade {
    /**
     * numeric id of the Grade (unique)
     */
    private final int id;

    /**
     * the {@link Subject} the grade was given in
     */
    private final Subject subject;

    /**
     * the name / type of the grade, e.g. "Presentation"
     */
    private final String name;

    /**
     * the grade itself as String, e.g "1" or "15" or "A" or what system you use
     */
    private final String grade;


    /**
     * standard c'tor
     *
     * @param id      unique numeric id of the Grade
     * @param subject the {@link Subject} the grade was given in
     * @param name    the name / type of the grade, e.g. "Presentation"
     * @param grade   the grade itself as String, e.g "1" or "15" or "A" or what system you use
     */
    public Grade(int id, Subject subject, String name, String grade) {
        this.id = id;
        this.subject = subject;
        this.name = name;
        this.grade = grade;
    }

    /**
     * gets id of the Grade
     *
     * @return unique numeric id of the Grade
     */
    public int getId() {
        return id;
    }

    /**
     * gets the {@link Subject} the grade was given in
     *
     * @return subject the grade was given in
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * gets the name of the grade
     *
     * @return the name / type of the grade, e.g. "Presentation"
     */
    public String getName() {
        return name;
    }

    /**
     * gets the grade itself as String
     *
     * @return the grade itself as String, e.g "1" or "15" or "A" or what system you use
     */
    public String getGrade() {
        return grade;
    }

    /**
     * builds a string from Grade's values
     *
     * @return Grade as String
     */
    @Override
    public String toString() {
        return "---Grade--- \n" +
                "Id: \t" + id + "\n" +
                subject.toString() + "\n" +
                "Name: \t" + name + "\n" +
                "Grade: \t" + grade + "\n" +
                "---####---";
    }
}
