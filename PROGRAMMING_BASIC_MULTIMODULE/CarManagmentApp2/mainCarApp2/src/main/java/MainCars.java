import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class MainCars {


    private static List<String> getCarsInSalon() {
        final String filename="C:/Users/48783/IdeaProjects/DUZE PROGRAMY/PROGRAMMING_BASIC_MULTIMODULE/CarManagmentApp2/jsonConverterCarApp2/src/main/resources/";
        try {
            return Arrays
                    .stream(Objects.requireNonNull(new File(
                            filename)
                            .listFiles()))
                    .map(x -> filename + x.getName())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.JSON, "JSON FILES FOLDER EXCEPTION" + e.getMessage());
        }
    }
//
    public static void main(String[] args) {
        try {

            new MenuService().menageCars(getCarsInSalon());
        } catch
        (MyException e) {
            e.printStackTrace();
            System.err.println("EXCEPTION DATE TIME: " + e.getExceptionTime());
            System.err.println("EXCEPTION CODE: " + e.getExceptionCode());
            System.out.println(e.getMessage());
        }
    }
}
