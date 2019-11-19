package services;


import exceptions.ExceptionCode;
import exceptions.MyException;
import json.impl.ListOrdersJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.Order;
import utils.UserDataService;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Orders {
    private List<Order> orders;
    private final UserDataService userDataService = new UserDataService();


    public Orders(String filename) {
        this.orders = new ListOrdersJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.JSON, "ORDER LIST INITIALIZATION EXCEPTION"));
    }


    @Override

    public String toString() {
        return orders + "";
    }
}

