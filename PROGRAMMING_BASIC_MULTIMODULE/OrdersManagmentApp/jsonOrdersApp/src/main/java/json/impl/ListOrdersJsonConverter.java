package json.impl;


import json.JsonConverter;
import model.Order;

import java.util.List;

public class ListOrdersJsonConverter extends JsonConverter<List<Order>> {
    public ListOrdersJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
