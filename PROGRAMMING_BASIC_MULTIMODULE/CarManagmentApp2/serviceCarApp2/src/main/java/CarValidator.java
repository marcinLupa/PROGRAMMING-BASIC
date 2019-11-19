

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarValidator implements Validator<Car> {

    private Map<String, String> errors = new HashMap<>();

    @Override
    public boolean hasErrors() {
        return errors.size() > 0;
    }

    @Override
    public Map<String, String> getErrors() {
        return errors;
    }

    @Override
    public void validate(Car car) {
        final String nameRegex = "[A-Z0-9 _]*";
        try {
            if (!car.getModel().matches(nameRegex)) {
                errors.put("model", "Model name is not correct: " + car.getModel());
                throw new IllegalArgumentException("Model name is not correct: ");
            }

            if (car.getPrice().compareTo(new BigDecimal(0)) < 0) {
                errors.put("price", "Price is not correct: " + car.getPrice());
                throw new IllegalArgumentException("Price is not correct: ");
            }

            if (car.getMileage() < 0) {
                errors.put("mileage", "Mileage is not correct: " + car.getMileage());
                throw new IllegalArgumentException("Mileage is not correct: ");
            }
            if (car.getCarBody().getComponents()
                    .stream()
                    .filter(x -> x.toUpperCase().matches(nameRegex))
                    .count()
                    != car.getCarBody().getComponents().size()) {

                errors.put("components", "Components are not correct: " + car.getCarBody().getComponents());
                throw new IllegalArgumentException("Components are not correct: ");
            }
            if (car.getEngine().getPower().compareTo(new BigDecimal(0)) < 0) {
                errors.put("power", "Power is not correct: " + car.getEngine().getPower());
                throw new IllegalArgumentException("Power is not correct: ");
            }
            if (!car.getWheels().getModel().matches(nameRegex)) {
                errors.put("Wheel model", "Wheel model name is not correct: " + car.getWheels().getModel());
                throw new IllegalArgumentException("Wheel model name is not correct: ");
            }
            if (car.getWheels().getSize() < 0) {
                errors.put("Size", "Size is not correct: " + car.getWheels().getSize());
                throw new IllegalArgumentException("Size is not correct: ");

            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.VALIDATION, "VALIDATION EXCEPTION");
        }
    }
}
