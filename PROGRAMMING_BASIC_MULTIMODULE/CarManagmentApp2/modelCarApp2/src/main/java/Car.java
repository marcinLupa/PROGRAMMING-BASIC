import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Car {

    private String model;
    private BigDecimal price;
    private int mileage;
    private Engine engine;
    private CarBody carBody;
    private Wheel wheels;

      public void setModel(String model) {
        if (model == null) {
            throw new NullPointerException("MODEL IS NULL EXCEPTION");
        }
        this.model = model.toUpperCase().replaceAll("[^W\\s]", "");
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("VALUE IS UNDER 0 EXCEPTION");
        } else {
            this.price = price;
        }
    }

    public void setMileage(int mileage) {
        if (mileage < 0) {
            throw new IllegalArgumentException("MILEAGE IS UNDER 0 EXCEPTION");
        } else {
            this.mileage = mileage;
        }

    }


    @Override
    public String toString() {
        return "\n" + "MARKA POJAZDU: " + model +
                ", CENA PODSTAWOWA: " + price +
                ", PRZEBIEG: " + mileage + "\n" +
                ", SILNIK: " + engine + "\n" +
                ", NADWOZIE: " + carBody + "\n" +
                ", KOŁA: " + wheels + "\n" +
                "-----------------------------------------------------";
    }

}
