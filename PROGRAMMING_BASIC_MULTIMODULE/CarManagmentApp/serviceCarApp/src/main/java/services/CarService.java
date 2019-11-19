package services;

import exc.ExceptionCode;
import exc.MyException;
import json.CarsConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Car;
import model.Colour;
import model.SortType;
import repo.CrudRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CarService {
    private CrudRepository crudRepository;

    private List<Car> cars;


    CarService(List<Car> cars) {

        this.cars = cars;

    }


    public List<Car> sortedCars(SortType type) {

        try {
            switch (type) {
                case MARK:
                    return cars.stream()
                            .sorted(Comparator.comparing(Car::getModel))
                            .collect(Collectors.toList());
                case PRICE:
                    return cars.stream()
                            .sorted(Comparator.comparing(Car::getPrice))
                            .collect(Collectors.toList());

                case MILLEAGE:
                    return cars.stream()
                            .sorted(Comparator.comparing(Car::getMileage))
                            .collect(Collectors.toList());
                case COMPONNENTS:
                    return cars.stream()
                            .sorted(Comparator.comparing(x -> x.getEquipment().size()))
                            .collect(Collectors.toList());
            }
        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "SORTING CARS EXCEPTION ");
        }
        return cars;
    }

    public List<Car> getWithMileageLessThan(double mileageFromUser) {
        try {


            if (mileageFromUser > getCars().stream()
                    .sorted(Comparator.comparingInt(Car::getMileage))
                    .collect(Collectors.toList())
                    .get(getCars().size() - 1)
                    .getMileage()) {
                throw new MyException(ExceptionCode.DATA, "GIVEN MILEAGE IS BIGGER THEN MAX MILEAGE FROM LIST");
            }

            return getCars()
                    .stream()
                    .filter(x -> x.getMileage() > mileageFromUser)
                    .collect(Collectors.toList());

        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "MILEAFROM USER EXCEPTION " + e.getMessage());
        }

    }

    Map<Colour, Integer> rosterColourOccurrences() {

        try {
            Set<Colour> colours = cars
                    .stream()
                    .map(Car::getColour)
                    .collect(Collectors.toSet());
            return colours
                    .stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            v -> (int) cars
                                    .stream()
                                    .filter(x -> x.getColour()
                                            .equals(v))
                                    .count()));

        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "COLOUR OCCURANCES EXCEPTION");
        }
    }

    public Map<String, Car> rosterModelCar() {

        try {
            Set<String> listOfCarsModel = cars
                    .stream()
                    .map(Car::getModel)
                    .collect(Collectors.toSet());

            return listOfCarsModel
                    .stream()
                    .collect(Collectors.toMap(k -> k, v -> cars.stream()
                            .filter(x -> x.getModel().equals(v))
                            .max(Comparator.comparing(Car::getPrice))
                            .orElseThrow(() -> new NoSuchElementException("NO MAX ELEMENT IN COLLECTION"))));

        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "ROSTER MODEL AND CAR EXCEPTION " + e.getMessage());
        }
    }

    public void statistic() {
        try {
            System.out.println("NAJWYZSZA CENA POJAZDU TO " + cars
                    .stream()
                    .max(Comparator.comparing(Car::getPrice))
                    .map(Car::getPrice)
                    .orElse(new BigDecimal(0)) + " ZŁ");
            System.out.println("NAJNIZSZA CENA POJAZDU TO " + String.format("%2.2f", cars
                    .stream()
                    .min(Comparator.comparing(Car::getPrice))
                    .map(Car::getPrice)
                    .orElse(new BigDecimal(0.0))));

            System.out.println("SREDNIA CENA POJAZDU TO " + cars
                    .stream()
                    .map(Car::getPrice)
                    .reduce(BigDecimal::add)
                    .map(x -> x.divide(new BigDecimal(cars.size()), RoundingMode.HALF_DOWN))
                    .orElse(new BigDecimal(0)));
            System.out.println();

            System.out.println("NAJWYZSZY PRZEBIEG POJAZDU TO " + cars
                    .stream()
                    .max(Comparator.comparing(Car::getMileage))
                    .map(Car::getMileage)
                    .orElse(0) + " km");
            System.out.println("NAJNIZSZY PRZEBIEG POJAZDU TO " + cars
                    .stream()
                    .min(Comparator.comparing(Car::getMileage))
                    .map(Car::getMileage)
                    .orElse(0) + " km");
            System.out.println("SREDNI PRZEBIEG POJAZDU TO " + cars
                    .stream()
                    .map(Car::getMileage)
                    .collect(Collectors.averagingInt(x -> x)) + " km" + "\n");


        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "CREATING STATISTICS EXCEPTION" + e.getMessage());
        }
    }

    public List<Car> mostExpensiveCar() {
        try {
            return cars.stream()
                    .filter(x -> x.getPrice()
                            .compareTo(cars
                                    .stream()
                                    .max(Comparator.comparing(Car::getPrice))
                                    .map(Car::getPrice)
                                    .orElseThrow(() -> new NoSuchElementException("CANT FIND MAX VALUE")))
                            == 0)
                    .collect(Collectors.toList());

        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "SEARCHING FOR MOST EXPENSIVE CAR EXCEPTION " + e.getMessage());
        }
    }

    public boolean isComponent() {
        try {


            System.out.println("PODAJ SZUKANY KOMPONENT ");
            String component = new Scanner(System.in).nextLine();
            if (component == null || !component.matches("[A-Z ]*")) {
                throw new IllegalArgumentException("DATA FROM USER ILLEGAL FORMAT");
            }

            return cars.size() == cars
                    .stream()
                    .flatMap(x -> x.getEquipment()
                            .stream())
                    .filter(y -> y.equals(component))
                    .count();
        } catch (MyException e) {
            throw new MyException(ExceptionCode.DATA, "SEARCHING FOR COMPONENT EXCEPTION " + e.getMessage());
        }
    }

    public List<Car> alphabheticComponents() {
        try {
            cars
                    .forEach(x -> x
                            .getEquipment()
                            .sort(Comparator.naturalOrder()));
            return cars;
        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "SORTING ALPHABETIC COOMPONENTS EXCEPTION " + e.getMessage());
        }
    }

    public Map<String, List<Car>> rosterComponentCarList() {
        try {

            Set<String> components = new HashSet<>();

            cars.forEach(c -> components.addAll(c.getEquipment()));

            return components
                    .stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            v -> cars
                                    .stream()
                                    .filter(x -> x
                                            .getEquipment()
                                            .stream()
                                            .anyMatch(y -> y.equals(v)))
                                    .collect(Collectors.toList())
                    ));


        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "COMPONENTS COUNTING EXCEPTION " + e.getMessage());
        }
    }

    public Car getCarPriceFromUser(BigDecimal min, BigDecimal max) {
        try {
            if (min == null || min.compareTo(max) > 0 || min.compareTo(new BigDecimal(0)) < 0) {
                throw new IllegalArgumentException("BAD VALUES FROM USER");
            }
            return cars
                    .stream()
                    .filter(x -> x.getPrice().compareTo(min) > 0)
                    .filter(x -> x.getPrice().compareTo(max) < 0)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("ELEMENT OUT OF LIST"));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.VALIDATION, "PRICES FROM USER EXCEPTION " + e.getMessage());
        }
    }

    private static List<Car> getCarsFromJson(String filename) {
        try {
            return new CarsConverter("C:/Users/48783/IdeaProjects/DUZE PROGRAMY/PROGRAMMING_BASIC_MULTIMODULE/CarManagmentApp/jsonCarApp/src/main/resources/" + filename + ".json")
                    .fromJson()
                    .orElseThrow(IllegalArgumentException::new);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.JSON, "GETING CARS FROM JSON EXCEPTION " + e.getMessage());
        }

    }

    public List<Car> carsOnA() {
        System.out.println(crudRepository.findAll());

        return crudRepository.findAll()
                .stream()
                .filter(x -> x.getModel().startsWith("A"))
                .collect(Collectors.toList());
    }

}
