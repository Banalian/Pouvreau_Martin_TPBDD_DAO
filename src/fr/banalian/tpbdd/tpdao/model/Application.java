package fr.banalian.tpbdd.tpdao.model;

public class Application {
    private String studentId;
    private int grantId;
    private String university;
    private int eval1Id;
    private int eval2Id;
    private float finalGrade;

    public Application(String studentId, int grantId, String university, int eval1Id, int eval2Id, float finalGrade) {
        this.studentId = studentId;
        this.grantId = grantId;
        this.university = university;
        this.eval1Id = eval1Id;
        this.eval2Id = eval2Id;
        this.finalGrade = finalGrade;
    }

    public String toString() {
        return  "\t" + studentId + "\t\t" + grantId + "\t\t" + university + "\t\t" + eval1Id + "\t\t" + eval2Id + "\t\t" + finalGrade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getGrantId() {
        return grantId;
    }

    public void setGrantId(int grantId) {
        this.grantId = grantId;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public int getEval1Id() {
        return eval1Id;
    }

    public void setEval1Id(int eval1Id) {
        this.eval1Id = eval1Id;
    }

    public int getEval2Id() {
        return eval2Id;
    }

    public void setEval2Id(int eval2Id) {
        this.eval2Id = eval2Id;
    }

    public float getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(float finalGrade) {
        this.finalGrade = finalGrade;
    }
}
