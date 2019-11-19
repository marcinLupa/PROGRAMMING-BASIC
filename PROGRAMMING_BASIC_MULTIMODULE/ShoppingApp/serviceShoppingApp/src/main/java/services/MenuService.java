package services;


import exceptions.ExceptionCode;
import exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.utils.Category;
import model.Customer;
import model.Product;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@Builder

public class MenuService {

    public void Manage(String folderName) {

        final DataService dataService = new DataService(folderName);
        try {
            while (true) {
                System.out.println("Wybierz opcje:" + "\n" +
                        "1 - WYSWIETL ZESTAWIENIE UZYTKOWNIK, PRODUKTY, ILOSC ZAKUPIONYCH PRODUKTOW" + "\n" +
                        "2 - KLIENT KTORY WYDAL NAJWIECEJ" + "\n" +
                        "3 - KLIENT KTORY WYDAL NAJWIECEJ W DANEJ KATEGORII" + "\n" +
                        "4 - WIEK KLIENTOW I NAJCZESCIEJ WYBIERANE KATEGORIE" + "\n" +
                        "5 - SREDNIA CENA PRODUKTOW W DANEJ KATEGORII" + "\n" +
                        "6 - NAJTANSZY ORAZ NAJDROZSZY PRODUKT W DANEJ KATEGORII" + "\n" +
                        "7 - KLIENCI KTORZY NAJCZESCIEJ WYBIERALI PRODUKT DANEJ KATEGORII" + "\n" +
                        "8 - CZY KLIENT POSIADA ODPOWIEDNI BUDZET(ZWRACA LISTE DLUZNIKOW " +
                        "WRAZ Z ICH DLUGIEM DO UREGULOWANIA" + "\n" +
                        "9 - WYJSCIE Z PROGRAMU");
                    String options = new Scanner(System.in).nextLine();
                    if (!options.matches("[0-9]*")) {
                        throw new NumberFormatException("FORMAT OR RANGE OF NUMBER EXCEPTION");
                    }
                    int intOptions = Integer.valueOf(options);
                    if (intOptions < 1 || intOptions > 9) {
                        throw new NumberFormatException("OPTION OUT OF RANGE");
                    }
                    switch (intOptions) {
                        case 1:
                            System.out.println("ZESTAWIENIE: UZYTKOWNIK, PRODUKTY, ILOSC ZAKUPIONYCH PRODUKTOW");
                            dataService.getPurchases()
                                    .forEach((k, v) ->
                                            System.out.println(
                                                    k.getName() + " " + v.keySet()
                                                            .stream()
                                                            .map(Product::getName)
                                                            .collect(Collectors.toList())
                                                            + v.values()));
                            break;
                        case 2:
                            System.out.println("KLIENT KTORY WYDAL NAJWIECEJ TO: " + dataService.mostSpentMoneyCustomer().getName());
                            break;
                        case 3:
                            System.out.println("PODAJ KATEGORIE DLA KTOREJ MASZ WYZNACZYC KLIENTA CO NAJWIECEJ WYDAL" + "\n" +
                                    "LISTA KATEGORII TO: " + Arrays.toString(Category.values()));
                            String categoryFromUser = new Scanner(System.in).nextLine();

                            if (!Arrays.stream(Category.values())
                                    .map(Enum::toString)
                                    .collect(Collectors.toList())
                                    .contains(categoryFromUser)) {
                                throw new IllegalArgumentException("NO SUCH CATEGORY EXCEPTION");
                            }
                            System.out.println("KLIENT KTORY WYDAL NAJWIECEJ W KATEGORII: " + categoryFromUser + ", TO: "
                                    + dataService.mostSpentMoneyCustomerByCategory(Category.valueOf(categoryFromUser)).getName());
                            break;
                        case 4:
                            System.out.println("ZESTAWIENIE WIEK KLIENTOW I NAJCZESCIEJ WYBIERANE KATEGORIE");
                            dataService.ageAndCategory().forEach((k, v) -> System.out.println("WIEK: " + k + " " + v));
                            break;
                        case 5:
                            System.out.println("SREDNIA CENA PRODUKTOW W DANEJ KATEGORII");
                            dataService.categoryAveragePrice().forEach((k, v) -> System.out.println("KATEGORIA: " + k + " " + v + " PLN"));
                            break;
                        case 6:
                            System.out.println("NAJTANSZY PRODUKT W DANEJ KATEGORII");
                            dataService.minCategoryPrices().forEach((k, v) -> System.out.println("KATEGORIA: " + k + " " + v + " PLN"));
                            System.out.println("\n" + "NAJTANSZY PRODUKT W DANEJ KATEGORII");
                            dataService.maxCategoryPrices().forEach((k, v) -> System.out.println("KATEGORIA: " + k + " " + v + " PLN"));
                            break;
                        case 7:
                            System.out.println("KLIENCI KTORZY NAJCZESCIEJ WYBIERALI PRODUKT DANEJ KATEGORII");
                            dataService
                                    .categoryCustomer()
                                    .forEach((k, v) -> System.out.println(
                                            k + " " + v
                                                    .stream()
                                                    .map(Customer::getName)
                                                    .collect(Collectors.toList())));
                            break;
                        case 8:
                            System.out.println("LIST DLUZNIKOW WRAZ Z ICH DLUGIEM DO UREGULOWANIA");
                            Map<Customer, BigDecimal> debt = dataService.debtCalculator();
                            if (debt.isEmpty()) {
                                System.out.println("BRAK DLUZNIKOW NA LISCIE");
                            } else {
                                debt
                                        .forEach(
                                                (k, v) -> System.out.println("KLIENT: " + k.getName() + ", DLUG: " + v + " PLN"));
                            }
                            break;
                        case 9:
                            return;
                    }
            }
        } catch (MyException e) {
            throw new MyException(ExceptionCode.SERVICE, "SERVICE EXCEPTION" + e.getExceptionInfo().getMessage());
        }

    }
}
