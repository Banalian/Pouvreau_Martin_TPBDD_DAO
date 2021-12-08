package fr.banalian.tpbdd.tpdao;

public class MainClass {

    public static void main(String[] args) {

        ConnectBdd.initConnection();


        ConnectBdd.closeConnection();
    }

}
