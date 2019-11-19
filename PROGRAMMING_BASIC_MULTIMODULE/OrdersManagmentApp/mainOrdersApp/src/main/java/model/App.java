package model;

import exceptions.MyException;
import services.MenuService;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {

          new MenuService().manage();
        } catch
                (MyException e) {
            e.printStackTrace();
            System.err.println("EXCEPTION DATE TIME: " + e.getExceptionInfo().getTimeOfException());
            System.err.println("EXCEPTION CODE: " + e.getExceptionInfo().getExceptionCode());
            System.out.println(e.getExceptionInfo().getMessage());
        }


    }
}
