package json;

import model.utils.ModelListCustomerProducts;

public class JsonConverterListCustomersAndProducts extends JsonConverter<ModelListCustomerProducts> {
    public JsonConverterListCustomersAndProducts(String jsonFilename) {
        super(jsonFilename);
    }
}
