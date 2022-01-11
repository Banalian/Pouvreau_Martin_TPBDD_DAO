package fr.banalian.tpbdd.tpdao.model;

import javax.persistence.*;

@Entity
@Table(name = "grant")
public class Grant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "destination")
    private String destination;

    @Column(name = "totalseats")
    private int totalSeats;

    @OneToOne
    @JoinColumn(name = "teacher")
    private Teacher teacher;

    public Grant() {
    }

    public Grant(int id, String destination, int totalSeats, Teacher teacher) {
        this.id = id;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.teacher = teacher;
    }

    public Grant(String destination, int totalSeats, Teacher teacher) {
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.teacher = teacher;
    }

    public String toString() {
        return "\t" + id + "\t\t" + destination + "\t\t" + totalSeats + "\t\t" + teacher.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
