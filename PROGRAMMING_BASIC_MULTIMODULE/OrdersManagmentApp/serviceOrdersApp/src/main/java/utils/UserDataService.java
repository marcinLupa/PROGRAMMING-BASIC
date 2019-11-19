package utils;


import exceptions.ExceptionCode;
import exceptions.MyException;
import model.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Stream;

public class UserDataService {

    private Scanner sc = new Scanner(System.in);

    public String getEmail(){
        String text=sc.nextLine();
        if (!text.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) {
            throw new MyException(ExceptionCode.VALIDATION,"WRONG FORMAT OF E-MAIL "+text);
        }
        return text;
    }
    public int getOptionsWithScale(int scale){
        String text=sc.nextLine();
        if (!text.matches("[0-9]*")) {
            throw new MyException(ExceptionCode.VALIDATION,"WRONG FORMAT OF OPTION "+text);
        }
        if (Integer.parseInt(text)> scale || Integer.parseInt(text)<0 ){
            throw new MyException(ExceptionCode.VALIDATION,"INTEGER OUT OF BOUND "+text);
        }
        return Integer.parseInt(text);
    }
    public int getInts(){
        String text=sc.nextLine();
        if (!text.matches("[0-9]*")) {
            throw new MyException(ExceptionCode.VALIDATION,"WRONG FORMAT OF OPTION "+text);
        }
        if ( Integer.parseInt(text)<0 ){
            throw new MyException(ExceptionCode.VALIDATION,"GIVEN INT UNDER ZERO"+text);
        }

        return Integer.parseInt(text);
    }
    public int getAge(){
        String text=sc.nextLine();
        if (!text.matches("[0-9]*")) {
            throw new MyException(ExceptionCode.VALIDATION,"WRONG FORMAT OF AGE "+text);
        }if (Integer.parseInt(text)<18) {
            throw new MyException(ExceptionCode.VALIDATION,"AGE UNDER 18 "+text);
        }
        return Integer.parseInt(text);
    }
    public boolean getYesOrNo() {
        String text = sc.nextLine();
        if (!text.matches("TAK|NIE|tak|nie+")) {
            throw new MyException(ExceptionCode.VALIDATION, "NOT CORRECT AGREEMENT FORMAT " + text);
        }
        if (text.equalsIgnoreCase("TAK")) {
            return true;
        }
        if (text.equalsIgnoreCase("NIE")) {
            return false;
        }
        else {
            throw new MyException(ExceptionCode.VALIDATION, "INCORRECT VALUE " + text);
        }
    }
    public Category getCategory(){
        String text=sc.nextLine();
        if (!text.matches(String.join("|", (Stream.of(Category.values()).map(Category::name).toArray(String[]::new))))) {
            throw new MyException(ExceptionCode.VALIDATION,"WRONG FORMAT OF CATEGORY "+text);
        }
        return Category.valueOf(text);
    }
    public LocalDate getLocalDate() {

        String text = sc.nextLine();
        if (text == null) {
            throw new MyException(ExceptionCode.VALIDATION, "ORDER DATE IS NULL ");
        }
        if(!text.matches("\\d{4}-\\d{2}-\\d{2}")){
            throw new MyException(ExceptionCode.VALIDATION, "ORDER DATE WRONG FORMAT ");
        }
        if(LocalDate.parse(text).isBefore(LocalDate.now())){
            throw new MyException(ExceptionCode.VALIDATION, "ORDER DATE IS FROM PAST ");
        }

        return LocalDate.parse(
                text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    }
    public String getString() {


        String text = sc.nextLine();
        if (!text.matches("[A-Z ]*")) {
            throw new MyException(ExceptionCode.VALIDATION, "WRONG FORMAT OF STRING: " + text);
        }
        return text;
    }
    public BigDecimal getPrice() {

        String text = sc.nextLine();
        if (!text.matches("\\d.+")) {
            throw new MyException(ExceptionCode.VALIDATION, "VALUE IS NOT BIG DECIMAL: " + text);
        }
        if (new BigDecimal(text).compareTo(BigDecimal.ZERO) < 0) {
            throw new MyException(ExceptionCode.VALIDATION, "VALUE UNDER ZERO " + text);
        }

        return new BigDecimal(text);
    }
    public void close() {
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }
}
