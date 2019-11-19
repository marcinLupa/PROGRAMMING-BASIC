package utils;
import exceptions.ExceptionCode;
import exceptions.MyException;
import org.eclipse.collections.impl.collector.Collectors2;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.Category;
import model.Customer;
import model.Order;
import model.Product;
import services.Orders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
@NoArgsConstructor
@Data
public class MenuUtilsService {
    private Orders orders;
    private final UserDataService userDataService = new UserDataService();

    public MenuUtilsService(String filename) {
        this.orders = new Orders(filename);
    }

    public List<Order> ordersFromUser() {
        List<Order> ordersChanged=orders.getOrders();
        System.out.println("CZY NA PEWNO CHCESZ WPROWADZIC DANE NOWEGO ZAMOWIENIA?");
        if (!userDataService.getYesOrNo()) {
            return orders.getOrders();
        } else {
            System.out.println("PODAJ IMIE UZYTKOWNIKA");
            String name = userDataService.getString();
            System.out.println("PODAJ NAZWISKO UZYTKOWNIKA");
            String surname = userDataService.getString();
            System.out.println("PODAJ WIEK UZYTKOWNIKA");
            int age = userDataService.getAge();
            System.out.println("PODAJ E-MAIL UZYTKOWNIKA");
            String email = userDataService.getEmail();
            System.out.println("PODAJ NAZWE PRODUKTU");
            String productName = userDataService.getString();
            System.out.println("PODAJ KATEGORIE PRODUKTU (" + Arrays.toString(Category.values()) + ")");
            Category category = userDataService.getCategory();
            System.out.println("PODAJ CENE PRODUKTU");
            BigDecimal price = userDataService.getPrice();
            System.out.println("PODAJ ILOSC PRODUKTOW");
            int quantity = userDataService.getInts();
            System.out.println("PODAJ DATE REALIZACJI ZAMOWIENIA PRODUKTU");
            LocalDate orderDate = userDataService.getLocalDate();


            ordersChanged.add(Order
                    .builder()
                    .customer(Customer
                            .builder()
                            .name(name)
                            .surname(surname)
                            .email(email)
                            .age(age)
                            .build())
                    .product(Product
                            .builder()
                            .name(productName)
                            .category(category)
                            .price(price)
                            .build())
                    .quantity(quantity)
                    .orderDate(orderDate)
                    .build());
        }
        return ordersChanged;
    }

    public BigDecimal avgPriceInDates() {
        System.out.println("PODAJ DATE OD: ");
        LocalDate date1 = userDataService.getLocalDate();
        System.out.println("PODAJ DATĘ DO: ");
        LocalDate date2 = userDataService.getLocalDate();
        System.out.println("SREDNIA CENA ZAMOWIEN W CZASIE OD: " + date1 + ", DO: " + date2);
        return orders.getOrders()
                .stream()
                .filter(x -> x.getOrderDate()
                        .isAfter(date1) && x.getOrderDate()
                        .isBefore(date2))
                .collect(Collectors2
                        .summarizingBigDecimal(x -> x
                                .getProduct()
                                .getPrice()))
                .getAverage();
    }

    public BigDecimal maxPriceInCategory(Category category) {
        return orders.getOrders()
                .stream()
                .filter(x -> x.getProduct().getCategory().equals(category))
                .collect(Collectors2
                        .summarizingBigDecimal(x -> x
                                .getProduct()
                                .getPrice()))
                .getMax();
    }

    public void sendOrdersToCustomer() {
        orders.getOrders()
                .stream()
                .collect(Collectors
                        .groupingBy(Order::getCustomer,
                                Collectors
                                        .mapping(Order::getProduct, Collectors.toList()))).forEach((k, v) -> {
            try {
                new EmailService().sendAsHtml(
                        /*k.getEmail()*/"marcin.lupa1987@gmail.com",
                        "Lista zamowien uzytkownika: " + k.getName() + " " + k.getSurname(),
                        "<body>" + "<h1> TWOJE PRODUKTY TO: </h1>" + v);
            } catch (Exception e) {
                throw new MyException(ExceptionCode.EMAIL, "SENDING EMAIL EXCEPTION" + e.getMessage());
            }
        });
    }

