import com.google.gson.JsonParseException;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


class CarService {

   private List<Car> cars;

    CarService(List<String> filenameList) {

       this.cars = filenameList
               .stream()
               .map(CarService::getCarFromFile)
               .collect(Collectors.toList());
       validation();

   }

   private static Car getCarFromFile(String filename) {
       return new CarConverter(filename)
               .fromJson()
               .orElseThrow(() -> new JsonParseException("JSON FILE PARSING EXCEPTION"));

   }

   private void validation() {

       CarValidator carValidator = new CarValidator();

       try {
           cars.forEach(carValidator::validate);
       }catch (Exception e){
           if (carValidator.hasErrors()) {
               carValidator.getErrors().forEach((k, v) -> System.err.println(v));
               throw new MyException(ExceptionCode.VALIDATION,"VALIDATION EXCEPTION ");
           }
       }
  }


   List<Car> sortingCars(SortingOption option, SortingOption kindOfSorting) {
       try {
           if (option == null && SortingOption.DESC != kindOfSorting || kindOfSorting != SortingOption.ASC) {
               throw new NullPointerException("OPTIONS TO SORT ARE NULL OR NOT SORTING_OPTION CLASS EXCEPTION");
           }
           switch (option) {
               case COMPONENTS:
                   switch (kindOfSorting) {
                       case ASC:
                           return cars
                                   .stream()
                                   .sorted(Comparator.
                                           comparing(x -> x.getCarBody()
                                                   .getComponents().size())
                                   )
                                   .collect(Collectors.toList());
                       case DESC:
                           return cars
                                   .stream()
                                   .sorted(Comparator.<Car, Integer>
                                           comparing(x -> x.getCarBody()
                                           .getComponents().size())
                                           .reversed())
                                   .collect(Collectors.toList());
                   }

               case POWER_ENGINE:
                   switch (kindOfSorting) {
                       case ASC:
                           return cars
                                   .stream()
                                   .sorted(Comparator.
                                           comparing(x -> x.getEngine().getPower()))
                                   .collect(Collectors.toList());
                       case DESC:
                           return cars
                                   .stream()
                                   .sorted(Comparator.<Car, BigDecimal>
                                           comparing(x -> x.getEngine().getPower())
                                           .reversed())
                                   .collect(Collectors.toList());
                   }

               case TIRE_SIZE:
                   switch (kindOfSorting) {
                       case ASC:
                           return cars.stream()
                                   .sorted(Comparator.
                                           comparing(x -> x.getWheels().getSize()))
                                   .collect(Collectors.toList());

                       case DESC:
                           return cars.stream()
                                   .sorted(Comparator.<Car, Integer>
                                           comparing(x -> x.getWheels().getSize())
                                           .reversed())
                                   .collect(Collectors.toList());
                   }
           }
       } catch (Exception e) {
           throw new MyException(ExceptionCode.UNIDENTIFIED, "SERVICE SORTING CARS EXCEPTION" + e.getMessage());
       }
       throw new IllegalArgumentException("PARAMETR TO SORTING EXCEPTION");
   }

   Set<Car> choisingCarByBodyAndPrice(KindOfBody bodyToFind, BigDecimal minPrice, BigDecimal maxPrice) {
       try {
           return cars
                   .stream()
                   .filter(x -> x.getCarBody().getType().equals(bodyToFind)
                           && x.getPrice().compareTo(minPrice) > 0
                           && x.getPrice().compareTo(maxPrice) < 0)
                   .collect(Collectors.toSet());
       } catch (Exception e) {
           throw new MyException(ExceptionCode.UNIDENTIFIED, "SERVICE BODY-PRICE METHOD EXCEPTION" + e.getMessage());
       }
   }

   Set<Car> choisingCarByEngine(EngineType type) {
       try {
           return cars
                   .stream()
                   .filter(x -> x.getEngine().getType().equals(type))
                   .sorted(Comparator.comparing(Car::getModel))
                   .collect(Collectors.toCollection(LinkedHashSet::new));
       } catch (Exception e) {
           throw new MyException(ExceptionCode.UNIDENTIFIED, "SERVICE CAR-ENGINE METHOD EXCEPTION" + e.getMessage());
       }
   }

