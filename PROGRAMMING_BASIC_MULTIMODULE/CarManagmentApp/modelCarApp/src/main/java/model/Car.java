package model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Builder
@Data

public class Car {
    private String model;
    private BigDecimal price;
    private int mileage;
    private Colour colour;
    private List<String> equipment;

//    public model.Car(String model, BigDecimal price, int mileage, model.Colour colour, List<String> equipment) {
//        try {
//            setModel(model);
//            setPrice(price);
//            setMileage(mileage);
//            setColour(colour);
//            setEquipment(equipment);
//        } catch (Exception e) {
//            throw new exc.MyException(exc.ExceptionCode.VALIDATION, "CREATING CAR EXCEPTION" + e.getMessage());
//
//        }
//    }

    private void setModel(String model) {
        this.model = Optional.of(model)
                .filter(x -> x.matches("[A-Z ]*"))
                .orElseThrow(() -> new IllegalArgumentException("MODEL VALUE EXCEPTION"));
    }

    private void setPrice(BigDecimal price) {
        this.price = Optional.of(price)
                .filter(x -> x.compareTo(new BigDecimal(0)) >= 0)
                .orElseThrow(() -> new IllegalArgumentException("PRICE VALUE EXCEPTION"));
    }

    private void setMileage(int mileage) {
        this.mileage = Optional.of(mileage)
                .filter(x -> x >= 0)
                .orElseThrow(() -> new IllegalArgumentException("MILEAGE VALUE EXCEPTION"));
    }

    private void setColour(Colour colour) {
        this.colour = colour;
    }

    private void setEquipment(List<String> equipment) {
        this.equipment = Optional.of(equipment)
                .filter(x -> x.stream().allMatch(n -> n.matches(("[A-Z ]*"))))
                .orElseThrow(() -> new IllegalArgumentException("CAR EQUIPMENT VALUE EXCEPTION"));
    }

    @Override
    public String toString() {
        return "\n"+"Pojazd: " + model +
                ", cena: " + price +" zl "+
                ", przebieg: " + mileage + " km "+
                ", kolor: "+colour+
                ", wyposażenie: " + equipment;
    }
}
