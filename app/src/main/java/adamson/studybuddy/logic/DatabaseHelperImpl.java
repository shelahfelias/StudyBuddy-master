package adamson.studybuddy.logic;


import android.app.Activity;
import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import adamson.studybuddy.gui.DatabaseCascadeDeleteConfirmDialog;
import adamson.studybuddy.logic.objects.Exam;
import adamson.studybuddy.logic.objects.Grade;
import adamson.studybuddy.logic.objects.Homework;
import adamson.studybuddy.logic.objects.Lesson;
import adamson.studybuddy.logic.objects.Period;
import adamson.studybuddy.logic.objects.Schedule;
import adamson.studybuddy.logic.objects.Subject;
import adamson.studybuddy.logic.objects.Teacher;
import adamson.studybuddy.logic.objects.Weekday;

/**
 * Implementation of DatabaseHelper interface to create and interact with the schoolPlanner SQLite Database.
 */
public class DatabaseHelperImpl extends SQLiteOpenHelper implements DatabaseHelper {
    private final Context context;
    private Activity activity = null;

    /**
     * standard c'tor for DatabaseHelperImpl
     *
     * @param context context of the application
     */
    public DatabaseHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * c'tor for DatabaseHelperImpl using the current {@link Activity}.
     * <br> </br>
     * Note: if this c'tor is used all delete methods will finish the {@link Activity} after successful delete
     *
     * @param activity activity which is using the Database
     */
    public DatabaseHelperImpl(Activity activity) {
        super(activity, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = activity;
        this.activity = activity;
    }

    /**
     * method inherited from SQLiteOpenHelper to create and setup the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
    }

    /**
     * Create and/or open a database that will be used for reading and writing with enabled foreign key support.
     * The first time this is called, the database will be opened and
     * {@link #onCreate}, {@link #onUpgrade} and/or {@link #onOpen} will be
     * called.
     * <p>
     * <p>Once opened successfully, the database is cached, so you can
     * call this method every time you need to write to the database.
     * (Make sure to call {@link #close} when you no longer need the database.)
     * Errors such as bad permissions or a full disk may cause this method
     * to fail, but future attempts may succeed if the problem is fixed.</p>
     * <p>
     * <p class="caution">Database upgrade may take a long time, you
     * should not call this method from the application main thread, including
     * from {@link ContentProvider#onCreate ContentProvider.onCreate()}.
     *
     * @return a read/write database object valid until {@link #close} is called
     * @throws SQLiteException if the database cannot be opened for writing
     */
    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase database = super.getWritableDatabase();
        database.setForeignKeyConstraintsEnabled(true);

        return database;
    }

    /**
     * method inherited from SQLiteOpenHelper called to reset the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     * @param i              old db version number
     * @param i1             new db version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropAllTables(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }


    //region handling Methods
    //region getObjectAtId

    /**
     * gets the {@link Subject} at a specific id from database.
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Subject}, or null if not existing
     */
    @Override
    public Subject getSubjectAtId(int id) {
        try {
            return getSubjectAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Subject", context);
            return null;
        }
    }

    /**
     * gets the {@link Teacher} at a specific id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Teacher}, or null if not existing
     */
    @Override
    public Teacher getTeacherAtId(int id) {
        try {
            return getTeacherAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Teacher", context);
            return null;
        }
    }

    /**
     * gets the {@link Homework} at a specific id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Homework}, or null if not existing
     */
    @Override
    public Homework getHomeworkAtId(int id) {
        try {
            return getHomeworkAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Homework", context);
            return null;
        }
    }

    /**
     * gets the {@link Exam} at a specific id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Exam}, or null if not existing
     */
    @Override
    public Exam getExamAtId(int id) {
        try {
            return getExamAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Exam", context);
            return null;
        }
    }

    /**
     * gets the {@link Grade} at a specific id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Grade}, or null if not existing
     */
    @Override
    public Grade getGradeAtId(int id) {
        try {
            return getGradeAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Grade", context);
            return null;
        }
    }

    /**
     * gets the {@link Period} at a specific id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Period}, or null if not existing
     */
    @Override
    public Period getPeriodAtId(int id) {
        try {
            return getPeriodAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Period", context);
            return null;
        }
    }

    /**
     * gets the {@link Lesson} at a specific id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Lesson}, or null if not existing
     */
    @Override
    public Lesson getLessonAtId(int id) {
        try {
            return getLessonAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Lesson", context);
            return null;
        }
    }

    /**
     * gets the {@link Weekday} at a specific id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Weekday}, or null if not existing
     */
    @Override
    public Weekday getWeekdayAtId(int id) {
        try {
            return getWeekdayAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Weekday", context);
            return null;
        }
    }

    /**
     * gets the {@link Schedule} at a specific id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForGettingANotExistingObject(String, Context)} to handle exceptions
     *
     * @param id id in database
     * @return row with given id from db as {@link Schedule}, or null if not existing
     */
    @Override
    public Schedule getScheduleAtId(int id) {
        try {
            return getScheduleAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForGettingANotExistingObject("Schedule", context);
            return null;
        }
    }
    //endregion

    //region updateObjectAtId

    /**
     * updates {@link Subject} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newSubject the new {@link Subject}
     */
    @Override
    public void updateSubjectAtId(Subject newSubject) {
        try {
            updateSubjectAtIdOrThrow(newSubject);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Subject", context);
        }
    }

    /**
     * updates {@link Teacher} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newTeacher the new {@link Teacher}
     */
    @Override
    public void updateTeacherAtId(Teacher newTeacher) {
        try {
            updateTeacherAtIdOrThrow(newTeacher);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Teacher", context);
        }
    }

    /**
     * updates {@link Homework} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newHomework the new {@link Homework}
     */
    @Override
    public void updateHomeworkAtId(Homework newHomework) {
        try {
            updateHomeworkAtIdOrThrow(newHomework);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Homework", context);
        }
    }

    /**
     * updates {@link Exam} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newExam the new {@link Exam}
     */
    @Override
    public void updateExamAtId(Exam newExam) {
        try {
            updateExamAtIdOrThrow(newExam);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Exam", context);
        }
    }

    /**
     * updates {@link Grade} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newGrade the new {@link Grade}
     */
    @Override
    public void updateGradeAtId(Grade newGrade) {
        try {
            updateGradeAtIdOrThrow(newGrade);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Grade", context);
        }
    }

    /**
     * updates {@link Period} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newPeriod the new {@link Period}
     */
    @Override
    public void updatePeriodAtId(Period newPeriod) {
        try {
            updatePeriodAtIdOrThrow(newPeriod);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Period", context);
        }
    }

    /**
     * updates {@link Lesson} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newLesson the new {@link Lesson}
     */
    @Override
    public void updateLessonAtId(Lesson newLesson) {
        try {
            updateLessonAtIdOrThrow(newLesson);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Lesson", context);
        }
    }

    /**
     * updates {@link Weekday} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newWeekday the new {@link Weekday}
     */
    @Override
    public void updateWeekdayAtId(Weekday newWeekday) {
        try {
            updateWeekdayAtIdOrThrow(newWeekday);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Weekday", context);
        }
    }

    /**
     * updates {@link Schedule} at the given id in database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForUpdatingAnNotExistingObject(String, Context)} to handle exceptions
     *
     * @param newSchedule the new {@link Schedule}
     */
    @Override
    public void updateScheduleAtId(Schedule newSchedule) {
        try {
            updateScheduleAtIdOrThrow(newSchedule);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject("Schedule", context);
        }
    }
    //endregion