   String carStatistics(SortingOption option) {
       try {
           switch (option) {
               case PRICE:
                   return "MAX: " + cars
                           .stream()
                           .map(Car::getPrice)
                           .max(Comparator.comparing(Function.identity()))
                           .orElseThrow(() -> new IndexOutOfBoundsException("PRICE MAX STATISTIC EXCEPTION")) + " PLN " +
                           ", MIN: " + cars
                           .stream()
                           .map(Car::getPrice)
                           .min(Comparator.comparing(Function.identity()))
                           .orElseThrow(() -> new IndexOutOfBoundsException("PRICE MIN STATISTIC EXCEPTION")) + " PLN " +
                           ", SREDNIA: " + String.format("%2.2f", cars
                           .stream()
                           .map(Car::getPrice)
                           .reduce(BigDecimal::add)
                           .map(x -> x.divide(new BigDecimal(cars.size()), RoundingMode.UP))
                           .orElseThrow(() -> new IndexOutOfBoundsException("PRICE AVERAGE STATISTIC EXCEPTION"))) + " PLN ";
               case MILEAGE:
                   return "MAX: " + cars
                           .stream()
                           .mapToInt(Car::getMileage)
                           .summaryStatistics()
                           .getMax() + " KM " +
                           ", MIN: " + cars
                           .stream().mapToInt(Car::getMileage)
                           .summaryStatistics()
                           .getMin() + " KM " +
                           ", SREDNIA: " + String.format("%2.2f", cars
                           .stream()
                           .mapToInt(Car::getMileage)
                           .summaryStatistics()
                           .getAverage()) + " KM ";
               case POWER_ENGINE:
                   return "MAX: " + cars
                           .stream()
                           .map(x -> x.getEngine().getPower())
                           .max(Comparator.comparing(Function.identity()))
                           .orElseThrow(() -> new IndexOutOfBoundsException("POWER MAX STATISTIC EXCEPTION")) + " KW " +
                           ", MIN: " + cars
                           .stream()
                           .map(x -> x.getEngine().getPower())
                           .min(Comparator.comparing(Function.identity()))
                           .orElseThrow(() -> new IndexOutOfBoundsException("POWER MIN STATISTIC EXCEPTION")) + " KM " +
                           ", SREDNIA: " + String.format("%2.2f", cars
                           .stream()
                           .map(x -> x.getEngine().getPower())
                           .reduce(BigDecimal::add)
                           .map(x -> x.divide(new BigDecimal(cars.size()), RoundingMode.UP))
                           .orElseThrow(() -> new IndexOutOfBoundsException("POWER AVERAGE STATISTIC EXCEPTION"))) + " KM ";
           }
       } catch (Exception e) {
           throw new MyException(ExceptionCode.VALIDATION, "CARS STATISTIC EXCEPTION" + e.getMessage());
       }
       throw new IllegalArgumentException("SORTING OPTION OUT OF RANGE");
   }

   Map<Car, Integer> carsMileage() {
       try {
           return cars
                   .stream()
                   .sorted(Comparator.comparing(Car::getMileage).reversed())
                   .collect(Collectors.toMap(
                           Function.identity(),
                           Car::getMileage,
                           (v1, v2) -> v1,
                           LinkedHashMap::new
                   ));
       } catch (Exception e) {
           throw new MyException(ExceptionCode.VALIDATION, "CARS STATISTIC EXCEPTION" + e.getMessage());
       }
   }

   Map<TyreType, List<Car>> tyreTypeCars() {
       try {

           return cars
                   .stream()
                   .collect(Collectors.groupingBy(
                           k -> k.getWheels().getType()))
                   .entrySet()
                   .stream()
                   .sorted(Comparator.<Map.Entry<TyreType, List<Car>>, Integer>comparing(x -> x.getValue().size())
                           .reversed())
                   .collect(Collectors.toMap(
                           Map.Entry::getKey,
                           Map.Entry::getValue,
                           (v1, v2) -> v1,
                           LinkedHashMap::new));


       } catch (Exception e) {
           throw new MyException(ExceptionCode.VALIDATION, "CARS AND TYRE TYPES EXCEPTION" + e.getMessage());
       }
   }

   List<Car> carsComponentsFiltring(List<String> components) {
       try {
           return cars.stream()
                   .filter(x -> x.getCarBody()
                           .getComponents()
                           .containsAll(components))
                   .collect(Collectors.toList());

       } catch (Exception e) {
           throw new MyException(ExceptionCode.VALIDATION, "CARS AND TYRE TYPES EXCEPTION" + e.getMessage());
       }
   }
}

