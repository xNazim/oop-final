package home.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClassesModel {
    private SimpleStringProperty classId;
    private SimpleStringProperty c_Name;
    private SimpleStringProperty Lecturer;
    private SimpleIntegerProperty lesson_p_Week;

    public String getClassId() {
        return classId.get();
    }

    public void setClassId(String classId) {
        this.classId.set(classId);
    }

    public void setC_Name(String c_Name) {
        this.c_Name.set(c_Name);
    }

    public void setLecturer(String lecturer) {
        this.Lecturer.set(lecturer);
    }

    public void setLesson_p_Week(int lesson_p_Week) {
        this.lesson_p_Week.set(lesson_p_Week);
    }

    public SimpleStringProperty classIdProperty() {
        return classId;
    }

    public String getC_Name() {
        return c_Name.get();
    }

    public SimpleStringProperty c_NameProperty() {
        return c_Name;
    }

    public String getLecturer() {
        return Lecturer.get();
    }

    public SimpleStringProperty lecturerProperty() {
        return Lecturer;
    }

    public int getLesson_p_Week() {
        return lesson_p_Week.get();
    }

    public SimpleIntegerProperty lesson_p_WeekProperty() {
        return lesson_p_Week;
    }

    public ClassesModel(String classId, String c_Name, String Lecturer, int lesson_p_Week) {
        this.classId = new SimpleStringProperty(classId);
        this.c_Name = new SimpleStringProperty(c_Name);
        this.Lecturer = new SimpleStringProperty(Lecturer);
        this.lesson_p_Week = new SimpleIntegerProperty(lesson_p_Week);
    }

}
