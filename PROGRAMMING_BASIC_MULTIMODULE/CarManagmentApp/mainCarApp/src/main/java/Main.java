import exc.MyException;
import services.MenuService;

public class Main {

    public static void main(String[] args) {

        try {
//            new json.CarsConverter("C:/Users/48783/IdeaProjects/DUZE PROGRAMY/PROGRAMMING_BASIC_MULTIMODULE/CarManagmentApp/jsonCarApp/src/main/resources/cars.json")
//            .toJson(List.of(model.Car
//                    .builder()
//                    .colour(model.Colour.BIALY)
//                    .equipment(List.of( "ABS",
//                            "ESR",
//                            "REFLEKTOR LED",
//                            "LAKIER METALIK",
//                            "BOSE SYSTEM",
//                            "PAKIET ZIMOWY",
//                            "TV TUNER",
//                            "SPORTOWE ZAWIESZENIE",
//                            "RADIO",
//                            "HAK"))
//                    .mileage(5000)
//                    .model("AUDI")
//                    .price(new BigDecimal(89000))
//                    .build(),
//                    model.Car
//                            .builder()
//                            .colour(model.Colour.CZARNY)
//                            .equipment(List.of( "ABS",
//                                    "ESR",
//                                    "HAK"))
//                            .mileage(250000)
//                            .model("DACIA")
//                            .price(new BigDecimal(20000))
//                            .build()
//            ,model.Car
//                            .builder()
//                            .colour(model.Colour.NIEBIESKI)
//                            .equipment(List.of( "ABS",
//                                    "ESR",
//                                    "REFLEKTOR LED",
//                                    "BOSE SYSTEM",
//                                    "PAKIET ZIMOWY",
//                                    "SPORTOWE ZAWIESZENIE",
//                                    "RADIO",
//                                    "HAK"))
//                            .mileage(25000)
//                            .model("BMW")
//                            .price(new BigDecimal(250000))
//                            .build(),
//                    model.Car
//                            .builder()
//                            .colour(model.Colour.CZARNY)
//                            .equipment(List.of( "ABS",
//                                    "ESR",
//                                    "REFLEKTOR LED",
//                                    "LAKIER METALIK",
//                                    "BOSE SYSTEM",
//                                    "RADIO",
//                                    "HAK"))
//                            .mileage(20000)
//                            .model("NISSAN")
//                            .price(new BigDecimal(50000))
//                            .build(),
//                    model.Car
//                            .builder()
//                            .colour(model.Colour.BIALY)
//                            .equipment(List.of( "ABS"
//                                 ))
//                            .mileage(10000)
//                            .model("FIAT")
//                            .price(new BigDecimal(12500))
//                            .build(),
//                    model.Car
//                            .builder()
//                            .colour(model.Colour.CZARNY)
//                            .equipment(List.of( "ABS",
//                                    "ESR",
//                                    "REFLEKTOR LED",
//                                    "LAKIER METALIK",
//                                    "HAK"))
//                            .mileage(150000)
//                            .model("AUDI")
//                            .price(new BigDecimal(35000))
//                            .build()
//                    ));
          new MenuService().manager();
        } catch (MyException e) {
            System.err.println("EXCEPTION DATE TIME: " + e.getExceptionInfo().getTimeOfException());
            System.err.println("EXCEPTION CODE: " + e.getExceptionInfo().getExceptionCode().getDescription());
            System.out.println(e.getExceptionInfo().getMessage());

        }
    }
}
