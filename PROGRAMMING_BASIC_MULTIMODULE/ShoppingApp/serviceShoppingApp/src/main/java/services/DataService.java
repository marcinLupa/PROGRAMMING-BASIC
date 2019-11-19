package services;


import exceptions.ExceptionCode;
import exceptions.MyException;
import json.JsonConverterListCustomersAndProducts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.utils.Category;
import model.Customer;
import model.Product;
import model.utils.ModelCustomerAndProducts;
import model.utils.ModelListCustomerProducts;


import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;


@AllArgsConstructor
@Data
@Builder
public class DataService {
    private Map<Customer, Map<Product, Long>> purchases;

    public DataService(String folderName) {

        this.purchases = gettingPurchases(folderName);
    }

    private static ModelListCustomerProducts fromFileModelList(String folderName) {
        try {
            List<ModelCustomerAndProducts> joiningConvertedLists = new ArrayList<>();

            Arrays
                    .stream(Objects.requireNonNull(new File(folderName)
                            .listFiles()))
                    .map(File::getName)
                    .collect(Collectors.toList())
                    .stream()
                    .map(x -> new JsonConverterListCustomersAndProducts(folderName+x)
                            .fromJson()
                            .orElseThrow(() -> new NoSuchElementException("FILES LIST PROBLEM")))
                    .collect(Collectors.toList())
                    .forEach(x -> joiningConvertedLists.addAll(x.getCustomersAndProducts()));

            return ModelListCustomerProducts.builder().customersAndProducts(joiningConvertedLists).build();

        } catch (MyException e) {
            throw new MyException(ExceptionCode.DATA, "FILES LIST EXCEPTION" + e.getMessage());
        }
    }

    private Map<Customer, Map<Product, Long>> gettingPurchases(String folderName) {
        //try {
            Map<Customer, List<Product>> CustomersWithProducts = new HashMap<>();

           fromFileModelList(folderName
            ).getCustomersAndProducts()
                    .stream()
                    .map(ModelCustomerAndProducts::getCustomer)
                    .collect(Collectors.toSet()).forEach(c -> CustomersWithProducts.put(c, fromFileModelList(folderName)
                    .getCustomersAndProducts()
                    .stream()
                    .filter(x -> x.getCustomer().equals(c))
                    .map(ModelCustomerAndProducts::getProducts)
                    .collect(Collectors.toList()).stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList())));

