

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class MenuService {


    public void menageCars(List<String> carsInFiles) {
       final CarService carService=new CarService(carsInFiles);
        try {
            boolean continuation = true;
            while (continuation) {
                System.out.println(
                        "WYBIERZ OPCJE" + "\n" +
                                "1 - SORTOWANIE LISTY AUT" + "\n" +
                                "2 - FILTROWANIE LISTY AUT" + "\n" +
                                "3 - STATYSTYKI DLA WYBRANEGO PARAMETRU" + "\n" +
                                "4 - ZESTAWIENIE POJAZD ORAZ PRZEBIEG POSORTOWANE MALEJACO" + "\n" +
                                "5 - ZESTAWIENIE RODZAJ OPON I LISTA POJAZDOW, POSORTOWANA MALEJACO PO ILOSCI POJAZDOW" + "\n" +
                                "6 - LISTA POJAZDOW KTORE ZAWIERAJA PODANE JAKO LISTA KOMPONENTY I DO TEGO POSORTOWANE " +
                                "ALABETYCZNIE PO NAZWIE MODELU POJAZDU" + "\n" +
                                "7 - WYJSCIE Z PROGRAMU");

                int option = new Scanner(System.in).nextInt();
                if (option < 1 || option > 7 || !String.valueOf(option).matches("[0-9]")) {
                    throw new IllegalArgumentException("WRONG FORMAT OR RANGE OF DATA FROM USER");
                }
                switch (option) {
                    case 1:
                        System.out.println(
                                "WYBIERZ RODZAJ SORTOWANIA" + "\n" +
                                        "1 - ILOSC KOMPONENTOW ROSNACO" + "\n" +
                                        "2 - MOC SILNIKA ROSNACO" + "\n" +
                                        "3 - ROZMIAR KOL ROSNACO" + "\n" +
                                        "4 - ILOSC KOMPONENTOW MALEJACO" + "\n" +
                                        "5 - MOC SILNIKA MALEJACOMALEJACO" + "\n" +
                                        "6 - ROZMIAR KOL ROSNACO");
                        int sortingOption = new Scanner(System.in).nextInt();
                        if (sortingOption < 1 || sortingOption > 6) {
                            throw new IllegalArgumentException("WRONG RANGE OF DATA FROM USER");
                        }
                        switch (sortingOption) {
                            case 1:
                                carService.sortingCars(SortingOption.COMPONENTS, SortingOption.ASC)
                                        .forEach(x -> System.out.println(
                                                x.getModel() + " ,ILOSC KOMPONENTOW: " + x.getCarBody().getComponents().size()));
                                break;
                            case 2:
                                carService.sortingCars(SortingOption.POWER_ENGINE, SortingOption.ASC).forEach(x -> System.out.println(
                                        x.getModel() + " ,MOC SILNIKA: " + x.getEngine().getPower() + " KW"));
                                break;
                            case 3:
                                carService.sortingCars(SortingOption.TIRE_SIZE, SortingOption.ASC).forEach(x -> System.out.println(
                                        x.getModel() + " ,ROZMIAR OPONY: " + x.getWheels().getSize() + "'"));
                                break;
                            case 4:
                                carService.sortingCars(SortingOption.COMPONENTS, SortingOption.DESC).forEach(x -> System.out.println(
                                        x.getModel() + " ,ILOSC KOMPONENTOW: " + x.getCarBody().getComponents().size()));
                                break;
                            case 5:
                                carService.sortingCars(SortingOption.POWER_ENGINE, SortingOption.DESC).forEach(x -> System.out.println(
                                        x.getModel() + " ,MOC SILNIKA: " + x.getEngine().getPower() + " KW"));
                                break;
                            case 6:
                                carService.sortingCars(SortingOption.TIRE_SIZE, SortingOption.DESC).forEach(x -> System.out.println(
                                        x.getModel() + " ,ROZMIAR OPONY: " + x.getWheels().getSize() + "'"));
                                break;
                        }
                        break;
                    case 2:
                        System.out.println(
                                "WYBIERZ RODZAJ FILTROWANIA" + "\n" +
                                        "1 - PO NADWOZIU(HATCHBACK, KOMBI, SEDAN) I CENIE" + "\n" +
                                        "2 - PO OKRESLONYM RODZAJU SILNIKA(DIESEL,BENZYNA,LPG) " + "\n");
                        int filtrOption = new Scanner(System.in).nextInt();
                        if (filtrOption < 1 || filtrOption > 3 || !String.valueOf(option).matches("[0-9]")) {
                            throw new IllegalArgumentException("WRONG FORMAT OR RANGE OF DATA FROM USER");
                        }
                        switch (filtrOption) {
                            case 1:
                                System.out.println("PODAJ RODZAJ NADWOZIA(HATCHBACK, KOMBI, SEDAN)");
                                String chosenByUser = new Scanner(System.in).nextLine();
                                if (!chosenByUser.matches("[A-Z ]*")) {
                                    throw new IllegalArgumentException("PARAMETR FROM USER WRNG FORMAT");
                                }
                                System.out.println("PODAJ CENE OD");
                                BigDecimal minPriceByUser = new Scanner(System.in).nextBigDecimal();
                                System.out.println("PODAJ CENE DO");
                                BigDecimal maxPriceByUser = new Scanner(System.in).nextBigDecimal();
                                if (minPriceByUser.compareTo(new BigDecimal(0)) < 0 || maxPriceByUser.compareTo(minPriceByUser) < 0) {
                                    throw new IllegalArgumentException("PRICES WRONG RANGE EXCEPTION");
                                }
                                switch (chosenByUser) {
                                    case "HATCHBACK":
                                        carService.choisingCarByBodyAndPrice(KindOfBody.HATCHBACK, minPriceByUser, maxPriceByUser)
                                                .forEach(x -> System.out.println(
                                                        x.getModel() + " ," + x.getCarBody().getType() +
                                                                " ,CENA: " + x.getPrice() + " PLN"));
                                        break;
                                    case "KOMBI":
                                        carService.choisingCarByBodyAndPrice(KindOfBody.KOMBI, minPriceByUser, maxPriceByUser)
                                                .forEach(x -> System.out.println(
                                                        x.getModel() + " ," + x.getCarBody().getType() +
                                                                " ,CENA: " + x.getPrice() + " PLN"));
                                        break;
                                    case "SEDAN":
                                        carService.choisingCarByBodyAndPrice(KindOfBody.SEDAN, minPriceByUser, maxPriceByUser)
                                                .forEach(x -> System.out.println(
                                                        x.getModel() + " ," + x.getCarBody().getType() +
                                                                " ,CENA: " + x.getPrice() + " PLN"));
                                        break;
                                }
                                break;
                            case 2:
                                System.out.println("PODAJ RODZAJ SILNIKA(DIESEL,BENZYNA,LPG)");
                                String engineByUser = new Scanner(System.in).nextLine();
                                if (!engineByUser.matches("[A-Z ]*")) {
                                    throw new IllegalArgumentException("PARAMETR FROM USER WRNG FORMAT");
                                }
                                switch (engineByUser) {
                                    case "DIESEL":
                                        carService.choisingCarByEngine(EngineType.DIESEL).forEach(x -> System.out.println(
                                                x.getModel() + " ," + x.getEngine().getType()));
                                        break;
                                    case "BENZYNA":
                                        carService.choisingCarByEngine(EngineType.GASOLINE).forEach(x -> System.out.println(
                                                x.getModel() + " ," + x.getEngine().getType()));
                                        break;
                                    case "LPG":
                                        carService.choisingCarByEngine(EngineType.LPG).forEach(x -> System.out.println(
                                                x.getModel() + " ," + x.getEngine().getType()));
                                        break;
                                }
                                break;
                        }
                        break;
                    case 3:
                        System.out.println("PODAJ PARAMETR STATYSTYK(CENA, PRZEBIEG, MOC SILNIKA)");
                        String parametrToStatistic = new Scanner(System.in).nextLine();
                        if (!parametrToStatistic.matches("[A-Z ]*")) {
                            throw new IllegalArgumentException("PARAMETR FROM USER WRNG FORMAT");
                        }
                        switch (parametrToStatistic) {
                            case "CENA":
                                System.out.println(carService.carStatistics(SortingOption.PRICE));
                                break;
                            case "PRZEBIEG":
                                System.out.println(carService.carStatistics(SortingOption.MILEAGE));
                                break;
                            case "MOC SILNIKA":
                                System.out.println(carService.carStatistics(SortingOption.POWER_ENGINE));
                                break;
                        }

                        break;
                    case 4:
                        carService.carsMileage().forEach((k, v) -> System.out.println(k.getModel() + " PRZEBIEG: " + v + " KM"));
                        break;
                    case 5:
                        carService.tyreTypeCars()
                                .forEach((k, v) -> System.out.println(
                                        k + "" + Arrays.toString(v.stream()
                                                .map(Car::getModel)
                                                .toArray())));
                        break;
                    case 6:
                        List<String> componentsFromUser = new ArrayList<>();
                        String anotherComp;
                        do {
                            System.out.println("CZY CHCESZ DODAC KOMPONENT?(TAK/NIE)");
                            anotherComp = new Scanner(System.in).nextLine();
                            if (anotherComp.equalsIgnoreCase("NIE")) {
                                break;
                            }
                            System.out.println("PODAJ NAZWE KOMPONENTU");
                            componentsFromUser.add(new Scanner(System.in).nextLine());
                        } while (anotherComp.equalsIgnoreCase("TAK"))
                                ;
                        carService.carsComponentsFiltring(componentsFromUser)
                                .forEach(x -> System.out.println(x.getModel() + "\n"
                                        + "COMPONENTS: " + x.getCarBody().getComponents()));
                        break;
                    case 7:
                        continuation = false;
                        break;
                }
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, "SERVICE CARS EXCEPTION" + e.getMessage());
        }
    }
}
