package fr.banalian.tpbdd.tpdao.model;

public class Grant {
    private int id;
    private String destination;
    private int totalSeats;
    private int teacherId;

    public Grant(int id, String destination, int totalSeats, int teacherId) {
        this.id = id;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.teacherId = teacherId;
    }

    public Grant(String destination, int totalSeats, int teacherId) {
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.teacherId = teacherId;
    }

    public String toString() {
        return "\t" + id + "\t\t" + destination + "\t\t" + totalSeats + "\t\t" + teacherId;
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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
