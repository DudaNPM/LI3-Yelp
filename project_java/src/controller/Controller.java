package controller;

import model.GestReviews;
import model.Crono;
import view.UI;
import java.io.IOException;

public class Controller {
    public static void run() {
        GestReviews sgr = new GestReviews();
        boolean exit = false;
        while (!exit) {
            int option;
            if (sgr.getFiles().isEmpty()) option = 13;
            else option = UI.Menu();
            switch (option) {
                case  0 -> exit = true; // leave
                case  1 -> {
                    Crono.start();
                    String stats = sgr.calcStats1();
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(stats);
                } // stats 1
                case  2 -> {
                    Crono.start();
                    String stats = sgr.calcStats2();
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(stats);
                } // stats 2
                case  3 -> {
                    Crono.start();
                    String query = sgr.calcQ1();
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 1
                case  4 -> {
                    int mes = UI.AskMes();
                    int ano = UI.AskAno();
                    Crono.start();
                    String query = sgr.calcQ2(mes, ano);
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 2
                case  5 -> {
                    String userId = UI.AskUserId();
                    Crono.start();
                    String query = sgr.calcQ3(userId);
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 3
                case  6 -> {
                    String businessId = UI.AskBusinessId();
                    Crono.start();
                    String query = sgr.calcQ4(businessId);
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 4
                case  7 -> {
                    String userId = UI.AskUserId();
                    Crono.start();
                    String query = sgr.calcQ5(userId);
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 5
                case  8 -> {
                    int x = UI.AskNum();
                    Crono.start();
                    String query = sgr.calcQ6(x);
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 6
                case  9 -> {
                    Crono.start();
                    String query = sgr.calcQ7();
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query7
                case 10 -> {
                    int x = UI.AskNum();
                    Crono.start();
                    String query = sgr.calcQ8(x);
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 8
                case 11 -> {
                    String businessId = UI.AskBusinessId();
                    int x = UI.AskNum();
                    Crono.start();
                    String query = sgr.calcQ9(businessId, x);
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 9
                case 12 -> {
                    Crono.start();
                    String query = sgr.calcQ10();
                    Crono.stop();
                    Crono.printElapsedTime();
                    System.out.println(query);
                } // query 10
                case 13 -> {
                    int aux = UI.AskFileMode();
                    if (aux == 1) { // texto
                        aux = UI.AskFileNamePref();
                        String userPath, businessPath, reviewPath;
                        if (aux == 1) { // padrao
                            userPath = "users_full.csv";
                            businessPath = "business_full.csv";
                            reviewPath = "reviews_1M.csv";
                        } else { // personalizado
                            String[] inputs = UI.indicarCaminho();
                            userPath = inputs[0];
                            businessPath = inputs[1];
                            reviewPath = inputs[2];
                        }
                        try {
                            Crono.start();
                            sgr.load(userPath,businessPath,reviewPath);
                            UI.SuccessMsg(1);
                            Crono.stop();
                            Crono.printElapsedTime();
                        }
                        catch (IOException exc) { exc.printStackTrace(); }
                    } else { // objetos
                        String filename = UI.AskFileName();
                        try {
                            Crono.start();
                            sgr = GestReviews.loadEstadoBin(filename); UI.SuccessMsg(1);
                            Crono.stop();
                            Crono.printElapsedTime();
                        }
                        catch (IOException | ClassNotFoundException exc) { exc.printStackTrace(); }
                    }
                } // load files
                case 14 -> {
                    int aux = UI.AskFileNamePref();
                    if (aux == 1) { // padrao
                        try { sgr.saveEstadoBin("gestReviews.dat"); UI.SuccessMsg(2); }
                        catch (IOException e) { e.printStackTrace(); }
                    } else { // personalizado
                        String filename = UI.AskFileName();
                        try { sgr.saveEstadoBin(filename); UI.SuccessMsg(2); }
                        catch (IOException e) { e.printStackTrace(); }
                    }
                } // save struct
            }
        }
    }
}