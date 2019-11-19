package services;

import exc.ExceptionCode;
import exc.MyException;
import model.SortType;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserDataService {

    private Scanner sc = new Scanner(System.in);

    public int getInt(/*String message*/) {
        //     System.out.println(message);

        String text = sc.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.VALIDATION, "VALUE IS NOT DIGIT: " + text);
        }
        if (Integer.parseInt(text) < 1 || Integer.parseInt(text) > 12) {
            throw new MyException(ExceptionCode.VALIDATION, "RANGE OUT OF BOUND " + text);
        }

        return Integer.parseInt(text);
    }

    public SortType getSortType(/*String message*/) {
        //     System.out.println(message);

        String text = sc.nextLine();
        if (!text.matches("MARKA|CENA|PRZEBIEG|KOLOR|ILOSC_SKLADNIKOW")) {
            throw new MyException(ExceptionCode.VALIDATION, "VALUE IS NOT CORRECT SORT TYPE: " + text);
        }

        if (text.matches("MARKA")) {
            return SortType.MARK;
        }
        if (text.matches("CENA")) {
            return SortType.PRICE;
        }
        if (text.matches("PRZEBIEG")) {
            return SortType.MILLEAGE;
        }
        if (text.matches("KOLOR")) {
            return SortType.COLOUR;
        }
        if (text.matches("ILOSC_SKLADNIKOW")) {
            return SortType.COMPONNENTS;
        } else {
            throw new MyException(ExceptionCode.VALIDATION, "VALUE IS NOT CORRECT SORT TYPE: " + text);
        }
    }


    public int getIntMilleage(/*String message*/) {
        //     System.out.println(message);

        String text = sc.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.VALIDATION, "VALUE IS NOT DIGIT: " + text);
        }
        if (Integer.parseInt(text) < 0) {
            throw new MyException(ExceptionCode.VALIDATION, "VALUE OUT OF BOUND " + text);
        }

        return Integer.parseInt(text);
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
