package model.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Customer;
import model.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ModelCustomerAndProducts {
    private Customer customer;
    private List<Product> products;
}