    //region insertIntoDB

    /**
     * inserts {@link Subject} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param subject {@link Subject} to be inserted
     * @return the id in the database the {@link Subject} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Subject subject) {
        try {
            return insertIntoDBOrThrow(subject);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(subject, context);
            return -1;
        }
    }

    /**
     * inserts {@link Teacher} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param teacher {@link Teacher} to be inserted
     * @return the id in the database the {@link Teacher} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Teacher teacher) {
        try {
            return insertIntoDBOrThrow(teacher);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(teacher, context);
            return -1;
        }
    }

    /**
     * inserts {@link Homework} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param homework {@link Homework} to be inserted
     * @return the id in the database the {@link Homework} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Homework homework) {
        try {
            return insertIntoDBOrThrow(homework);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(homework, context);
            return -1;
        }
    }

    /**
     * inserts {@link Exam} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param exam {@link Exam} to be inserted
     * @return the id in the database the {@link Exam} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Exam exam) {
        try {
            return insertIntoDBOrThrow(exam);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(exam, context);
            return -1;
        }
    }

    /**
     * inserts {@link Grade} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param grade {@link Grade} to be inserted
     * @return the id in the database the {@link Grade} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Grade grade) {
        try {
            return insertIntoDBOrThrow(grade);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(grade, context);
            return -1;
        }
    }

    /**
     * inserts {@link Period} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param period {@link Period} to be inserted
     * @return the id in the database the {@link Period} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Period period) {
        try {
            return insertIntoDBOrThrow(period);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(period, context);
            return -1;
        }
    }

    /**
     * inserts {@link Lesson} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param lesson {@link Lesson} to be inserted
     * @return the id in the database the {@link Lesson} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Lesson lesson) {
        try {
            return insertIntoDBOrThrow(lesson);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(lesson, context);
            return -1;
        }
    }

    /**
     * inserts {@link Weekday} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param weekday {@link Weekday} to be inserted
     * @return the id in the database the {@link Weekday} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Weekday weekday) {
        try {
            return insertIntoDBOrThrow(weekday);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(weekday, context);
            return -1;
        }
    }

    /**
     * inserts {@link Schedule} into database, use an ID <= 0 to insert at next unoccupied ID
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForAddingAAlreadyExistingObject(Object, Context)} to handle exceptions
     *
     * @param schedule {@link Schedule} to be inserted
     * @return the id in the database the {@link Schedule} was inserted or -1 if action could not be performed
     */
    @Override
    public int insertIntoDB(Schedule schedule) {
        try {
            return insertIntoDBOrThrow(schedule);
        } catch (IllegalAccessException e) {
            ExceptionHandler.handleDatabaseExceptionForAddingAAlreadyExistingObject(schedule, context);
            return -1;
        }
    }
    //endregion

    //region deleteObjectAtId

