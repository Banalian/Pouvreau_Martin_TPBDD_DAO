package fr.banalian.tpbdd.tpdao.model;

public class Evaluation {
    private int id;
    private float grade;
    private int teacherId;

    public Evaluation(int id, float grade, int teacherId) {
        this.id = id;
        this.grade = grade;
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