            return CustomersWithProducts
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            v -> v.getValue()
                                    .stream()
                                    .collect(Collectors.groupingBy(x -> x, Collectors.counting()))));
        //} catch (Exception e) {
        //    throw new exceptions.exceptions.MyException(exceptions.exceptions.ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
       // }


    }

    public Customer mostSpentMoneyCustomer() {
        try {
            return purchases
                    .entrySet()
                    .stream()
                    .max(comparing(x -> x.getValue()
                            .entrySet()
                            .stream()
                            .map(y -> y.getKey().getPrice())
                            .reduce(BigDecimal::add).orElseThrow(() -> new NoSuchElementException("PRICE IS NOT CORERECT"))))
                    .orElseThrow(() -> new NoSuchElementException("CUSTOMER OUT OF BASE")).getKey();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }
    }

    public Customer mostSpentMoneyCustomerByCategory(Category option) {
        try {
            return purchases
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            v -> v.getValue()
                                    .entrySet()
                                    .stream()
                                    .filter(x -> x.getKey().getCategory()
                                            .equals(option))
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            Map.Entry::getValue
                                    ))))
                    .entrySet()
                    .stream()
                    .max(comparing(x -> x.getValue()
                            .entrySet()
                            .stream()
                            .map(y -> y.getKey().getPrice())
                            .reduce(BigDecimal::add).orElse(new BigDecimal(0))))
                    .orElseThrow(() -> new NoSuchElementException("CUSTOMER OUT OF BASE")).getKey();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }
    }

    public Map<Integer, List<Category>> ageAndCategory() {
        try {
            return purchases
                    .keySet()
                    .stream()
                    .map(Customer::getAge)
                    .collect(Collectors.toSet())
                    .stream()
                    .collect(Collectors
                            .toMap(
                                    k -> k,
                                    v -> purchases
                                            .entrySet()
                                            .stream()
                                            .filter(x -> x.getKey().getAge() == v)
                                            .flatMap(x -> x.getValue()
                                                    .keySet()
                                                    .stream()
                                                    .map(Product::getCategory))
                                            .collect(Collectors.toList())));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }
    }

    public Map<Category, BigDecimal> categoryAveragePrice() {
        try {
            return purchases
                    .values()
                    .stream()
                    .map(x -> x.keySet()
                            .stream()
                            .map(Product::getCategory)
                            .collect(Collectors.toList()))
                    .flatMap(List::stream)
                    .collect(Collectors.toSet())
                    .stream()
                    .collect(Collectors.toMap(
                            k -> k,
                            v -> purchases
                                    .values()
                                    .stream()
                                    .flatMap(x -> x.keySet().stream())
                                    .filter(x -> x.getCategory().equals(v))
                                    .map(Product::getPrice)
                                    .reduce(BigDecimal::add)
                                    .map(x -> x.divide(new BigDecimal(purchases
                                            .values()
                                            .stream()
                                            .flatMap(z -> z.keySet().stream())
                                            .filter(z -> z.getCategory().equals(v)).count()), RoundingMode.UP))
                                    .orElseThrow(() -> new NoSuchElementException("DIVIDE EXCEPTION"))));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }
    }

    public Map<Category, BigDecimal> maxCategoryPrices() {
        try {
            return purchases
                    .values()
                    .stream()
                    .map(x -> x.keySet()
                            .stream()
                            .map(Product::getCategory)
                            .collect(Collectors.toList()))
                    .flatMap(List::stream)
                    .collect(Collectors.toSet())
                    .stream()
                    .collect(Collectors.toMap(
                            k -> k,
                            v -> purchases
                                    .values()
                                    .stream()
                                    .flatMap(x -> x.keySet().stream())
                                    .filter(x -> x.getCategory().equals(v))
                                    .map(Product::getPrice)
                                    .max(comparing(Function.identity()))
                                    .orElseThrow(() -> new NoSuchElementException("DIVIDE EXCEPTION"))));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }
    }

    public Map<Category, BigDecimal> minCategoryPrices() {
        try{
        return purchases
                .values()
                .stream()
                .map(x -> x.keySet()
                        .stream()
                        .map(Product::getCategory)
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> purchases
                                .values()
                                .stream()
                                .flatMap(x -> x.keySet().stream())
                                .filter(x -> x.getCategory().equals(v))
                                .map(Product::getPrice)
                                .min(comparing(Function.identity()))
                                .orElseThrow(() -> new NoSuchElementException("DIVIDE EXCEPTION"))));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }
    }

    private Map<Category, List<Customer>> categoryCustomerUtil(Category toStreamCategory) {
        try {
            Map<Customer, Map<Category, Long>> occurrencesOfCategoryEachCustomer = purchases
                    .entrySet()
                    .stream()
                    .collect(Collectors
                            .toMap(
                                    Map.Entry::getKey,
                                    v -> v.getValue()
                                            .keySet()
                                            .stream()
                                            .map(Product::getCategory)
                                            .collect(Collectors.toList())))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            x1 -> x1.getValue()
                                    .stream()
                                    .collect(Collectors.groupingBy(z -> z, Collectors.counting()))));

            Map<Customer, Map<Category, Long>> filtredPurchasesByCategory = occurrencesOfCategoryEachCustomer
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            v -> v.getValue()
                                    .entrySet()
                                    .stream()
                                    .filter(x -> x.getKey().equals(toStreamCategory))
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            Map.Entry::getValue))));

            Map<Customer, Map<Category, Long>> filtredCustomersCategoryAndOccurances = filtredPurchasesByCategory.entrySet()
                    .stream()
                    .filter(x -> x.getValue().values().contains(
                            filtredPurchasesByCategory.entrySet()
                                    .stream()
                                    .flatMap(m -> m.getValue().values().stream())
                                    .max(comparing(m -> m))
                                    .orElseThrow(NullPointerException::new)))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));

            return filtredCustomersCategoryAndOccurances
                    .entrySet()
                    .stream()
                    .collect(
                            Collectors
                                    .groupingBy(
                                            x -> x
                                                    .getValue()
                                                    .entrySet()
                                                    .stream()
                                                    .map(Map.Entry::getKey)
                                                    .findFirst().orElseThrow(IllegalArgumentException::new),
                                            Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }
    }

    public Map<Category, List<Customer>> categoryCustomer() {
        try {
            Map<Category, List<Customer>> rosterCategoryCustomer = new HashMap<>();

            for (Category c : Category.values()) {
                rosterCategoryCustomer.putAll(categoryCustomerUtil(c));
            }
            return rosterCategoryCustomer;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }
    }

    public Map<Customer, BigDecimal> debtCalculator() {
        try {

            Map<Customer, Map<Product, Long>> budgetChecking = new HashMap<>(purchases);

            for (Map.Entry<Customer, Map<Product, Long>> entry : budgetChecking.entrySet()) {
                entry.getKey().setBudget(entry
                        .getKey()
                        .getBudget()
                        .subtract(entry
                                .getValue()
                                .keySet()
                                .stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal::add)
                                .orElseThrow(NullPointerException::new)));
            }

            return budgetChecking
                    .entrySet()
                    .stream()
                    .filter(k -> k.getKey().getBudget().compareTo(new BigDecimal(0)) < 0)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            v -> v.getKey().getBudget()));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "SHOPPING CLASS EXCEPTION" + e.getMessage());
        }

    }
}
