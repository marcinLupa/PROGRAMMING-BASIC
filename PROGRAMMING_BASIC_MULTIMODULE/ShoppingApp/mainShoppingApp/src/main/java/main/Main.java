package main;

import exceptions.MyException;
import services.MenuService;

public class Main {
    public static void main(String[] args) {
        try {

            new MenuService().Manage("C:/Users/48783/IdeaProjects/DUZE PROGRAMY/PROGRAMMING_BASIC_MULTIMODULE/ShoppingApp/jsonShoppingApp/src/main/resources/");
        } catch
        (MyException e) {
            System.err.println("EXCEPTION DATE TIME: " + e.getExceptionInfo());
            System.err.println("EXCEPTION CODE: " + e.getExceptionInfo());
            System.out.println(e.getExceptionInfo().getMessage());
        }

    }
}