    /**
     * deletes the {@link Subject} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Subject} to delete has
     */
    @Override
    public void deleteSubjectAtId(int id) {
        try {
            deleteSubjectAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    /**
     * deletes the {@link Teacher} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Teacher} to delete has
     */
    @Override
    public void deleteTeacherAtId(int id) {
        try {
            deleteTeacherAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    /**
     * deletes the {@link Homework} at the given id from database
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Homework} to delete has
     */
    @Override
    public void deleteHomeworkAtId(int id) {
        try {
            deleteHomeworkAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    /**
     * deletes the {@link Exam} at the given id from database
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Exam} to delete has
     */
    @Override
    public void deleteExamAtId(int id) {
        try {
            deleteExamAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    /**
     * deletes the {@link Grade} at the given id from database
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Grade} to delete has
     */
    @Override
    public void deleteGradeAtId(int id) {
        try {
            deleteGradeAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    /**
     * deletes the {@link Period} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Period} to delete has
     */
    @Override
    public void deletePeriodAtId(int id) {
        try {
            deletePeriodAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    /**
     * deletes the {@link Lesson} at the given id from database
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Lesson} to delete has
     */
    @Override
    public void deleteLessonAtId(int id) {
        try {
            deleteLessonAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    /**
     * deletes the {@link Weekday} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Weekday} to delete has
     */
    @Override
    public void deleteWeekdayAtId(int id) {
        try {
            deleteWeekdayAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }

    /**
     * deletes the {@link Schedule} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     * <br> </br>
     * Note: Method naturally uses {@link ExceptionHandler#handleDatabaseExceptionForDeletingAnNotExistingObject(int, Context)} to handle exceptions
     *
     * @param id the id the {@link Schedule} to delete has
     */
    @Override
    public void deleteScheduleAtId(int id) {
        try {
            deleteScheduleAtIdOrThrow(id);
        } catch (NoSuchFieldException e) {
            ExceptionHandler.handleDatabaseExceptionForDeletingAnNotExistingObject(id, context);
        }
    }
    //endregion
    //endregion

    //region orThrow Methods
    //region getObjectAtId

    /**
     * gets the {@link Subject} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Subject}
     * @throws NoSuchFieldException if there is no {@link Subject} at the given id in the Database
     */
    @Override
    public Subject getSubjectAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_SUBJECT, SUBJECT_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Subject(
                    cursor.getInt(0),
                    getTeacherAtIdOrThrow(cursor.getInt(1)),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * gets the {@link Teacher} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Teacher}
     * @throws NoSuchFieldException if there is no {@link Teacher} at the given id in the Database
     */
    @Override
    public Teacher getTeacherAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_TEACHER, TEACHER_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Teacher(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3).charAt(0)
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * gets the {@link Homework} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Homework}
     * @throws NoSuchFieldException if there is no {@link Homework} at the given id in the Database
     */
    @Override
    public Homework getHomeworkAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_HOMEWORK, HOMEWORK_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            if (cursor.getInt(4) == 0) {
                return new Homework(
                        cursor.getInt(0),
                        getSubjectAtIdOrThrow(cursor.getInt(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        false
                );
            } else {
                return new Homework(
                        cursor.getInt(0),
                        getSubjectAtIdOrThrow(cursor.getInt(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        true
                );
            }

        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * gets the {@link Exam} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Exam}
     * @throws NoSuchFieldException if there is no {@link Exam} at the given id in the Database
     */
    @Override
    public Exam getExamAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_EXAM, EXAM_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Exam(
                    cursor.getInt(0),
                    getSubjectAtIdOrThrow(cursor.getInt(1)),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * gets the {@link Grade} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Grade}
     * @throws NoSuchFieldException if there is no {@link Grade} at the given id in the Database
     */
    @Override
    public Grade getGradeAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_GRADE, GRADE_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Grade(
                    cursor.getInt(0),
                    getSubjectAtIdOrThrow(cursor.getInt(1)),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * gets the {@link Period} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Period}
     * @throws NoSuchFieldException if there is no {@link Period} at the given id in the Database
     */
    @Override
    public Period getPeriodAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_PERIOD, PERIOD_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Period(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * gets the {@link Lesson} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Lesson}
     * @throws NoSuchFieldException if there is no {@link Lesson} at the given id in the Database
     */
    @Override
    public Lesson getLessonAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_LESSON, LESSON_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Lesson(
                    cursor.getInt(0),
                    getSubjectAtIdOrThrow(cursor.getInt(1)),
                    getPeriodAtIdOrThrow(cursor.getInt(2))
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * gets the {@link Weekday} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Weekday}
     * @throws NoSuchFieldException if there is no {@link Weekday} at the given id in the Database
     */
    @Override
    public Weekday getWeekdayAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_WEEKDAY, WEEKDAY_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Weekday(
                    cursor.getInt(0),
                    cursor.getString(2),
                    getLessonsAtWeekday(id)
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * gets the {@link Schedule} at a specific id from database
     *
     * @param id id in database
     * @return row with given id from db as {@link Schedule}
     * @throws NoSuchFieldException if there is no {@link Schedule} at the given id in the Database
     */
    @Override
    public Schedule getScheduleAtIdOrThrow(int id) throws NoSuchFieldException {

        String query = buildQueryToGetRowAtId(TABLE_SCHEDULE, SCHEDULE_COLUMN_ID, id);

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return new Schedule(
                    cursor.getInt(0),
                    cursor.getString(1),
                    getWeekdaysAtSchedule(id)
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }
    //endregion

    /**
     * gets the {@link Lesson} at a specific {@link Weekday} and {@link Period} from database
     *
     * @param day    the {@link Weekday}
     * @param period the {@link Period}
     * @return the row with the given specs
     * @throws NoSuchFieldException if there is no such {@link Lesson} in the Database
     */
    public Lesson getLessonOrThrowAtDate(Weekday day, Period period) throws NoSuchFieldException {

        String query = "SELECT " + LESSON_COLUMN_ID + " FROM " + TABLE_LESSON + " WHERE " + LESSON_COLUMN_WEEKDAY_ID + " = " + day.getId() +
                " AND " + LESSON_COLUMN_PERIOD_ID + " = " + period.getId();

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return getLessonAtIdOrThrow(cursor.getInt(0));
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    //region updateObjectAtId

    /**
     * updates {@link Subject} at the given id in database
     *
     * @param newSubject the new {@link Subject}
     * @throws NoSuchFieldException if there is no {@link Subject} at the given id in the Database
     */
    @Override
    public void updateSubjectAtIdOrThrow(Subject newSubject) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_SUBJECT + " SET " +
                    SUBJECT_COLUMN_TEACHER_ID + " = " + newSubject.getTeacher().getId() + ", " +
                    SUBJECT_COLUMN_NAME + " = \"" + newSubject.getName() + "\", " +
                    SUBJECT_COLUMN_ROOM + " = \"" + newSubject.getRoom() + "\", " +
                    SUBJECT_COLUMN_COLOR + " = \"" + newSubject.getColor() + "\" " +
                    "WHERE " + SUBJECT_COLUMN_ID + " = " + newSubject.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * updates {@link Teacher} at the given id in database
     *
     * @param newTeacher the new {@link Teacher}
     * @throws NoSuchFieldException if there is no {@link Teacher} at the given id in the Database
     */
    @Override
    public void updateTeacherAtIdOrThrow(Teacher newTeacher) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_TEACHER + " SET " +
                    TEACHER_COLUMN_NAME + " = \"" + newTeacher.getName() + "\", " +
                    TEACHER_COLUMN_ABBREVIATION + " = \"" + newTeacher.getAbbreviation() + "\", " +
                    TEACHER_COLUMN_GENDER + " = \"" + newTeacher.getGender() + "\" " +
                    "WHERE " + TEACHER_COLUMN_ID + " = " + newTeacher.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * updates {@link Homework} at the given id in database
     *
     * @param newHomework the new {@link Homework}
     * @throws NoSuchFieldException if there is no {@link Homework} at the given id in the Database
     */
    @Override
    public void updateHomeworkAtIdOrThrow(Homework newHomework) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_HOMEWORK + " SET " +
                    HOMEWORK_COLUMN_SUBJECT_ID + " = " + newHomework.getSubject().getId() + ", " +
                    HOMEWORK_COLUMN_DESCRIPTION + " = \"" + newHomework.getDescription() + "\", " +
                    HOMEWORK_COLUMN_DEADLINE + " = \"" + newHomework.getDeadlineAsDatabaseString() + "\", " +
                    HOMEWORK_COLUMN_DONE + " = " + newHomework.getDone() + " " +
                    "WHERE " + HOMEWORK_COLUMN_ID + " = " + newHomework.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * updates {@link Exam} at the given id in database
     *
     * @param newExam the new {@link Exam}
     * @throws NoSuchFieldException if there is no {@link Exam} at the given id in the Database
     */
    @Override
    public void updateExamAtIdOrThrow(Exam newExam) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_EXAM + " SET " +
                    EXAM_COLUMN_SUBJECT_ID + " = " + newExam.getSubject().getId() + ", " +
                    EXAM_COLUMN_DESCRIPTION + " = \"" + newExam.getDescription() + "\", " +
                    EXAM_COLUMN_DEADLINE + " = \"" + newExam.getDeadlineAsDatabaseString() + "\" " +
                    "WHERE " + EXAM_COLUMN_ID + " = " + newExam.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * updates {@link Grade} at the given id in database
     *
     * @param newGrade the new {@link Grade}
     * @throws NoSuchFieldException if there is no {@link Grade} at the given id in the Database
     */
    @Override
    public void updateGradeAtIdOrThrow(Grade newGrade) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_GRADE + " SET " +
                    GRADE_COLUMN_SUBJECT_ID + " = " + newGrade.getSubject().getId() + ", " +
                    GRADE_COLUMN_NAME + " = \"" + newGrade.getName() + "\", " +
                    GRADE_COLUMN_GRADE + " = \"" + newGrade.getGrade() + "\" " +
                    "WHERE " + GRADE_COLUMN_ID + " = " + newGrade.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * updates {@link Period} at the given id in database
     *
     * @param newPeriod the new {@link Period}
     * @throws NoSuchFieldException if there is no {@link Period} at the given id in the Database
     */
    @Override
    public void updatePeriodAtIdOrThrow(Period newPeriod) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_PERIOD + " SET " +
                    PERIOD_COLUMN_SCHOOL_HOUR_NO + " = " + newPeriod.getSchoolHourNo() + ", " +
                    PERIOD_COLUMN_STARTTIME + " = \"" + newPeriod.getStartTimeAsString() + "\", " +
                    PERIOD_COLUMN_ENDTIME + " = \"" + newPeriod.getEndTimeAsString() + "\" " +
                    "WHERE " + PERIOD_COLUMN_ID + " = " + newPeriod.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * updates {@link Lesson} at the given id in database
     *
     * @param newLesson the new {@link Lesson}
     * @throws NoSuchFieldException if there is no {@link Lesson} at the given id in the Database
     */
    @Override
    public void updateLessonAtIdOrThrow(Lesson newLesson) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_LESSON + " SET " +
                    LESSON_COLUMN_SUBJECT_ID + " = " + newLesson.getSubject().getId() + ", " +
                    LESSON_COLUMN_PERIOD_ID + " = " + newLesson.getPeriod().getId() + " " +
                    "WHERE " + LESSON_COLUMN_ID + " = " + newLesson.getId()
            );
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * updates {@link Weekday} at the given id in database
     *
     * @param newWeekday the new {@link Weekday}
     * @throws NoSuchFieldException if there is no {@link Weekday} at the given id in the Database
     */
    @Override
    public void updateWeekdayAtIdOrThrow(Weekday newWeekday) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_WEEKDAY + " SET " +
                    WEEKDAY_COLUMN_NAME + " = \"" + newWeekday.getName() + "\" " +
                    "WHERE " + WEEKDAY_COLUMN_ID + " = " + newWeekday.getId()
            );

            for (Lesson lesson : newWeekday.getLessons()) {
                updateLessonWeekdayIdAtId(lesson.getId(), newWeekday.getId());
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * updates {@link Schedule} at the given id in database
     *
     * @param newSchedule the new {@link Schedule}
     * @throws NoSuchFieldException if there is no {@link Schedule} at the given id in the Database
     */
    @Override
    public void updateScheduleAtIdOrThrow(Schedule newSchedule) throws NoSuchFieldException {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_SCHEDULE + " SET " +
                    SCHEDULE_COLUMN_NAME + " = \"" + newSchedule.getName() + "\" " +
                    "WHERE " + SCHEDULE_COLUMN_ID + " = " + newSchedule.getId()
            );

            for (Weekday weekday : newSchedule.getDays()) {
                updateWeekdayScheduleIdAtId(weekday.getId(), newSchedule.getId());
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }
    //endregion

    //region insertIntoDB

    /**
     * inserts {@link Subject} into database at a given id, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param subject {@link Subject} to be inserted
     * @return the id in the database the {@link Subject} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Subject subject) throws IllegalAccessException {
        try {
            getTeacherAtIdOrThrow(subject.getTeacher().getId());
        } catch (NoSuchFieldException e) {
            insertIntoDBOrThrow(subject.getTeacher());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        int subjectId;
        if (subject.getId() <= 0) {
            subjectId = getNewID(TABLE_SUBJECT, SUBJECT_COLUMN_ID);
        } else {
            subjectId = subject.getId();
        }

        String query = "INSERT INTO " + TABLE_SUBJECT + " VALUES ( " + subjectId + ", " + subject.getTeacher().getId() + ", \"" + subject.getName() + "\", \"" + subject.getRoom() + "\", \"" + subject.getColor() + "\" )";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return subjectId;
    }

    /**
     * inserts {@link Teacher} into database, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param teacher {@link Teacher} to be inserted
     * @return the id in the database the {@link Teacher} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Teacher teacher) throws IllegalAccessException {
        SQLiteDatabase db = this.getWritableDatabase();

        int teacherId;
        if (teacher.getId() <= 0) {
            teacherId = getNewID(TABLE_TEACHER, TEACHER_COLUMN_ID);
        } else {
            teacherId = teacher.getId();
        }

        String query = "INSERT INTO " + TABLE_TEACHER + " VALUES ( " + teacherId + ", \"" + teacher.getName() + "\", \"" + teacher.getAbbreviation() + "\", \"" + teacher.getGender() + "\")";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return teacherId;
    }

    /**
     * inserts {@link Homework} into database, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param homework {@link Homework} to be inserted
     * @return the id in the database the {@link Homework} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Homework homework) throws IllegalAccessException {
        try {
            getSubjectAtIdOrThrow(homework.getSubject().getId());
        } catch (NoSuchFieldException e) {
            insertIntoDBOrThrow(homework.getSubject());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        int homeworkId;
        if (homework.getId() <= 0) {
            homeworkId = getNewID(TABLE_HOMEWORK, HOMEWORK_COLUMN_ID);
        } else {
            homeworkId = homework.getId();
        }

        String query = "INSERT INTO " + TABLE_HOMEWORK + " VALUES ( " + homeworkId + ", " + homework.getSubject().getId() + ", \"" + homework.getDescription() + "\", \"" + homework.getDeadlineAsDatabaseString() + "\", " + homework.getDone() + ")";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }

        return homeworkId;
    }

    /**
     * inserts {@link Exam} into database, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param exam {@link Exam} to be inserted
     * @return the id in the database the {@link Exam} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Exam exam) throws IllegalAccessException {
        try {
            getSubjectAtIdOrThrow(exam.getSubject().getId());
        } catch (NoSuchFieldException e) {
            insertIntoDBOrThrow(exam.getSubject());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        int examId;
        if (exam.getId() <= 0) {
            examId = getNewID(TABLE_EXAM, EXAM_COLUMN_ID);
        } else {
            examId = exam.getId();
        }

        String query = "INSERT INTO " + TABLE_EXAM + " VALUES ( " + examId + ", " + exam.getSubject().getId() + ", \"" + exam.getDescription() + "\", \"" + exam.getDeadlineAsDatabaseString() + "\")";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return examId;
    }

    /**
     * inserts {@link Grade} into database, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param grade {@link Grade} to be inserted
     * @return the id in the database the {@link Grade} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Grade grade) throws IllegalAccessException {
        try {
            getSubjectAtIdOrThrow(grade.getSubject().getId());
        } catch (NoSuchFieldException e) {
            insertIntoDBOrThrow(grade.getSubject());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        int gradeId;
        if (grade.getId() <= 0) {
            gradeId = getNewID(TABLE_GRADE, GRADE_COLUMN_ID);
        } else {
            gradeId = grade.getId();
        }

        String query = "INSERT INTO " + TABLE_GRADE + " VALUES ( " + gradeId + ", " + grade.getSubject().getId() + ", \"" + grade.getName() + "\", \"" + grade.getGrade() + "\")";


        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return gradeId;
    }

    /**
     * inserts {@link Period} into database, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param period {@link Period} to be inserted
     * @return the id in the database the {@link Period} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Period period) throws IllegalAccessException {
        SQLiteDatabase db = this.getWritableDatabase();

        int periodId;
        if (period.getId() <= 0) {
            periodId = getNewID(TABLE_PERIOD, PERIOD_COLUMN_ID);
        } else {
            periodId = period.getId();
        }

        String query = "INSERT INTO " + TABLE_PERIOD + " VALUES ( " + periodId + ", " + period.getSchoolHourNo() + ", \"" + period.getStartTimeAsString() + "\", \"" + period.getEndTimeAsString() + "\")";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return periodId;
    }

    /**
     * inserts {@link Lesson} into database, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param lesson {@link Lesson} to be inserted
     * @return the id in the database the {@link Lesson} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Lesson lesson) throws IllegalAccessException {
        try {
            getSubjectAtIdOrThrow(lesson.getSubject().getId());
        } catch (NoSuchFieldException e) {
            insertIntoDBOrThrow(lesson.getSubject());
        }

        try {
            getPeriodAtIdOrThrow(lesson.getPeriod().getId());
        } catch (NoSuchFieldException e) {
            insertIntoDBOrThrow(lesson.getPeriod());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        int lessonId;
        if (lesson.getId() <= 0) {
            lessonId = getNewID(TABLE_LESSON, LESSON_COLUMN_ID);
        } else {
            lessonId = lesson.getId();
        }

        String query = "INSERT INTO " + TABLE_LESSON + " VALUES ( " + lessonId + ", " + lesson.getSubject().getId() + ", " + lesson.getPeriod().getId() + ", NULL)";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return lessonId;
    }

    /**
     * inserts {@link Weekday} into database, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param weekday {@link Weekday} to be inserted
     * @return the id in the database the {@link Weekday} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Weekday weekday) throws IllegalAccessException {
        for (int i = 0; i < weekday.getLessons().length; i++) {
            if (weekday.getLessons()[i] != null) {
                try {
                    if ((getLessonAtIdOrThrow(weekday.getLessons()[i].getId()).match(weekday.getLessons()[i]))) {
                        updateLessonWeekdayIdAtId(weekday.getLessons()[i].getId(), weekday.getId());
                    }
                } catch (NoSuchFieldException e) {
                    insertIntoDBOrThrow(weekday.getLessons()[i]);
                    updateLessonWeekdayIdAtId(weekday.getLessons()[i].getId(), weekday.getId());
                }
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();

        int weekdayId;
        if (weekday.getId() <= 0) {
            weekdayId = getNewID(TABLE_WEEKDAY, WEEKDAY_COLUMN_ID);
        } else {
            weekdayId = weekday.getId();
        }

        String query = "INSERT INTO " + TABLE_WEEKDAY + " VALUES ( " + weekdayId + ",NULL, \"" + weekday.getName() + "\")";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return weekdayId;
    }

    /**
     * inserts {@link Schedule} into database, use an ID <= 0 to insert at next unoccupied ID
     *
     * @param schedule {@link Schedule} to be inserted
     * @return the id in the database the {@link Schedule} was inserted
     * @throws IllegalAccessException if the given ID is already occupied
     */
    @Override
    public int insertIntoDBOrThrow(Schedule schedule) throws IllegalAccessException {
        for (int i = 0; i < schedule.getDays().length; i++) {
            if (schedule.getDays()[i] != null) {
                try {
                    if ((getWeekdayAtIdOrThrow(schedule.getDays()[i].getId()).match(schedule.getDays()[i]))) {
                        updateWeekdayScheduleIdAtId(schedule.getDays()[i].getId(), schedule.getId());
                    }
                } catch (NoSuchFieldException e) {
                    insertIntoDBOrThrow(schedule.getDays()[i]);
                    updateWeekdayScheduleIdAtId(schedule.getDays()[i].getId(), schedule.getId());
                }
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();

        int scheduleId;
        if (schedule.getId() <= 0) {
            scheduleId = getNewID(TABLE_SCHEDULE, SCHEDULE_COLUMN_ID);
        } else {
            scheduleId = schedule.getId();
        }

        String query = "INSERT INTO " + TABLE_SCHEDULE + " VALUES ( " + scheduleId + ", \"" + schedule.getName() + "\")";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
        return scheduleId;
    }
    //endregion

    //region deleteObjectAtId

    /**
     * deletes the {@link Subject} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Subject} to delete has
     * @throws NoSuchFieldException if there is no {@link Subject} at the given id in the Database
     */
    @Override
    public void deleteSubjectAtIdOrThrow(int id) throws NoSuchFieldException {
        final String query = "DELETE FROM " + TABLE_SUBJECT + " WHERE " + SUBJECT_COLUMN_ID + " = " + id;

        try {
            if (getCountOfRowsWhichUseSubjectAsForeignKey(id) <= 0) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(query);

                if (activity != null) {
                    activity.finish();
                }
            } else {
                DatabaseCascadeDeleteConfirmDialog dialog = new DatabaseCascadeDeleteConfirmDialog(context, getCountOfRowsWhichUseSubjectAsForeignKey(id));
                dialog.positiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = new DatabaseHelperImpl(context).getWritableDatabase();
                        db.execSQL(query);

                        if (activity != null) {
                            activity.finish();
                        }
                    }
                });
                dialog.show();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * deletes the {@link Teacher} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Teacher} to delete has
     * @throws NoSuchFieldException if there is no {@link Teacher} at the given id in the Database
     */
    @Override
    public void deleteTeacherAtIdOrThrow(int id) throws NoSuchFieldException {
        final String query = "DELETE FROM " + TABLE_TEACHER + " WHERE " + TEACHER_COLUMN_ID + " = " + id;

        try {
            if (getCountOfRowsWhichUseTeacherAsForeignKey(id) <= 0) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(query);

                if (activity != null) {
                    activity.finish();
                }
            } else {
                DatabaseCascadeDeleteConfirmDialog dialog = new DatabaseCascadeDeleteConfirmDialog(context, getCountOfRowsWhichUseTeacherAsForeignKey(id));
                dialog.positiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = new DatabaseHelperImpl(context).getWritableDatabase();
                        db.execSQL(query);

                        if (activity != null) {
                            activity.finish();
                        }
                    }
                });
                dialog.show();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * deletes the {@link Homework} at the given id from database
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Homework} to delete has
     * @throws NoSuchFieldException if there is no {@link Homework} at the given id in the Database
     */
    @Override
    public void deleteHomeworkAtIdOrThrow(int id) throws NoSuchFieldException {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_HOMEWORK + " WHERE " + HOMEWORK_COLUMN_ID + " = " + id;

        try {
            db.execSQL(query);

            if (activity != null) {
                activity.finish();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * deletes the {@link Exam} at the given id from database
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Exam} to delete has
     * @throws NoSuchFieldException if there is no {@link Exam} at the given id in the Database
     */
    @Override
    public void deleteExamAtIdOrThrow(int id) throws NoSuchFieldException {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_EXAM + " WHERE " + EXAM_COLUMN_ID + " = " + id;

        try {
            db.execSQL(query);

            if (activity != null) {
                activity.finish();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * deletes the {@link Grade} at the given id from database
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Grade} to delete has
     * @throws NoSuchFieldException if there is no {@link Grade} at the given id in the Database
     */
    @Override
    public void deleteGradeAtIdOrThrow(int id) throws NoSuchFieldException {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_GRADE + " WHERE " + GRADE_COLUMN_ID + " = " + id;

        try {
            db.execSQL(query);

            if (activity != null) {
                activity.finish();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * deletes the {@link Period} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Period} to delete has
     * @throws NoSuchFieldException if there is no {@link Period} at the given id in the Database
     */
    @Override
    public void deletePeriodAtIdOrThrow(int id) throws NoSuchFieldException {
        final String query = "DELETE FROM " + TABLE_PERIOD + " WHERE " + PERIOD_COLUMN_ID + " = " + id;

        try {
            if (getCountOfRowsWhichUsePeriodAsForeignKey(id) <= 0) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(query);

                if (activity != null) {
                    activity.finish();
                }
            } else {
                DatabaseCascadeDeleteConfirmDialog dialog = new DatabaseCascadeDeleteConfirmDialog(context, getCountOfRowsWhichUsePeriodAsForeignKey(id));
                dialog.positiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = new DatabaseHelperImpl(context).getWritableDatabase();
                        db.execSQL(query);

                        if (activity != null) {
                            activity.finish();
                        }
                    }
                });
                dialog.show();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * deletes the {@link Lesson} at the given id from database#
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Lesson} to delete has
     * @throws NoSuchFieldException if there is no {@link Lesson} at the given id in the Database
     */
    @Override
    public void deleteLessonAtIdOrThrow(int id) throws NoSuchFieldException {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_LESSON + " WHERE " + LESSON_COLUMN_ID + " = " + id;

        try {
            db.execSQL(query);

            if (activity != null) {
                activity.finish();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * deletes the {@link Weekday} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Weekday} to delete has
     * @throws NoSuchFieldException if there is no {@link Weekday} at the given id in the Database
     */
    @Override
    public void deleteWeekdayAtIdOrThrow(int id) throws NoSuchFieldException {
        final String query = "DELETE FROM " + TABLE_WEEKDAY + " WHERE " + WEEKDAY_COLUMN_ID + " = " + id;

        try {
            if (getCountOfRowsWhichUseWeekdayAsForeignKey(id) <= 0) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(query);

                if (activity != null) {
                    activity.finish();
                }
            } else {
                DatabaseCascadeDeleteConfirmDialog dialog = new DatabaseCascadeDeleteConfirmDialog(context, getCountOfRowsWhichUseWeekdayAsForeignKey(id));
                dialog.positiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = new DatabaseHelperImpl(context).getWritableDatabase();
                        db.execSQL(query);

                        if (activity != null) {
                            activity.finish();
                        }
                    }
                });
                dialog.show();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * deletes the {@link Schedule} at the given id from database,
     * uses {@link DatabaseCascadeDeleteConfirmDialog} for user confirmation if more than this object would be affected by the delete
     * <br> </br>
     * Note: if {@link DatabaseHelperImpl#DatabaseHelperImpl(Activity)} c'tor is used this methods will finish the {@link Activity} after successful delete
     *
     * @param id the id the {@link Schedule} to delete has
     * @throws NoSuchFieldException if there is no {@link Schedule} at the given id in the Database
     */
    @Override
    public void deleteScheduleAtIdOrThrow(int id) throws NoSuchFieldException {
        final String query = "DELETE FROM " + TABLE_SCHEDULE + " WHERE " + SCHEDULE_COLUMN_ID + " = " + id;

        try {
            if (getCountOfRowsWhichUseScheduleAsForeignKey(id) <= 0) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(query);

                if (activity != null) {
                    activity.finish();
                }
            } else {
                DatabaseCascadeDeleteConfirmDialog dialog = new DatabaseCascadeDeleteConfirmDialog(context, getCountOfRowsWhichUseScheduleAsForeignKey(id));
                dialog.positiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = new DatabaseHelperImpl(context).getWritableDatabase();
                        db.execSQL(query);

                        if (activity != null) {
                            activity.finish();
                        }
                    }
                });
                dialog.show();
            }
        } catch (Exception e) {
            throw new NoSuchFieldException();
        }
    }

    //endregion
    //endregion


    /**
     * represents the whole database to a String
     *
     * @return database as String
     */
    @Override
    public String toString() {
        return toString(TABLE_SUBJECT) + "\n \n" +
                toString(TABLE_TEACHER) + "\n \n" +
                toString(TABLE_HOMEWORK) + "\n \n" +
                toString(TABLE_EXAM) + "\n \n" +
                toString(TABLE_GRADE) + "\n \n" +
                toString(TABLE_PERIOD) + "\n \n" +
                toString(TABLE_LESSON) + "\n \n" +
                toString(TABLE_WEEKDAY) + "\n \n" +
                toString(TABLE_SCHEDULE);
    }

    /**
     * represents the given Table from the database as String
     *
     * @param tableName name of the table to convert tzo String, choose from the TABLE_XXX constants in {@link DatabaseHelper}
     * @return database as String
     */
    @Override
    public String toString(String tableName) {
        StringBuilder returnString = new StringBuilder("############################### \n" + tableName + "\n-------------------------------\n");

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName;


        try (Cursor cursor = db.rawQuery(query, null)) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    returnString.append(cursor.getColumnName(i)).append(": \t").append(cursor.getString(i)).append(" || ");
                }
                returnString.append("\n");
            }
        }
        returnString.append("############################### ");

        return returnString.toString();
    }

    /**
     * returns the size of the given Table in the Database
     *
     * @param tableName name of the table, choose from the TABLE_XXX constants in {@link DatabaseHelper}
     * @return the size of the table. 0 if table has no elements, 1 if table has one element and so on
     */
    @Override
    public int size(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) " + "FROM " + tableName;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int size = cursor.getInt(0);

        cursor.close();
        db.close();

        return size;
    }

    /**
     * returns all indices of the given Table in the Database
     *
     * @param tableName name of the table, choose from the TABLE_XXX constants in {@link DatabaseHelper}
     * @return all indices of the given Table in the Database as array
     */
    @Override
    public int[] getIndices(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * " + "FROM " + tableName;

        ArrayList<Integer> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            arrayList.add(cursor.getInt(0));
        }

        cursor.close();
        db.close();


        int[] returningArray = new int[arrayList.size()];
        for (int i = 0; i < returningArray.length; i++) {
            returningArray[i] = arrayList.get(i);
        }

        return returningArray;
    }

    /**
     * resets the database, onUpgrade can also called instead
     */
    public void resetDatabase() {
        onUpgrade(this.getWritableDatabase(), 1, 1);
    }

    //todo remove
    public void fillDatabaseWithExamples() {
        Teacher teacher1 = new Teacher(1, "Bräuer", "BRÄ", Teacher.MALE);
        Teacher teacher2 = new Teacher(2, "Dickens", "DICK", Teacher.FEMALE);

        Subject subject1 = new Subject(1, teacher1, "Math", "B213", "#ffffff");
        Subject subject2 = new Subject(2, teacher2, "German", "B308", "#ff0000");

        Exam exam1 = new Exam(1, subject1, "A simple Test in Math", "2017-06-13");
        Exam exam2 = new Exam(2, subject2, "German Test", "2017-05-03");

        Grade grade1 = new Grade(1, subject1, "2nd test", "13");
        Grade grade2 = new Grade(2, subject2, "3rd test", "4");
        Grade grade3 = new Grade(3, subject1, "5th test", "14");
        Grade grade4 = new Grade(4, subject2, "4th test", "6");

        Homework homework1 = new Homework(1, subject1, "Geometry - draw a rectangle", "2017-05-06", false);
        Homework homework2 = new Homework(2, subject2, "Characterisation Goethe", "2017-06-03", false);
        Homework homework3 = new Homework(3, subject1, "The calculation of probabilities", "2017-05-07", true);
        Homework homework4 = new Homework(4, subject2, "Literature during WW2", "2017-05-05", false);

        Period period1 = new Period(1, 1, "07-45-00", "08-30-00");
        Period period2 = new Period(2, 2, "08-35-00", "09-20-00");
        Period period3 = new Period(3, 3, "09-35-00", "10-20-00");
        Period period4 = new Period(4, 4, "10-25-00", "11-20-00");
        Period period5 = new Period(5, 5, "11-35-00", "12-10-00");
        Period period6 = new Period(6, 6, "12-15-00", "13-00-00");

        Lesson lesson1 = new Lesson(1, subject1, period1);
        Lesson lesson2 = new Lesson(2, subject2, period2);
        Lesson lesson3 = new Lesson(3, subject1, period3);
        Lesson lesson4 = new Lesson(4, subject2, period4);

        Weekday weekday1 = new Weekday(1, Weekday.MONDAY, new Lesson[]{lesson1, lesson2, lesson3, lesson4});

        Schedule schedule1 = new Schedule(1, "a", new Weekday[]{null, null, null, null, null, null});

        //###################################Insert###############################################
//
//        insertIntoDB(teacher1);
//        insertIntoDB(teacher2);
//
//        insertIntoDB(subject1);
//        insertIntoDB(subject2);
//
//        insertIntoDB(exam1);
//        insertIntoDB(exam2);
//
//        insertIntoDB(grade1);
//        insertIntoDB(grade2);
//        insertIntoDB(grade3);
//        insertIntoDB(grade4);
//
//
//        insertIntoDB(homework1);
//        insertIntoDB(homework2);
//        insertIntoDB(homework3);
//        insertIntoDB(homework4);
//
//        insertIntoDB(period1);
//        insertIntoDB(period2);
//        insertIntoDB(period3);
//        insertIntoDB(period4);
//        insertIntoDB(period5);
//        insertIntoDB(period6);
//
//        insertIntoDB(lesson1);
//        insertIntoDB(lesson2);
//        insertIntoDB(lesson3);
//        insertIntoDB(lesson4);
//
//        insertIntoDB(weekday1);

        insertIntoDB(schedule1);

    }


    //region private methods

    /**
     * deletes all tables from the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void dropAllTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.setForeignKeyConstraintsEnabled(false);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_SUBJECT);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TEACHER);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_HOMEWORK);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_EXAM);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_GRADE);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PERIOD);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_LESSON);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_WEEKDAY);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_SCHEDULE);
    }

    /**
     * create all tables in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createTables(SQLiteDatabase sqLiteDatabase) {
        createSubjectTable(sqLiteDatabase);
        createTeacherTable(sqLiteDatabase);
        createHomeworkTable(sqLiteDatabase);
        createExamTable(sqLiteDatabase);
        createGradeTable(sqLiteDatabase);
        createPeriodTable(sqLiteDatabase);
        createLessonTable(sqLiteDatabase);
        createWeekdayTable(sqLiteDatabase);
        createScheduleTable(sqLiteDatabase);
    }

    //region table creation

    /**
     * create subject table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createSubjectTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_SUBJECT + "(" +
                SUBJECT_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SUBJECT_COLUMN_TEACHER_ID + " INTEGER NOT NULL " +
                "REFERENCES " + TABLE_TEACHER + "(" + TEACHER_COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                SUBJECT_COLUMN_NAME + " VARCHAR NOT NULL, " +
                SUBJECT_COLUMN_ROOM + " VARCHAR NOT NULL, " +
                SUBJECT_COLUMN_COLOR + " VARCHAR NOT NULL )"
        );
    }

    /**
     * create teacher table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createTeacherTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TEACHER + "(" +
                TEACHER_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                TEACHER_COLUMN_NAME + " VARCHAR NOT NULL, " +
                TEACHER_COLUMN_ABBREVIATION + " VARCHAR UNIQUE, " +
                TEACHER_COLUMN_GENDER + " CHAR NOT NULL )"
        );
    }

    /**
     * create homework table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createHomeworkTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HOMEWORK + "(" +
                HOMEWORK_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                HOMEWORK_COLUMN_SUBJECT_ID + " INTEGER NOT NULL " +
                "REFERENCES " + TABLE_SUBJECT + "(" + SUBJECT_COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                HOMEWORK_COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                HOMEWORK_COLUMN_DEADLINE + " DATE NOT NULL, " +
                HOMEWORK_COLUMN_DONE + " INTEGER )"
        );
    }

    /**
     * create exam table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createExamTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_EXAM + "(" +
                EXAM_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                EXAM_COLUMN_SUBJECT_ID + " INTEGER NOT NULL " +
                "REFERENCES " + TABLE_SUBJECT + "(" + SUBJECT_COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                EXAM_COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                EXAM_COLUMN_DEADLINE + " DATE NOT NULL)"
        );
    }

    /**
     * create grade table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createGradeTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_GRADE + "(" +
                GRADE_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                GRADE_COLUMN_SUBJECT_ID + " INTEGER NOT NULL " +
                "REFERENCES " + TABLE_SUBJECT + "(" + SUBJECT_COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                GRADE_COLUMN_NAME + " VARCHAR NOT NULL, " +
                GRADE_COLUMN_GRADE + " VARCHAR NOT NULL )"
        );
    }

    /**
     * create period table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createPeriodTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PERIOD + "(" +
                PERIOD_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                PERIOD_COLUMN_SCHOOL_HOUR_NO + " INTEGER NOT NULL, " +
                PERIOD_COLUMN_STARTTIME + " TIME NOT NULL, " +
                PERIOD_COLUMN_ENDTIME + " TIME NOT NULL )"
        );
    }

    /**
     * create lesson table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createLessonTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_LESSON + "(" +
                LESSON_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                LESSON_COLUMN_SUBJECT_ID + " INTEGER NOT NULL " +
                "REFERENCES " + TABLE_SUBJECT + "(" + SUBJECT_COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                LESSON_COLUMN_PERIOD_ID + " INTEGER NOT NULL " +
                "REFERENCES " + TABLE_PERIOD + "(" + PERIOD_COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                LESSON_COLUMN_WEEKDAY_ID + " INTEGER  " +
                "REFERENCES " + TABLE_WEEKDAY + "(" + WEEKDAY_COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE)"
        );
    }

    /**
     * create weekday table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createWeekdayTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_WEEKDAY + "(" +
                WEEKDAY_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                WEEKDAY_COLUMN_SCHEDULE_ID + " INTEGER " +
                "REFERENCES " + TABLE_SCHEDULE + "(" + SCHEDULE_COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                WEEKDAY_COLUMN_NAME + " VARCHAR NOT NULL )"
        );
    }

    /**
     * create schedule table in the schoolPlanner Database
     *
     * @param sqLiteDatabase the schoolPlanner Database
     */
    private void createScheduleTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_SCHEDULE + "(" +
                SCHEDULE_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SCHEDULE_COLUMN_NAME + " VARCHAR NOT NULL )"
        );
    }
    //endregion

    /**
     * returns the highest id in the given Table in the Database + 1
     *
     * @param tableName    name of the table, choose from the TABLE_XXX constants of {@link DatabaseHelper}
     * @param idColumnName name of the column containing the id's, choose from the XXX_COLUMN_ID constants in {@link DatabaseHelper}
     * @return the highest id in the Table + 1
     */
    private int getNewID(String tableName, String idColumnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + idColumnName + ") FROM " + tableName;

        try (Cursor cursor = db.rawQuery(query, null)) {
            cursor.moveToFirst();

            return cursor.getInt(0) + 1;
        }
    }

    /**
     * gets the {@link Lesson}s as array at a specific {@link Weekday}
     *
     * @param weekdayID id of the {@link Weekday}
     * @return Periods at the given Weekday as Array
     * @throws Exception if an error occurs
     */
    private Lesson[] getLessonsAtWeekday(int weekdayID) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = buildQueryToGetRowAtId(TABLE_LESSON, LESSON_COLUMN_WEEKDAY_ID, weekdayID);

        ArrayList<Lesson> lessonArrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            lessonArrayList.add(getLessonAtIdOrThrow(cursor.getInt(0)));
        }

        cursor.close();
        db.close();

        return lessonArrayList.toArray(new Lesson[0]);
    }


    /**
     * gets the {@link Weekday}s as array at a specific {@link Schedule}
     *
     * @param scheduleID id of the {@link Schedule}
     * @return Weekdays at the given schedule as Array
     */
    private Weekday[] getWeekdaysAtSchedule(int scheduleID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = buildQueryToGetRowAtId(TABLE_WEEKDAY, WEEKDAY_COLUMN_SCHEDULE_ID, scheduleID);

        ArrayList<Weekday> weekdayArrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            weekdayArrayList.add(getWeekdayAtId(cursor.getInt(0)));
        }

        cursor.close();
        db.close();

        return weekdayArrayList.toArray(new Weekday[0]);
    }

    /**
     * method to build a SQLite query to get a row in a specific table at a specific id from schoolPlaner database
     *
     * @param table         name of the table to get the row from as String
     * @param tableColumnID name of the id column in the given table as String
     * @param id            id of the row to get
     * @return a SQLite query  as String
     */
    private String buildQueryToGetRowAtId(String table, String tableColumnID, int id) {
        return "SELECT * " +
                "FROM " + table +
                " WHERE " + tableColumnID +
                " = " + id;
    }

    /**
     * updates the WEEKDAY_COLUMN_SCHEDULE_ID column in the TABLE_WEEKDAY with the new value for at a given id
     *
     * @param id         id of the object to update
     * @param scheduleId id of the new schedule
     */
    private void updateWeekdayScheduleIdAtId(int id, int scheduleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.setForeignKeyConstraintsEnabled(false);

        String query = "UPDATE " + TABLE_WEEKDAY + " SET " + WEEKDAY_COLUMN_SCHEDULE_ID + " = " + scheduleId + " WHERE " + WEEKDAY_COLUMN_ID + " = " + id;

        try {
            db.execSQL(query);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject(WEEKDAY_COLUMN_SCHEDULE_ID + " in WEEKDAY", context);
        }
    }

    /**
     * updates the LESSON_COLUMN_WEEKDAY_ID column in the TABLE_LESSON with the new value for at a given id
     *
     * @param id        id of the object to update
     * @param weekdayId id of the new schedule
     */
    private void updateLessonWeekdayIdAtId(int id, int weekdayId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.setForeignKeyConstraintsEnabled(false);

        String query = "UPDATE " + TABLE_LESSON + " SET " + LESSON_COLUMN_WEEKDAY_ID + " = " + weekdayId + " WHERE " + LESSON_COLUMN_ID + " = " + id;

        try {
            db.execSQL(query);
        } catch (Exception e) {
            ExceptionHandler.handleDatabaseExceptionForUpdatingAnNotExistingObject(WEEKDAY_COLUMN_SCHEDULE_ID + " in WEEKDAY", context);
        }
    }

    //region getCountOfRowsWhichUseObjectAsForeignKey

    /**
     * method to read the count of objects from database which contains a specific {@link Subject} at the given id
     *
     * @param id the id of the {@link Subject}
     * @return count of objects from database which contains a specific {@link Subject} at the given id
     */
    private int getCountOfRowsWhichUseSubjectAsForeignKey(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String queryExam = "SELECT COUNT(*) FROM (SELECT * FROM " + TABLE_EXAM + " WHERE " + EXAM_COLUMN_SUBJECT_ID + " = " + id + " )";
        String queryHomework = "SELECT COUNT(*) FROM (SELECT * FROM " + TABLE_HOMEWORK + " WHERE " + HOMEWORK_COLUMN_SUBJECT_ID + " = " + id + " )";
        String queryGrade = "SELECT COUNT(*) FROM (SELECT * FROM " + TABLE_GRADE + " WHERE " + GRADE_COLUMN_SUBJECT_ID + " = " + id + " )";
        String queryLesson = "SELECT COUNT(*) FROM (SELECT * FROM " + TABLE_LESSON + " WHERE " + LESSON_COLUMN_SUBJECT_ID + " = " + id + " )";

        int usingElementsCount = 0;

        try {
            cursor = db.rawQuery(queryExam, null);
            cursor.moveToFirst();
            usingElementsCount += cursor.getInt(0);

            cursor = db.rawQuery(queryHomework, null);
            cursor.moveToFirst();
            usingElementsCount += cursor.getInt(0);

            cursor = db.rawQuery(queryGrade, null);
            cursor.moveToFirst();
            usingElementsCount += cursor.getInt(0);

            cursor = db.rawQuery(queryLesson, null);
            cursor.moveToFirst();
            usingElementsCount += cursor.getInt(0);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return usingElementsCount;
    }

    /**
     * method to read the count of objects from database which contains a specific {@link Teacher} at the given id
     *
     * @param id the id of the {@link Teacher}
     * @return count of objects from database which contains a specific {@link Teacher} at the given id
     */
    private int getCountOfRowsWhichUseTeacherAsForeignKey(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String querySubject = "SELECT COUNT(*) FROM (SELECT * FROM " + TABLE_SUBJECT + " WHERE " + SUBJECT_COLUMN_TEACHER_ID + " = " + id + " )";
        String querySubjectIDs = "SELECT " + SUBJECT_COLUMN_ID + " FROM (SELECT * FROM " + TABLE_SUBJECT + " WHERE " + SUBJECT_COLUMN_TEACHER_ID + " = " + id + " )";

        int usingElementsCount = 0;

        try {
            //teacher using subjects count
            cursor = db.rawQuery(querySubject, null);
            cursor.moveToFirst();
            usingElementsCount += cursor.getInt(0);

            //things which use teacher using subjects count
            cursor = db.rawQuery(querySubjectIDs, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                usingElementsCount += getCountOfRowsWhichUseSubjectAsForeignKey(cursor.getInt(0));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return usingElementsCount;
    }

    /**
     * method to read the count of objects from database which contains a specific {@link Period} at the given id
     *
     * @param id the id of the {@link Period}
     * @return count of objects from database which contains a specific {@link Period} at the given id
     */
    private int getCountOfRowsWhichUsePeriodAsForeignKey(int id) {

        String queryLesson = "SELECT COUNT(*) FROM (SELECT * FROM " + TABLE_LESSON + " WHERE " + LESSON_COLUMN_PERIOD_ID + " = " + id + " )";

        int usingElementsCount = 0;

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(queryLesson, null)) {
            cursor.moveToFirst();
            usingElementsCount += cursor.getInt(0);

        }
        return usingElementsCount;
    }

    /**
     * method to read the count of objects from database which contains a specific {@link Weekday} at the given id
     *
     * @param id the id of the {@link Weekday}
     * @return count of objects from database which contains a specific {@link Weekday} at the given id
     */
    private int getCountOfRowsWhichUseWeekdayAsForeignKey(int id) {

        String queryLesson = "SELECT COUNT(*) FROM (SELECT * FROM " + TABLE_LESSON + " WHERE " + LESSON_COLUMN_WEEKDAY_ID + " = " + id + " )";

        int usingElementsCount = 0;

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(queryLesson, null)) {
            cursor.moveToFirst();
            usingElementsCount += cursor.getInt(0);

        }
        return usingElementsCount;
    }

    /**
     * method to read the count of objects from database which contains a specific {@link Schedule} at the given id
     *
     * @param id the id of the {@link Schedule}
     * @return count of objects from database which contains a specific {@link Schedule} at the given id
     */
    private int getCountOfRowsWhichUseScheduleAsForeignKey(int id) {

        String queryWeekday = "SELECT COUNT(*) FROM (SELECT * FROM " + TABLE_WEEKDAY + " WHERE " + WEEKDAY_COLUMN_SCHEDULE_ID + " = " + id + " )";

        int usingElementsCount = 0;

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery(queryWeekday, null)) {
            cursor.moveToFirst();
            usingElementsCount += cursor.getInt(0);

        }
        return usingElementsCount;
    }
    //endregion

    //endregion

}
