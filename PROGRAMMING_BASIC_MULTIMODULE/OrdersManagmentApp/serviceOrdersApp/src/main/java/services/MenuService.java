package services;

import exceptions.ExceptionCode;
import exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.Category;
import utils.MenuUtilsService;
import utils.UserDataService;

import java.util.Arrays;
import java.util.List;

@Builder
@AllArgsConstructor
@Data

public class MenuService {
    private MenuUtilsService menuUtilsService;

    private static final String FILENAME = "C:/Users/48783/IdeaProjects/DUZE PROGRAMY/PROGRAMMING_BASIC_MULTIMODULE/OrdersManagmentApp/jsonOrdersApp/src/main/resources/ORDER_LIST.json";
    private final UserDataService userDataService = new UserDataService();
    private final List<String> options = Arrays.asList("1 - DODAJ ZAMOWIENIE DO ISTNIEJACEJ LISTY ZAMOWIEN ",
            "2 - WYSWIETL AKTUALNA LISTE ZAMOWIEN", "3 - SREDNIA CANA ZAMOWIEN W PRZEDZIALE CZASOWYM",
            "4 - NAJWYZSZA CENA PRODUKTU W DANEJ KATEGORII", "5 - WYSYLANIE MAILEM ZESTAWIENIA ZAMOWIEN",
            "6 - DNI W KTORYCH JEST NAJWIECEJ I NAJMNIEJ ZAMOWIEN",
            "7 - KLIENT KTÓRY ZAPLACIL NAJWIECEJ ZA WSZYSTKIE PRODUKTY",
            "8 - CALKOWITA CENA ZA WSZYSTKIE ZAMOWIENIA Z UWZGLEDNIEM ZNIZEK 2% ZA DATE REALIZACJI i 3% ZA WIEK",
            "9 - OBLICZANIE ILU KLIENTOW KUPILO PRODUKTY W ILOSCI POWYZEJ X KTOREGO PODAJE UZYTKOWNIK",
            "10 - NAJBARDZIEJ POPULARNA KATEGORIA",
            "11 - ZESTAWIENIE ILE PRODUKTOW ZOSTALO ZAMOWIONYCH NA DANY MIESIAC",
            "12 - ZESTAWIENIE MIESIAC I NAJPOPULARNIEJSZA KATEGORIA W DANYM MIESIACU",
            "13 - WYJSCIE Z PROGRAMU");

    public MenuService() {

        this.menuUtilsService = new MenuUtilsService(FILENAME);
    }

    public void manage() {
        try {
            while (true) {
                try {
                    System.out.println("PODAJ OPCJE KTORA WYBIERASZ");
                    options.forEach(System.out::println);
                    int option = userDataService.getOptionsWithScale(options.size());
                    switch (option) {
                        case 1:
                            menuUtilsService.setOrders(new Orders(menuUtilsService.ordersFromUser()));
                            break;
                        case 2:
                            menuUtilsService.getOrders().getOrders().forEach(System.out::println);
                            break;
                        case 3:
                            System.out.println(String.format("%2.2f", menuUtilsService.avgPriceInDates()) + " PLN");
                            break;
                        case 4:
                            for (Category c : Category.values()) {
                                System.out.println("NAJWYZSZA CENA PRODUKTU W KATEGORII " + c);
                                System.out.println(String.format("%2.2f", menuUtilsService.maxPriceInCategory(c)));
                            }

                            break;
                        case 5:
                            menuUtilsService.sendOrdersToCustomer();
                            break;
                        case 6:
                            System.out.println("NAJWIECEJ ZAMOWIEN ZOSTANIE ZREALIZOWANYCH: " + menuUtilsService.dateMaxOrders());
                            System.out.println("NAJMNIEJ ZAMOWIEN ZOSTANIE ZREALIZOWANYCH: " + menuUtilsService.dateMinOrders());
                            break;
                        case 7:
                            System.out.println("UZYTKOWNIK KTORY WYDAL NAJWIECEJ NA PRODUKTY " + menuUtilsService.mostSpendedMoneyCustomer());
                            break;
                        case 8:
                            System.out.println("CALKOWITA CENA ZA WSZYSTKIE ZAMOWIENIA PRZY " +
                                    "UWZGLEDNIENIU ZNIZEK ZA WIEK I DATE ZAMOWIENIA TO: " + "\n" +
                                    String.format("%2.2f", menuUtilsService.totalPriceOrdersWhithDiscount()) + " PLN");
                            break;
                        case 9:

                            System.out.println("PODAJ POWYZEJ ILU PRODUKTOW MA ZOSTAC SPRAWDZONY WARUNEK");
                            int scale = userDataService.getOptionsWithScale(menuUtilsService.scaleCount());
                            System.out.println("KLIENCI I ILE RAZY KUPOWALI POSZCZEGOLNE PRODUKTY: ");
                            menuUtilsService.customersAndHowManyProducts().forEach((k, v) -> System.out.println(k.getName() + " " + v));
                            System.out.println("ILOSC KLIENTOW KTORZY KUPILI PRODUKTY POWYZEJ " + scale +
                                    " PRODUKTOW TO: " + menuUtilsService.howManyClientsOrdersCount((long) scale));
                            break;
                        case 10:
                            System.out.println("NAJWIECEJ PRODUKTOW ZAMOWIONO Z KATEGORII: " + menuUtilsService.mostOftenChoosenCategory());
                            break;
                        case 11:
                            menuUtilsService.howManyProductsInTheMonth().forEach((k, v) -> System.out.println(k + " " + v));

                            break;
                        case 12:
                            menuUtilsService.mostPopularCategryInMonth().forEach((k, v) -> System.out.println(k + " " + v));
                            break;
                        case 13:
                            return;
                    }
                } catch (MyException e) {
                    System.err.println(e.getExceptionInfo().getMessage());
                }
            }
        } catch (MyException e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.SERVICE, "MENAGE METHOD EXCEPTION " + e.getExceptionInfo().getMessage());
        }
    }
}
