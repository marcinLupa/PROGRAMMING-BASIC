package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.utils.Category;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Product {
    private String name;
    private BigDecimal price;
    private Category category;


    @Override
    public String toString() {
        return  name +
                ", CENA: " + price +" PLN"+
                ", KATEGORIA: " + category+"\n";
    }
}