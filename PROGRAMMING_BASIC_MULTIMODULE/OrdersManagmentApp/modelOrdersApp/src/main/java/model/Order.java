package model;

import exceptions.ExceptionCode;
import exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data

public class Order {
    private Customer customer;
    private Product product;
    private Integer quantity;
    private LocalDate orderDate;

    public Order(OrderBuilder builder) {
        this.customer = builder.customer;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.orderDate = builder.orderDate;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Customer customer;
        private Product product;
        private Integer quantity;
        private LocalDate orderDate;

        public OrderBuilder customer(Customer customer) {
            try {
                if (customer == null) {
                    throw new MyException(ExceptionCode.BUILDER, "CUSTOMER IS NULL ");
                }
                this.customer = customer;
                return this;
            } catch (MyException e) {
                throw new MyException(ExceptionCode.BUILDER, "ORDER BUILDER CUSTOMER EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }

        public OrderBuilder product(Product product) {
            try {
                if (product == null) {
                    throw new MyException(ExceptionCode.BUILDER, "PRODUCT IS NULL ");
                }
                this.product = product;
                return this;
            } catch (MyException e) {
                throw new MyException(ExceptionCode.BUILDER, "ORDER BUILDER CUSTOMER EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }

        public OrderBuilder orderDate(LocalDate orderDate) {
            try {
                if (orderDate == null) {
                    throw new MyException(ExceptionCode.BUILDER, "ORDER DATE IS NULL ");
                }
                if (orderDate.isBefore(LocalDate.now())) {
                    throw new MyException(ExceptionCode.BUILDER, "ORDER DATE IS FROM PAST ");
                }
                this.orderDate = orderDate;
                return this;
            } catch (MyException e) {
                throw new MyException(ExceptionCode.BUILDER, "ORDER BUILDER CUSTOMER EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }

        public OrderBuilder quantity(Integer quantity) {
            try {
                if (quantity == null) {
                    throw new MyException(ExceptionCode.BUILDER, "PRODUCT IS NULL ");
                }
                if (quantity < 0) {
                    throw new MyException(ExceptionCode.BUILDER, "QUANTITY UNDER ZERO");
                }
                this.quantity = quantity;
                return this;
            } catch (MyException e) {
                throw new MyException(ExceptionCode.BUILDER, "ORDER BUILDER CUSTOMER EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }

        public Order build() {
            return new Order(this);
        }
    }

    @Override
    public String toString() {
        return
                " UZYTKOWNIK: " + customer.getName() + ", " +
                        customer.getSurname() + ", " +
                        customer.getEmail() + ", WIEK: " +
                        customer.getAge() +
                        " PRODUKT: " + product.getName() + ", CENA: " +
                        product.getPrice() + ", KATEGORIA: " +
                        product.getCategory() +

                        ", ILOSC PRODUKTOW: " + quantity +
                        ", DATA REALIZACJI ZAMOWIENIA: " + orderDate;
    }
}
