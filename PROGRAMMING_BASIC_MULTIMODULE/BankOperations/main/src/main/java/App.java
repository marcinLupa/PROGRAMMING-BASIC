
public class App {

    public static void main(String[] args) {
        try {
            new MyService().cardOperationManager("C:/Users/48783/IdeaProjects/DUZE PROGRAMY/PROGRAMMING_BASIC_MULTIMODULE/BankOperations/main/src/main/resources/LIST_OF_COUNTS.json");
        } catch (MyException e) {
            System.err.println("EXCEPTION DATE TIME: " + e.getExceptionInfo().getTimeOfException());
            System.err.println("EXCEPTION CODE: " + e.getExceptionInfo().getExceptionCode().getDescription());
            System.out.println(e.getExceptionInfo().getMessage());

        }
    }
}
