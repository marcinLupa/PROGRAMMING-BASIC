package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Customer {
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private BigDecimal budget;


}