    public List<LocalDate> dateMaxOrders() {
        orders.getOrders()
                .stream()
                .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.counting())).forEach((k, v) -> System.out.println(k + " " + v));

        return orders.getOrders()
                .stream()
                .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(x -> x.getValue().equals(orders.getOrders()
                        .stream()
                        .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(Comparator.comparingLong(Map.Entry::getValue))
                        .map(Map.Entry::getValue)
                        .get()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    public List<LocalDate> dateMinOrders() {
        return orders.getOrders()
                .stream()
                .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(x -> x.getValue().equals(orders.getOrders()
                        .stream()
                        .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .min(Comparator.comparingLong(Map.Entry::getValue))
                        .map(Map.Entry::getValue)
                        .get()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    public Customer mostSpendedMoneyCustomer() {
        return orders.getOrders()
                .stream()
                .collect(Collectors
                        .groupingBy(
                                Order::getCustomer,
                                Collectors.mapping(x -> x.getProduct().getPrice().multiply(new BigDecimal(x.getQuantity())), Collectors.toList())))
                .entrySet()
                .stream()
                .collect(Collectors
                        .toMap(
                                Map.Entry::getKey,
                                v -> v.getValue()
                                        .stream()
                                        .collect(Collectors2
                                                .summingBigDecimal(x -> x))))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "BIG DECIMAL MATH EXCEPTION"));
    }

    public BigDecimal totalPriceOrdersWhithDiscount() {
        final int DAYS_FROM_ACTUAL_DATE = 2;
        final BigDecimal DICOUNT_FROM_DATE = new BigDecimal(0.02);
        final int AGE_TO_DISCOUNT = 25;
        final BigDecimal DICOUNT_FROM_AGE = new BigDecimal(0.03);

        List<Order> ageDiscountList = new ArrayList<>();
        orders.getOrders().forEach(o -> {
            if (o.getCustomer().getAge().compareTo(AGE_TO_DISCOUNT) < 0) {
                ageDiscountList.add(new Order(o.getCustomer(),
                        new Product(
                                o.getProduct().getName(),
                                o.getProduct().getPrice().subtract(o.getProduct().getPrice().multiply(DICOUNT_FROM_AGE)),
                                o.getProduct().getCategory()),
                        o.getQuantity(),
                        o.getOrderDate()));
            } else {
                ageDiscountList.add(o);
            }
        });

        List<Order> dateDiscountList = new ArrayList<>();

        orders.getOrders().forEach(o -> {
            if (o.getOrderDate()
                    .isBefore(LocalDate.now()
                            .plusDays(DAYS_FROM_ACTUAL_DATE))) {
                dateDiscountList.add(new Order(o.getCustomer(),
                        new Product(
                                o.getProduct().getName(),
                                o.getProduct().getPrice().subtract(o.getProduct().getPrice().multiply(DICOUNT_FROM_DATE)),
                                o.getProduct().getCategory()),
                        o.getQuantity(),
                        o.getOrderDate()));
            } else {
                dateDiscountList.add(o);
            }
        });
        List<Order> newOrderList = new ArrayList<>();
        for (int i = 0; i < orders.getOrders().size(); i++) {
            if (dateDiscountList.get(i).getProduct().getPrice()
                    .compareTo(ageDiscountList.get(i).getProduct().getPrice()) < 0) {
                newOrderList.add(dateDiscountList.get(i));
            } else if (dateDiscountList.get(i).getProduct().getPrice()
                    .compareTo(ageDiscountList.get(i).getProduct().getPrice()) > 0) {
                newOrderList.add(ageDiscountList.get(i));
            } else {
                newOrderList.add(orders.getOrders().get(i));
            }
        }

        return newOrderList
                .stream()
                .collect(Collectors2.summingBigDecimal(x -> x.getProduct().getPrice()));
    }

    public int howManyClientsOrdersCount(Long counts) {

        return (int) customersAndHowManyProducts().entrySet()
                .stream()
                .filter(x -> x.getValue()
                        .stream()
                        .filter(y -> y > counts)
                        .collect(Collectors.toList()).size() > 0)
                .count();
    }

    public Map<Customer, List<Long>> customersAndHowManyProducts() {
        return orders.getOrders().stream()
                .collect(Collectors
                        .groupingBy(Order::getCustomer,
                                Collectors
                                        .groupingBy(Order::getProduct,
                                                Collectors.summingLong(Order::getQuantity)))).entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> new ArrayList<>(v.getValue().values())));
    }

    public int scaleCount() {
        return customersAndHowManyProducts()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .max(Comparator.comparingLong(y -> y))
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "")).intValue();
    }

    public Category mostOftenChoosenCategory() {
        return orders.getOrders()
                .stream()
                .collect(Collectors.groupingBy(x -> x.getProduct().getCategory(),
                        Collectors.mapping(Order::getQuantity, Collectors.summingInt(x -> x))))
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CATEGORY MAX COUNT EXCEPTION"));
    }

    public Map<String, Integer> howManyProductsInTheMonth() {

        return orders.getOrders()
                .stream()
                .collect(Collectors.groupingBy(x -> YearMonth.from(x.getOrderDate()).getMonth().toString(),
                        Collectors.mapping(Order::getQuantity,
                                Collectors.summingInt(x -> x))))
                .entrySet()
                .stream()
                .sorted(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                        .reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new));
    }

    public Map<String, Category> mostPopularCategryInMonth() {
        return orders.getOrders()
                .stream()
                .collect(Collectors.groupingBy(x -> YearMonth.from(x.getOrderDate()).getMonth().toString(),
                        Collectors.groupingBy(k -> k.getProduct().getCategory(), Collectors.mapping(x -> x.getProduct().getCategory(), Collectors.counting()))))
                .entrySet()
                .stream()
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                v -> v.getValue()
                                        .entrySet()
                                        .stream()
                                        .max(Comparator.comparingLong(Map.Entry::getValue))
                                        .map(Map.Entry::getKey)
                                        .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "COMPARING LONG EXCEPTION"))));
    }
}
