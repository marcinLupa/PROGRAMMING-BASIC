package services;

import exc.ExceptionCode;
import exc.MyException;
import json.CarsConverter;
import model.Car;
import model.SortType;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class MenuService {

    public void manager() {
        final CarService carService = new CarService(new CarsConverter(
                "C:/Users/48783/IdeaProjects/DUZE PROGRAMY/PROGRAMMING_BASIC_MULTIMODULE/CarManagmentApp/jsonCarApp/src/main/resources/cars.json")
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.JSON, "JSON EXC")));
        final UserDataService userDataService = new UserDataService();
        while (true) {
            try {
                System.out.println("WYBIERZ OPCJĘ" + "\n" +
                        "1-Wyswietl wszystkie pojazdy:" + "\n" +
                        "2-Sortowanie po typie" + "\n" +
                        "3-Usuwanie aut z listy powyżej określonego przebiegu" + "\n" +
                        "4-Zestawienie kolor i ilość aut w poszczególnych kolorach" + "\n" +
                        "5-Zestawienie MARKA+SAMOCHOD O NAJWYZSZEJ CENIE Z DANEJ MARKI" + "\n" +
                        "6-Statystki" + "\n" +
                        "7-Najdroższy pojazd to: " + "\n" +
                        "8-Czy wszystkie pojazdy posiadaja wskazany komponent" + "\n" +
                        "9-Sortowanie alfabetyczne komponentow pojazdu" + "\n" +
                        "10-Zestawienie KOMPONENT + LISTA pojazdow posiadajacych dany komponent" + "\n" +
                        "11-Pojazd z podanego przez uzytkownika przedzialu cenowego" + "\n" +
                        "12-WYJSCIE");

                int fromUser = userDataService.getInt();


                switch (fromUser) {
                    case 1:
                        System.out.println("Wszystkie pojazdy z listy: ");
                        carService.getCars().forEach(System.out::println);
                        break;
                    case 2:

                        System.out.println("Podaj typ do sortowania(MARKA, CENA,KOLOR, PRZEBIEG, ILOSC_SKLADNIKOW");
                        SortType type = userDataService.getSortType();

                        switch (type) {
                            case MARK:
                                carService.sortedCars(SortType.MARK).forEach(System.out::println);
                                break;
                            case PRICE:
                                carService.sortedCars(SortType.PRICE).forEach(System.out::println);
                                break;
                            case MILLEAGE:
                                carService.sortedCars(SortType.MILLEAGE).forEach(System.out::println);
                                break;
                            case COMPONNENTS:
                                carService.sortedCars(SortType.COMPONNENTS).forEach(System.out::println);
                                break;
                            case COLOUR:
                                carService.sortedCars(SortType.COLOUR).forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.println("Podaj przebieg powyżej, któego mają zostać usunięte autua z listy: ");
                        carService.getWithMileageLessThan(userDataService.getIntMilleage())
                                .forEach(System.out::println);
                        break;
                    case 4:
                        System.out.println("Marka oraz Przebieg, posortowana alfabetycznie");
                        carService.rosterColourOccurrences().forEach((k, v) -> System.out.println(k + " " + v));
                        break;
                    case 5:
                        carService.rosterModelCar().forEach((k, v) -> System.out.println(k + " " + v));
                        break;
                    case 6:
                        carService.statistic();
                        break;
                    case 7:
                        System.out.println("najdroższy samochód to:");
                        System.out.println(carService.mostExpensiveCar());
                        break;
                    case 8:
                        System.out.println(carService.isComponent() ? "TAK" : "NIE");
                        break;
                    case 9:
                        System.out.println("Sortowanie komponentów alfabetyczne: ");
                        carService.alphabheticComponents().forEach(System.out::println);
                        break;
                    case 10:
                        System.out.println("Komponenty i lista (marek)samochodów, które zawieraja te komponenty");
                        carService.rosterComponentCarList()//Wyswietlane tylko marki dla wygody ogladajacego
                                .forEach((k, v) -> System.out.println(k + " " + v
                                        .stream()
                                        .map(Car::getModel)
                                        .collect(Collectors.toList())));

                        break;
                    case 11:
                        System.out.println("PODAJ CENE MINIMALNA");
                        BigDecimal minFromUser = userDataService.getPrice();
                        System.out.println("PODAJ CENE MAKSYMALNA");
                        BigDecimal maxFromUser = userDataService.getPrice();
                        System.out.println(carService.getCarPriceFromUser(minFromUser, maxFromUser));
                        break;
                    case 12:
                        userDataService.close();
                        return;
                }

            } catch (MyException e) {
                e.printStackTrace();
                throw new MyException(ExceptionCode.DATA, "MANAGE METHOD EXCEPTION " + e.getExceptionInfo().getMessage());
            }
        }
    }
}
