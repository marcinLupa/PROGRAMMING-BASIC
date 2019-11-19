package model;

import exceptions.ExceptionCode;
import exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data

public class Product {
    private String name;
    private BigDecimal price;
    private Category category;

    private Product(ProductBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.category = builder.category;
    }


    public static ProductBuilder builder() {

        return new ProductBuilder();
    }

    public static class ProductBuilder {

        private String name;
        private BigDecimal price;
        private Category category;

        public ProductBuilder name(String name) {
            try {
                if(name==null){
                    throw new MyException(ExceptionCode.BUILDER, "NAME IS NULL ");
                }
                if(!name.matches("[A-Z ]*")){
                    throw new MyException(ExceptionCode.BUILDER, "INCORRECT FORMAT OF STRING");
                }
                this.name=name;
                return this;

            } catch (MyException e) {
                throw new MyException(ExceptionCode.BUILDER, "PRODUCT BUILDER NAME EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }
        public ProductBuilder price(BigDecimal price) {
            try {
                if(price==null){
                    throw new MyException(ExceptionCode.BUILDER, "PRICE IS NULL ");
                }
                if(price.compareTo(BigDecimal.ZERO)<0){
                    throw new MyException(ExceptionCode.BUILDER, "UNDER ZERO VALUE");
                }
                this.price=price;
                return this;

            } catch (MyException e) {
                throw new MyException(ExceptionCode.BUILDER, "PRODUCT BUILDER PRICE EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }
        public ProductBuilder category(Category category) {
            try {
                if(category==null){
                    throw new MyException(ExceptionCode.BUILDER, "CATEGORY IS NULL ");
                }
                this.category=category;
                return this;

            } catch (MyException e) {
                throw new MyException(ExceptionCode.BUILDER, "CATEGORY BUILDER PRICE EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }
        public Product build() {

            return new Product(this);
        }
    }

    @Override
    public String toString() {
        return  name +
                ", CENA: " + price +" PLN"+
                ", KATEGORIA: " + category+"\n";
    }
}